;   Copyright (c) Rich Hickey. All rights reserved.
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
    ;; First check that the basic memo behavior holds
;;    (test-type-transparency #(memo-fifo % 10))

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
  ;; (test-type-transparency #(memo-lru % 10))
  
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
  ;;  (test-type-transparency #(memo-ttl % 2000))
  
  (let [mine (memo-ttl identity 2000)]
    (are [x y] =
         42        (id 42)
         {[42] 42} (snapshot id))
    (Thread/sleep 3000)
    (are [x y] =
         43        (id 43)
         {[43] 43} (snapshot id))))

(deftest test-memo-lu
  ;; (test-type-transparency #(memo-lu % 10))
  
  (let [mine (memo-lu identity 3)]
    (are [x y] =
         42                 (id 42)
         42                 (id 42)
         43                 (id 43)
         44                 (id 44)
         {[44] 44, [42] 42} (snapshot id))))

