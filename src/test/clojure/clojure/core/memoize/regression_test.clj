;   Copyright (c) Rich Hickey and Michael Fogus. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "A memoization library for Clojure."
      :author "Michael Fogus"}
  clojure.core.memoize.regression-test
  (:use [clojure.test]
        [clojure.core.memoize]
        [clojure.core.cache :only [defcache lookup has? hit miss seed ttl-cache-factory]])
  (:import (clojure.core.memoize PluggableMemoization)
           (clojure.core.cache CacheProtocol)))

(deftest test-regression-cmemoize-5
  (testing "that the TTL doesn't bomb on race-condition"
    (try
      (let [id (ttl identity :ttl/threshold 100)]
        (dotimes [_ 10000000] (id 1)))
      (is (= 1 1))
      (catch NullPointerException npe
        (is (= 1 0))))))

(deftest test-regression-cmemoize-7
  (testing "that a memoized function that throws a RTE continues to retry"
    (let [doit (fn [] (throw (RuntimeException. "Foo")))
          it   (lru doit)]
      (is (= :RTE
             (try
               (it)
               (catch Exception _
                 (try
                   (it)
                   (catch NullPointerException _
                     :NPE)
                   (catch RuntimeException _
                     :RTE)
                   (catch Exception e
                     (class e))))))))))

(defn- test-helper-26
  ([] 100)
  ([i] (inc i)))

(deftest test-regression-cmemoize-26
  (let [mine (memo test-helper-26)]
    (testing "that memo-swap! is respected for zero arity"
      (is (= 100 (mine)))
      (is (= 201 (mine 200)))
      (is (= {[] 100, [200] 201} (snapshot mine)))
      (memo-swap! mine {[] :something [200] :else})
      (is (= {[] :something, [200] :else} (snapshot mine)))
      ;; unary works...
      (is (= :else (mine 200)))
      ;; ...but 0-arity still has old value...
      (is (= :something (mine)))
      ;; ...and snapshot gets changed
      (is (= {[] :something, [200] :else} (snapshot mine))))))
