;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "A memoization library for Clojure."
      :author "Michael Fogus"}
  clojure.core.memoize.tests
  (:use [clojure.core.memoize] :reload-all)
  (:use [clojure.test]))

(def id (memo identity))

(defn- test-type-transparency
  [factory]
  (let [mine (factory identity)
        them (memoize identity)]
    (testing "That the memo function works the same as core.memoize"
      (are [x y] =
           (mine 42) (them 42)
           (mine ()) (them ())
           (mine []) (them [])
           (mine #{}) (them #{})
           (mine {}) (them {})
           (mine nil) (them nil)))
    (testing "That the memo function has a proper cache"
      (is (memoized? mine))
      (is (not (memoized? them)))
      (is (= 42 (mine 42)))
      (is (not (empty? (snapshot mine))))
      (is (memo-clear! mine))
      (is (empty? (snapshot mine))))))

(deftest test-memo
  (test-type-transparency memo))

(deftest test-memo-fifo
  (let [mine (memo-fifo identity 2)]
    ;; First check that the basic memo behavior holds
    (test-type-transparency #(memo-fifo % 10))

    ;; Now check FIFO-specific behavior
    (testing "that when the limit threshold is not breached, the cache works like the basic version"
      (are [x y] =
           42                 (mine 42)
           {[42] 42}         (snapshot mine)
           43                 (mine 43)
           {[42] 42, [43] 43} (snapshot mine)
           42                 (mine 42)
           {[42] 42, [43] 43} (snapshot mine)))
    (testing "that when the limit is breached, the oldest value is dropped"
      (are [x y] =
           44                 (mine 44)
           {[44] 44, [43] 43} (snapshot mine)))))

(deftest test-memo-lru
  (test-type-transparency #(memo-lru % 10))
  (let [mine (memo-lru identity)]
    (are [x y] =
         42                 (id 42)
         43                 (id 43)
         {[42] 42, [43] 43} (snapshot id)
         44                 (id 44)
         {[44] 44, [43] 43} (snapshot id)
         43                 (id 43)
         0                  (id 0)
         {[0] 0, [43] 43}   (snapshot id))))

(deftest test-ttl
  (test-type-transparency #(memo-ttl % 2000))
  (let [mine (memo-ttl identity 2000)]
    (are [x y] =
         42        (id 42)
         {[42] 42} (snapshot id))
    (Thread/sleep 3000)
    (are [x y] =
         43        (id 43)
         {[43] 43} (snapshot id))))

(deftest test-memo-lu
  (test-type-transparency #(memo-lu % 10))
  (let [mine (memo-lu identity 3)]
    (are [x y] =
         42                 (id 42)
         42                 (id 42)
         43                 (id 43)
         44                 (id 44)
         {[44] 44, [42] 42} (snapshot id))))

(deftest test-memoization-utils
  (let [CACHE_IDENTITY (:unk (meta id))]
    (testing "That an unk-populated function looks correct at its inception"
      (is (memoized? id))
      (is (snapshot id))
      (is (empty? (snapshot id))))
    (testing "That an unk-populated function looks correct after some interactions"
      ;; Memoize once
      (is (= 42 (id 42)))
      ;; Now check to see if it looks right.
      (is (find (snapshot id) '(42)))
      (is (= 1 (count (snapshot id))))
      ;; Memoize again
      (is (= [] (id [])))
      (is (find (snapshot id) '([])))
      (is (= 2 (count (snapshot id))))
      (testing "that upon memoizing again, the cache should not change"
        (is (= [] (id [])))
        (is (find (snapshot id) '([])))
        (is (= 2 (count (snapshot id)))))
      (testing "if clearing the cache works as expected"
        (is (memo-clear! id))
        (is (empty? (snapshot id)))))
    (testing "that after all manipulations, the cache maintains its identity"
      (is (identical? CACHE_IDENTITY (:unk (meta id)))))
    (testing "that a cache can be seeded and used normally"
      (is (memo-swap! id {[42] 42}))
      (is (= 42 (id 42)))
      (is (= {[42] 42} (snapshot id)))
      (is (= 108 (id 108)))
      (is (= {[42] 42 [108] 108} (snapshot id))))
    (testing "that we can get back the original function"
      (is (memo-clear! id))
      (is (memo-swap! id {[42] 24}))
      (is 24 (id 42))
      (is 42 ((memo-unwrap id) 42)))))
