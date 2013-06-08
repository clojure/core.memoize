{:namespaces
 ({:source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize/clojure.core.memoize-api.html",
   :name "clojure.core.memoize",
   :author "fogus",
   :doc
   "core.memoize is a memoization library offering functionality above Clojure's core `memoize`\nfunction in the following ways:\n\n**Pluggable memoization**\n\ncore.memoize allows for different back-end cache implmentations to be used as appropriate without\nchanging the memoization modus operandi.\n\n**Manipulable memoization**\n\nBecause core.memoize allows you to access a function's memoization store, you do interesting things like\nclear it, modify it, and save it for later.\n"}),
 :vars
 ({:arglists ([f cache]),
   :name "->PluggableMemoization",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L30",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/->PluggableMemoization",
   :doc
   "Positional factory function for class clojure.core.memoize.PluggableMemoization.",
   :var-type "function",
   :line 30,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([cache-factory f & args]),
   :name "build-memoizer",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L120",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/build-memoizer",
   :doc
   "Builds a function that given a function, returns a pluggable memoized\nversion of it.  `build-memoizer` Takes a cache factory function, a function\nto memoize, and the arguments to the factory.  At least one of those\nfunctions should be the function to be memoized.",
   :var-type "function",
   :line 120,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f seed]),
   :name "memo",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L139",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo",
   :doc
   "Used as a more flexible alternative to Clojure's core `memoization`\nfunction.  Memoized functions built using `memo` will respond to\nthe core.memo manipulable memoization utilities.  As a nice bonus,\nyou can use `memo` in place of `memoize` without any additional\nchanges.\n\nThe default way to use this function is to simply apply a function\nthat will be memoized.  Additionally, you may also supply a map\nof the form `'{[42] 42, [108] 108}` where keys are a vector\nmapping expected argument values to arity positions.  The map values\nare the return values of the memoized function.\n\nYou can access the memoization cache directly via the `:clojure.core.memoize/cache` key\non the memoized function's metadata.  However, it is advised to\nuse the core.memo primitives instead as implementation details may\nchange over time.",
   :var-type "function",
   :line 139,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f]),
   :name "memo-clear!",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L81",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-clear!",
   :doc
   "Reaches into an core.memo-memoized function and clears the cache.  This is a\ndestructive operation and should be used with care.\n\nKeep in mind that depending on what other threads or doing, an\nimmediate call to `snapshot` may not yield an empty cache.  That's\ncool though, we've learned to deal with that stuff in Clojure by\nnow.",
   :var-type "function",
   :line 81,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-fifo",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L163",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-fifo",
   :doc
   "Works the same as the basic memoization function (i.e. `memo` and `core.memoize` except\nwhen a given threshold is breached.  Observe the following:\n\n    (def id (memo-fifo identity 2))\n\n    (id 42)\n    (id 43)\n    (snapshot id)\n    ;=> {[42] 42, [43] 43}\n\nAs you see, the limit of `2` has not been breached yet, but if you call again with another\nvalue, then it will:\n\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [43] 43}\n\nThat is, the oldest entry `42` is pushed out of the memoization cache.  This is the standard\n**F**irst **I**n **F**irst **O**ut behavior.",
   :var-type "function",
   :line 163,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-lru",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L192",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-lru",
   :doc
   "Works the same as the basic memoization function (i.e. `memo` and `core.memoize` except\nwhen a given threshold is breached.  Observe the following:\n\n    (def id (memo-lru identity 2))\n\n    (id 42)\n    (id 43)\n    (snapshot id)\n    ;=> {[42] 42, [43] 43}\n\nAt this point the cache has not yet crossed the set threshold of `2`, but if you execute\nyet another call the story will change:\n\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [43] 43}\n\nAt this point the operation of the LRU cache looks exactly the same at the FIFO cache.\nHowever, the difference becomes apparent on further use:\n\n    (id 43)\n    (id 0)\n    (snapshot id)\n    ;=> {[0] 0, [43] 43}\n\nAs you see, once again calling `id` with the argument `43` will expose the LRU nature\nof the underlying cache.  That is, when the threshold is passed, the cache will expel\nthe **L**east **R**ecently **U**sed element in favor of the new.",
   :var-type "function",
   :line 192,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-lu",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L256",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-lu",
   :doc
   "Similar to the implementation of memo-lru, except that this function removes all cache\nvalues whose usage value is smallest.\n\n    (def id (memo-lu identity 3))\n\n    (id 42)\n    (id 42)\n    (id 43)\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [42] 42}\n\nThe **L**east **U**sed values are cleared on cache misses.",
   :var-type "function",
   :line 256,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f base]),
   :name "memo-swap!",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L93",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-swap!",
   :doc
   "Takes a core.memo-populated function and a map and replaces the memoization cache\nwith the supplied map.  This is potentially some serious voodoo,\nsince you can effectively change the semantics of a function on the fly.\n\n    (def id (memo identity))\n    (memo-swap! id '{[13] :omg})\n    (id 13)\n    ;=> :omg\n\nWith great power comes ... yadda yadda yadda.",
   :var-type "function",
   :line 93,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-ttl",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L230",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-ttl",
   :doc
   "Unlike many of the other core.memo memoization functions, `memo-ttl`'s cache policy is time-based\nrather than algortihmic or explicit.  When memoizing a function using `memo-ttl` you should\nshould provide a **T**ime **T**o **L**ive parameter in milliseconds.\n\n    (def id (memo-ttl identity 5000))\n\n    (id 42)\n    (snapshot id)\n    ;=> {[42] 42}\n\n    ... wait 5 seconds ...\n    (id 43)\n    (snapshot id)\n    ;=> {[43] 43}\n\nThe expired cache entries will be removed on each cache miss.",
   :var-type "function",
   :line 230,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f]),
   :name "memoized?",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L76",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memoized?",
   :doc
   "Returns true if a function has an core.memo-placed cache, false otherwise.",
   :var-type "function",
   :line 76,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([memoized-fn]),
   :name "snapshot",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj#L66",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/14e846c23610065f40f17dd54166f1a55ba7bdbe/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/snapshot",
   :doc
   "Returns a snapshot of a core.memo-placed memoization cache.  By snapshot\nyou can infer that what you get is only the cache contents at a\nmoment in time.",
   :var-type "function",
   :line 66,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/PluggableMemoization",
   :namespace "clojure.core.memoize",
   :var-type "type",
   :name "PluggableMemoization"})}
