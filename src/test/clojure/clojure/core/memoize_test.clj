;   Copyright (c) Rich Hickey and Michael Fogus. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "A memoization library for Clojure."
      :author "Michael Fogus"}
  clojure.core.memoize-test
  (:use [clojure.test]
        [clojure.core.cache :only [defcache lookup has? hit miss seed ttl-cache-factory]]
        [clojure.core.memoize]))

(println "\nTesting with Clojure" (clojure-version))

(def id (memo identity))

(defn- check-core-features
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
      (is (not (empty? (lazy-snapshot mine))))
      (is (memo-clear! mine))
      (is (empty? (snapshot mine)))
      (is (empty? (lazy-snapshot mine)))))
  (testing "That the cache retries in case of exceptions"
    (let [access-count (atom 0)
          f (factory
              (fn []
                (swap! access-count inc)
                (throw (Exception.))))]
      (is (thrown? Exception (f)))
      (is (thrown? Exception (f)))
      (is (= 2 @access-count))))
  (testing "That the memo function does not have a race condition"
    (let [access-count (atom 0)
          slow-identity
          (factory (fn [x]
                     (swap! access-count inc)
                     (Thread/sleep 100)
                     x))]
      (every? identity (pvalues (slow-identity 5) (slow-identity 5)))
      (is (= @access-count 1)))))

(deftest test-memo
  (check-core-features memo))


(deftest test-fifo
  (let [mine (fifo identity :fifo/threshold 2)]
    ;; First check that the basic memo behavior holds
    (check-core-features #(fifo % :fifo/threshold 10))

    ;; Now check FIFO-specific behavior
    (testing "that when the limit threshold is not breached, the cache works like the basic version"
      (are [x y] =
           42                 (mine 42)
           {[42] 42}          (snapshot mine)
           43                 (mine 43)
           {[42] 42, [43] 43} (snapshot mine)
           42                 (mine 42)
           {[42] 42, [43] 43} (snapshot mine)
           [[42] 42, [43] 43] (lazy-snapshot mine)))
    (testing "that when the limit is breached, the oldest value is dropped"
      (are [x y] =
           44                 (mine 44)
           {[44] 44, [43] 43} (snapshot mine)
           [[44] 44, [43] 43] (lazy-snapshot mine)))))

(deftest test-lru
  ;; First check that the basic memo behavior holds
  (check-core-features #(lru % :lru/threshold 10))

  ;; Now check LRU-specific behavior
  (let [mine (lru identity)]
    (are [x y] =
         42                 (mine 42)
         43                 (mine 43)
         {[42] 42, [43] 43} (snapshot mine)
         44                 (mine 44)
         {[44] 44, [43] 43} (snapshot mine)
         43                 (mine 43)
         0                  (mine 0)
         {[0] 0, [43] 43}   (snapshot mine)
         [[0] 0, [43] 43]   (lazy-snapshot mine))))


(deftest test-ttl
  ;; First check that the basic memo behavior holds
  (check-core-features #(ttl % :ttl/threshold 2000))

  ;; Now check TTL-specific behavior
  (let [mine (ttl identity :ttl/threshold 2000)]
    (are [x y] =
         42        (mine 42)
         {[42] 42} (snapshot mine))
    (Thread/sleep 3000)
    (are [x y] =
         43        (mine 43)
         {[43] 43} (snapshot mine)
         [[43] 43] (lazy-snapshot mine)))

  ;; CMEMOIZE-15 edge case where TTLCache expires on miss/lookup
  (let [mine  (ttl identity :ttl/threshold 5)
        limit 2000000
        start (System/currentTimeMillis)]
    (loop [n 0]
      (if-not (mine 42)
        (do
          (is false (str  "Failure on call " n)))
        (if (< n limit)
          (recur (+ 1 n)))))
    (println "ttl test completed" limit "calls in"
             (- (System/currentTimeMillis) start) "ms")))


(deftest test-lu
  ;; First check that the basic memo behavior holds
  (check-core-features #(lu % :lu/threshold 10))

  ;; Now check LU-specific behavior
  (let [mine (lu identity :lu/threshold 3)]
    (are [x y] =
         42                 (mine 42)
         42                 (mine 42)
         43                 (mine 43)
         44                 (mine 44)
         {[44] 44, [42] 42} (snapshot mine)
         [[44] 44, [42] 42] (lazy-snapshot mine))))


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

(defn memo-pass-through-1 [f limit]
  (build-memoizer
       #(clojure.core.memoize.PluggableMemoization. %1 (PassThrough. (ttl-cache-factory %3 :ttl %2)))
       f
       limit
       {}))

(deftest test-snapshot-without-cache-field-1
  (testing "that we can call snapshot against an object without a 'cache' field"
    (is (= {} (snapshot (memo-pass-through-1 identity 2000))))))

(defn memo-pass-through-2 [f limit]
  (memoizer f (PassThrough. (ttl-cache-factory {} :ttl limit))))

(deftest test-snapshot-without-cache-field-2
  (testing "that we can call snapshot against an object without a 'cache' field"
    (is (= {} (snapshot (memo-pass-through-2 identity 2000))))))

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
   (testing "that a cache can be swapped and used normally"
     (is (memo-swap! id clojure.core.cache/miss [42] 42))
     (is (= 42 (id 42)))
     (is (= {[42] 42} (snapshot id)))
     (is (= 108 (id 108)))
     (is (= {[42] 42 [108] 108} (snapshot id))))
   (testing "that a cache can be seeded and used normally"
     (is (memo-reset! id {[42] 42}))
     (is (= 42 (id 42)))
     (is (= {[42] 42} (snapshot id)))
     (is (= 108 (id 108)))
     (is (= {[42] 42 [108] 108} (snapshot id))))
   (testing "that we can get back the original function"
     (is (memo-clear! id))
     (is (memo-reset! id {[42] 24}))
     (is (= 24 (id 42)))
     (is (= 42 ((memo-unwrap id) 42))))
   (testing "that a cache can be seeded and used normally - deprecated memo-swap!"
     (is (memo-swap! id {[42] 42}))
     (is (= 42 (id 42)))
     (is (= {[42] 42} (snapshot id)))
     (is (= 108 (id 108)))
     (is (= {[42] 42 [108] 108} (snapshot id))))
   (testing "that we can get back the original function - deprecated memo-swap!"
     (is (memo-clear! id))
     (is (memo-swap! id {[42] 24}))
     (is (= 24 (id 42)))
     (is (= 42 ((memo-unwrap id) 42))))))

(deftest memo-with-seed-cmemoize-18
  (let [mine (memo identity {[42] 99})]
    (testing "that a memo seed works"
      (is (= 41 (mine 41)))
      (is (= 99 (mine 42)))
      (is (= 43 (mine 43)))
      (is (= {[41] 41, [42] 99, [43] 43} (snapshot mine)))
      (are [x y] =
        [[41] 41, [42] 99, [43] 43] (lazy-snapshot mine)))))

(defn- ^{:clojure.core.memoize/args-fn rest}
  ignores-first-arg
  "Even tho' this adds all three arguments, we're going to cache it as if
  the first argument is irrelevant, so we can verify that the cache will
  collapse the argument space."
  [a b c]
  (+ a b c))

(deftest memo-with-args-fn-cmemoize-22
  ;; must use var to preserve metadata
  (let [mine (memo #'ignores-first-arg)]
    (testing "that args-fn collapses the cache key space"
      (is (= 13 (mine 1 2 10)))
      (is (= 13 (mine 10 2 1)))
      (is (= 13 (mine 10 2 10)))
      (is (= {[2 10] 13, [2 1] 13} (snapshot mine))))))
