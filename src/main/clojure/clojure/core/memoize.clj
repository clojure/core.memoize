;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.core.memoize
  "core.memoize is a memoization library offering functionality above Clojure's core `memoize`
  function in the following ways:

  **Pluggable memoization**

  core.memoize allows for different back-end cache implmentations to be used as appropriate without
  changing the memoization modus operandi.

  **Manipulable memoization**

  Because core.memoize allows you to access a function's memoization store, you do interesting things like
  clear it, modify it, and save it for later.
  "
  {:author "fogus"}

  (:require [clojure.core.cache :as cache])
  (:import [clojure.core.cache CacheProtocol]))

;; Plugging Interface

(deftype PluggableMemoization [f cache]
  CacheProtocol
  (has? [_ item]
    (clojure.core.cache/has? cache item))
  (hit  [_ item]
    (PluggableMemoization. f (clojure.core.cache/hit cache item)))
  (miss [_ item result]
    (PluggableMemoization. f (clojure.core.cache/miss cache item result)))
  (evict [_ key]
    (PluggableMemoization. f (clojure.core.cache/evict cache key)))
  (lookup [_ item]
    (clojure.core.cache/lookup cache item))
  (seed [_ base]
    (PluggableMemoization. f (clojure.core.cache/seed cache base)))
  Object
  (toString [_] (str cache)))


;; # Auxilliary functions

(defn through* [cache f item]
  "The basic hit/miss logic for the cache system based on `core.cache/through`.
  Clojure delays are used to hold the cache value."
  (clojure.core.cache/through
    #(delay (%1 %2))
    #(clojure.core/apply f %)
    cache
    item))

