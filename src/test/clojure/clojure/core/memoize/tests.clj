;   Copyright (c) Rich Hickey and Michael Fogus. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "A memoization library for Clojure."
      :author "Michael Fogus"}
  clojure.core.memoize.tests
  (:use [clojure.test]
        [clojure.core.cache :only [defcache lookup has? hit miss seed ttl-cache-factory]]
        [clojure.core.memoize]))

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
      (is (empty? (snapshot mine))))
    (testing "That the memo function does not have a race condition"
      (let [access-count (atom 0)
            slow-identity
            (factory (fn [x] (swap! access-count inc)
                       (Thread/sleep 1000)
                       x))]
        (and (future (slow-identity 5)) (slow-identity 5))
        (is (= @access-count 1))))))

(deftest test-memo
  (test-type-transparency memo))


(deftest test-fifo
  (let [mine (fifo identity :fifo/threshold 2)]
    ;; First check that the basic memo behavior holds
    (test-type-transparency #(fifo % :fifo/threshold 10))

    ;; Now check FIFO-specific behavior
    (testing "that when the limit threshold is not breached, the cache works like the basic version"
      (are [x y] =
           42                 (mine 42)
           {[42] 42}          (snapshot mine)
           43                 (mine 43)
           {[42] 42, [43] 43} (snapshot mine)
           42                 (mine 42)
           {[42] 42, [43] 43} (snapshot mine)))
    (testing "that when the limit is breached, the oldest value is dropped"
      (are [x y] =
           44                 (mine 44)
           {[44] 44, [43] 43} (snapshot mine)))))


(deftest test-lru
  ;; First check that the basic memo behavior holds
  (test-type-transparency #(lru % :lru/threshold 10))

  ;; Now check LRU-specific behavior
  (let [mine (lru identity)]
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
  ;; First check that the basic memo behavior holds
  (test-type-transparency #(ttl % :ttl/threshold 2000))

  ;; Now check TTL-specific behavior
  (let [mine (ttl identity :ttl/threshold 2000)]
    (are [x y] =
         42        (id 42)
         {[42] 42} (snapshot id))
    (Thread/sleep 3000)
    (are [x y] =
         43        (id 43)
         {[43] 43} (snapshot id))))


(deftest test-lu
  ;; First check that the basic memo behavior holds
  (test-type-transparency #(lu % :lu/threshold 10))

  ;; Now check LU-specific behavior
  (let [mine (lu identity :lu/threshold 3)]
    (are [x y] =
         42                 (id 42)
         42                 (id 42)
         43                 (id 43)
         44                 (id 44)
         {[44] 44, [42] 42} (snapshot id))))


(defcache PassThrough [impl]
  clojure.core.cache/CacheProtocol
  (lookup [_ item]
    (if (has? impl item)
      (lookup impl item)
      (delay nil)))
  (has? [_ item]
    (clojure.core.cache/has? impl item))
  (hit [this item]
    (PassThrough. (hit impl item)))
  (miss [this item result]
    (PassThrough. (miss impl item result)))
  (seed [_ base]
    (PassThrough. (seed impl base))))

(defn memo-pass-through [f limit]
  (build-memoizer
       #(clojure.core.memoize.PluggableMemoization. %1 (PassThrough. (ttl-cache-factory %3 :ttl %2)))
       f
       limit
       {}))

(deftest test-snapshot-without-cache-field
  (testing "that we can call snapshot against an object without a 'cache' field"
    (is (= {} (snapshot (memo-pass-through identity 2000))))))
