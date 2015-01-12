{:namespaces
 ({:source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
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
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L29",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/->PluggableMemoization",
   :doc
   "Positional factory function for class clojure.core.memoize.PluggableMemoization.",
   :var-type "function",
   :line 29,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([cache-factory f & args]),
   :name "build-memoizer",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L137",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/build-memoizer",
   :doc
   "Builds a function that given a function, returns a pluggable memoized\nversion of it.  `build-memoizer` Takes a cache factory function, a function\nto memoize, and the arguments to the factory.  At least one of those\nfunctions should be the function to be memoized.",
   :var-type "function",
   :line 137,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :name "fifo",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L227",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/fifo",
   :doc
   "Works the same as the basic memoization function (i.e. `memo`\nand `core.memoize` except when a given threshold is breached.\n\nObserve the following:\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/fifo identity :fifo/threshold 2))\n\n    (id 42)\n    (id 43)\n    (snapshot id)\n    ;=> {[42] 42, [43] 43}\n\nAs you see, the limit of `2` has not been breached yet, but\nif you call again with another value, then it is:\n\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [43] 43}\n\nThat is, the oldest entry `42` is pushed out of the\nmemoization cache.  This is the standard **F**irst **I**n\n**F**irst **O**ut behavior.",
   :var-type "function",
   :line 227,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :name "lru",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L277",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/lru",
   :doc
   "Works the same as the basic memoization function (i.e. `memo`\nand `core.memoize` except when a given threshold is breached.\n\nObserve the following:\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/lru identity :lru/threshold 2))\n\n    (id 42)\n    (id 43)\n    (snapshot id)\n    ;=> {[42] 42, [43] 43}\n\nAt this point the cache has not yet crossed the set threshold\nof `2`, but if you execute yet another call the story will\nchange:\n\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [43] 43}\n\nAt this point the operation of the LRU cache looks exactly\nthe same at the FIFO cache.  However, the difference becomes\napparent on further use:\n\n    (id 43)\n    (id 0)\n    (snapshot id)\n    ;=> {[0] 0, [43] 43}\n\nAs you see, once again calling `id` with the argument `43`\nwill expose the LRU nature of the underlying cache.  That is,\nwhen the threshold is passed, the cache will expel the\n**L**east **R**ecently **U**sed element in favor of the new.",
   :var-type "function",
   :line 277,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :name "lu",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L384",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/lu",
   :doc
   "Similar to the implementation of memo-lru, except that this\nfunction removes all cache values whose usage value is\nsmallest:\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/lu identity :lu/threshold 3))\n\n    (id 42)\n    (id 42)\n    (id 43)\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [42] 42}\n\nThe **L**east **U**sed values are cleared on cache misses.",
   :var-type "function",
   :line 384,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f seed]),
   :name "memo",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L156",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo",
   :doc
   "Used as a more flexible alternative to Clojure's core `memoization`\nfunction.  Memoized functions built using `memo` will respond to\nthe core.memo manipulable memoization utilities.  As a nice bonus,\nyou can use `memo` in place of `memoize` without any additional\nchanges.\n\nThe default way to use this function is to simply apply a function\nthat will be memoized.  Additionally, you may also supply a map\nof the form `'{[42] 42, [108] 108}` where keys are a vector\nmapping expected argument values to arity positions.  The map values\nare the return values of the memoized function.\n\nYou can access the memoization cache directly via the `:clojure.core.memoize/cache` key\non the memoized function's metadata.  However, it is advised to\nuse the core.memo primitives instead as implementation details may\nchange over time.",
   :var-type "function",
   :line 156,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f args]),
   :name "memo-clear!",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L92",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-clear!",
   :doc
   "Reaches into an core.memo-memoized function and clears the cache.  This is a\ndestructive operation and should be used with care.\n\nWhen the second argument is a vector of input arguments, clears cache only\nfor argument vector.\n\nKeep in mind that depending on what other threads or doing, an\nimmediate call to `snapshot` may not yield an empty cache.  That's\ncool though, we've learned to deal with that stuff in Clojure by\nnow.",
   :var-type "function",
   :line 92,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-fifo",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L216",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-fifo",
   :doc "DEPRECATED: Please use clojure.core.memoize/fifo instead.",
   :var-type "function",
   :line 216,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-lru",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L266",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-lru",
   :doc "DEPRECATED: Please use clojure.core.memoize/lru instead.",
   :var-type "function",
   :line 266,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-lu",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L373",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-lu",
   :doc "DEPRECATED: Please use clojure.core.memoize/lu instead.",
   :var-type "function",
   :line 373,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f base]),
   :name "memo-swap!",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L110",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-swap!",
   :doc
   "Takes a core.memo-populated function and a map and replaces the memoization cache\nwith the supplied map.  This is potentially some serious voodoo,\nsince you can effectively change the semantics of a function on the fly.\n\n    (def id (memo identity))\n    (memo-swap! id '{[13] :omg})\n    (id 13)\n    ;=> :omg\n\nWith great power comes ... yadda yadda yadda.",
   :var-type "function",
   :line 110,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f limit] [f limit base]),
   :name "memo-ttl",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L327",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memo-ttl",
   :doc "DEPRECATED: Please use clojure.core.memoize/ttl instead.",
   :var-type "function",
   :line 327,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f]),
   :name "memoized?",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L87",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/memoized?",
   :doc
   "Returns true if a function has an core.memo-placed cache, false otherwise.",
   :var-type "function",
   :line 87,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([memoized-fn]),
   :name "snapshot",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L77",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/snapshot",
   :doc
   "Returns a snapshot of a core.memo-placed memoization cache.  By snapshot\nyou can infer that what you get is only the cache contents at a\nmoment in time.",
   :var-type "function",
   :line 77,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :name "ttl",
   :namespace "clojure.core.memoize",
   :source-url
   "https://github.com/clojure/core.memoize/blob/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj#L338",
   :raw-source-url
   "https://github.com/clojure/core.memoize/raw/20181ce9083b041a2fc89f83b26919dfee0dd70c/src/main/clojure/clojure/core/memoize.clj",
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/ttl",
   :doc
   "Unlike many of the other core.memo memoization functions,\n`memo-ttl`'s cache policy is time-based rather than algortihmic\nor explicit.  When memoizing a function using `memo-ttl` you\nshould provide a **T**ime **T**o **L**ive parameter in\nmilliseconds.\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/ttl identity :ttl/threshold 5000))\n\n    (id 42)\n    (snapshot id)\n    ;=> {[42] 42}\n\n    ... wait 5 seconds ...\n    (id 43)\n    (snapshot id)\n    ;=> {[43] 43}\n\nThe expired cache entries will be removed on each cache **miss**.",
   :var-type "function",
   :line 338,
   :file "src/main/clojure/clojure/core/memoize.clj"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/core.memoize//clojure.core.memoize-api.html#clojure.core.memoize/PluggableMemoization",
   :namespace "clojure.core.memoize",
   :var-type "type",
   :name "PluggableMemoization"})}
