;   Copyright (c) Rich Hickey and Michael Fogus. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "A memoization library for Clojure."
      :author "Michael Fogus"}
  clojure.core.memoize.deprecation-tests
  (:use [clojure.core.memoize] :reload-all)
  (:use [clojure.test]
        [clojure.core.cache :only [defcache lookup has? hit miss seed ttl-cache-factory]])
  (:import (clojure.core.memoize PluggableMemoization)
           (clojure.core.cache CacheProtocol)))

(def id (memo identity))

(deftest test-memo-fifo
  (let [mine (memo-fifo identity 2)]
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
  (let [mine (memo-ttl identity 2000)]
    (are [x y] =
         42        (id 42)
         {[42] 42} (snapshot id))
    (Thread/sleep 3000)
    (are [x y] =
         43        (id 43)
         {[43] 43} (snapshot id))))

(deftest test-memo-lu
  (let [mine (memo-lu identity 3)]
    (are [x y] =
         42                 (id 42)
         42                 (id 42)
         43                 (id 43)
         44                 (id 44)
         {[44] 44, [42] 42} (snapshot id))))


(deftest test-memoization-utils
  (let [CACHE_IDENTITY (:clojure.core.memoize/cache (meta id))]
    (testing "that the stored cache is not null"
      (is (not= nil CACHE_IDENTITY)))
    (testing "that a populated function looks correct at its inception"
      (is (memoized? id))
      (is (snapshot id))
      (is (empty? (snapshot id))))
    (testing "that a populated function looks correct after some interactions"
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
      (is (identical? CACHE_IDENTITY (:clojure.core.memoize/cache (meta id)))))
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