(def ^{:private true
       :doc "Returns a function's cache identity."}
  cache-id #(::cache (meta %)))


;; # Public Utilities API

(defn snapshot
  "Returns a snapshot of a core.memo-placed memoization cache.  By snapshot
   you can infer that what you get is only the cache contents at a
   moment in time."
  [memoized-fn]
  (when-let [cache (::cache (meta memoized-fn))]
    (into {}
          (for [[k v] (.cache @cache)]
            [(vec k) @v]))))

(defn memoized?
  "Returns true if a function has an core.memo-placed cache, false otherwise."
  [f]
  (boolean (::cache (meta f))))

(defn memo-clear!
  "Reaches into an core.memo-memoized function and clears the cache.  This is a
   destructive operation and should be used with care.

   Keep in mind that depending on what other threads or doing, an
   immediate call to `snapshot` may not yield an empty cache.  That's
   cool though, we've learned to deal with that stuff in Clojure by
   now."
  [f]
  (when-let [cache (cache-id f)]
    (swap! cache (constantly (clojure.core.cache/seed @cache {})))))

(defn memo-swap!
  "Takes a core.memo-populated function and a map and replaces the memoization cache
   with the supplied map.  This is potentially some serious voodoo,
   since you can effectively change the semantics of a function on the fly.

       (def id (memo identity))
       (memo-swap! id '{[13] :omg})
       (id 13)
       ;=> :omg

   With great power comes ... yadda yadda yadda."
  [f base]
  (when-let [cache (cache-id f)]
    (swap! cache
           (constantly (clojure.core.cache/seed @cache
                             (into {}
                                   (for [[k v] base]
                                     [k (reify
                                          clojure.lang.IDeref
                                          (deref [this] v))])))))))

(defn memo-unwrap
  [f]
  (::original (meta f)))

;; # Public memoization API

(defn build-memoizer
  "Builds a function that given a function, returns a pluggable memoized
   version of it.  `build-memoizer` Takes a cache factory function, a function
   to memoize, and the arguments to the factory.  At least one of those
   functions should be the function to be memoized."
  ([cache-factory f & args]
     (let [cache (atom (apply cache-factory f args))]
       (with-meta
        (fn [& args]
          (let [cs  (swap! cache through* f args)
                val (clojure.core.cache/lookup cs args)]
            ;; The assumption here is that if what we got
            ;; from the cache was non-nil, then we can dereference
            ;; it.  core.memo currently wraps all of its values in
            ;; a `delay`.
            (and val @val)))
        {::cache cache
         ::original f}))))

(defn memo
  "Used as a more flexible alternative to Clojure's core `memoization`
   function.  Memoized functions built using `memo` will respond to
   the core.memo manipulable memoization utilities.  As a nice bonus,
   you can use `memo` in place of `memoize` without any additional
   changes.

   The default way to use this function is to simply apply a function
   that will be memoized.  Additionally, you may also supply a map
   of the form `'{[42] 42, [108] 108}` where keys are a vector
   mapping expected argument values to arity positions.  The map values
   are the return values of the memoized function.

   You can access the memoization cache directly via the `:clojure.core.memoize/cache` key
   on the memoized function's metadata.  However, it is advised to
   use the core.memo primitives instead as implementation details may
   change over time."
  ([f] (memo f {}))
  ([f seed]
     (build-memoizer
       #(PluggableMemoization. %1 (cache/basic-cache-factory %2))
       f
       seed)))

(defn ^:private !! [c new-form]
  (println "WARNING - Deprecated construction method for"
           c
           "cache prefered way is:"
           (str "(memo-" c " <base> :" c "/threshold <num>)")))

(defn memo-fifo
  "Works the same as the basic memoization function (i.e. `memo` and `core.memoize` except
   when a given threshold is breached.  Observe the following:

       (def id (memo-fifo identity 2))

       (id 42)
       (id 43)
       (snapshot id)
       ;=> {[42] 42, [43] 43}

   As you see, the limit of `2` has not been breached yet, but if you call again with another
   value, then it will:

       (id 44)
       (snapshot id)
       ;=> {[44] 44, [43] 43}

   That is, the oldest entry `42` is pushed out of the memoization cache.  This is the standard
   **F**irst **I**n **F**irst **O**ut behavior."
  ([f] (!!) (memo-fifo f 32 {}))
  ([f limit] (!!) (memo-fifo f limit {}))
  ([f limit base] (!!) (memo-fifo f base :threshold limit))
  ([f base _ & [threshold & _]]
     (build-memoizer
       #(PluggableMemoization. %1 (cache/fifo-cache-factory %3 :threshold %2))
       f
       (or threshold 32)
       base)))

(defn memo-lru
  "Works the same as the basic memoization function (i.e. `memo` and `core.memoize` except
   when a given threshold is breached.  Observe the following:

       (def id (memo-lru identity 2))

       (id 42)
       (id 43)
       (snapshot id)
       ;=> {[42] 42, [43] 43}

   At this point the cache has not yet crossed the set threshold of `2`, but if you execute
   yet another call the story will change:

       (id 44)
       (snapshot id)
       ;=> {[44] 44, [43] 43}

   At this point the operation of the LRU cache looks exactly the same at the FIFO cache.
   However, the difference becomes apparent on further use:

       (id 43)
       (id 0)
       (snapshot id)
       ;=> {[0] 0, [43] 43}

   As you see, once again calling `id` with the argument `43` will expose the LRU nature
   of the underlying cache.  That is, when the threshold is passed, the cache will expel
   the **L**east **R**ecently **U**sed element in favor of the new."
  ([f] (memo-lru f 32))
  ([f limit] (memo-lru f limit {}))
  ([f limit base]
     (build-memoizer
       #(PluggableMemoization. %1 (cache/lru-cache-factory %3 :threshold %2))
       f
       limit
       base)))

(defn memo-ttl
  "Unlike many of the other core.memo memoization functions, `memo-ttl`'s cache policy is time-based
   rather than algortihmic or explicit.  When memoizing a function using `memo-ttl` you should
   should provide a **T**ime **T**o **L**ive parameter in milliseconds.

       (def id (memo-ttl identity 5000))

       (id 42)
       (snapshot id)
       ;=> {[42] 42}

       ... wait 5 seconds ...
       (id 43)
       (snapshot id)
       ;=> {[43] 43}

   The expired cache entries will be removed on each cache miss."
  ([f] (memo-ttl f 3000 {}))
  ([f limit] (memo-ttl f limit {}))
  ([f limit base]
     (build-memoizer
       #(PluggableMemoization. %1 (cache/ttl-cache-factory %3 :ttl %2))
       f
       limit
       {})))

(defn memo-lu
  "Similar to the implementation of memo-lru, except that this function removes all cache
   values whose usage value is smallest.

       (def id (memo-lu identity 3))

       (id 42)
       (id 42)
       (id 43)
       (id 44)
       (snapshot id)
       ;=> {[44] 44, [42] 42}

   The **L**east **U**sed values are cleared on cache misses."
  ([f] (memo-lu f 32))
  ([f limit] (memo-lu f limit {}))
  ([f limit base]
     (build-memoizer
       #(PluggableMemoization. %1 (cache/lu-cache-factory %3 :threshold %2))
       f
       limit
       base)))

