{:namespaces
 ({:doc
   "core.memoize is a memoization library offering functionality above Clojure's core `memoize`\nfunction in the following ways:\n\n**Pluggable memoization**\n\ncore.memoize allows for different back-end cache implmentations to be used as appropriate without\nchanging the memoization modus operandi.\n\n**Manipulable memoization**\n\nBecause core.memoize allows you to access a function's memoization store, you do interesting things like\nclear it, modify it, and save it for later.\n",
   :author "fogus",
   :name "clojure.core.memoize",
   :wiki-url "http://clojure.github.io/core.memoize/index.html",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj"}),
 :vars
 ({:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "->PluggableMemoization",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L29",
   :line 29,
   :var-type "function",
   :arglists ([f cache]),
   :doc
   "Positional factory function for class clojure.core.memoize.PluggableMemoization.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/->PluggableMemoization"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "->RetryingDelay",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L52",
   :line 52,
   :var-type "function",
   :arglists ([fun available? value]),
   :doc
   "Positional factory function for class clojure.core.memoize.RetryingDelay.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/->RetryingDelay"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "build-memoizer",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L152",
   :line 152,
   :var-type "function",
   :arglists ([cache-factory f & args]),
   :doc
   "Builds a function that given a function, returns a pluggable memoized\nversion of it.  `build-memoizer` Takes a cache factory function, a function\nto memoize, and the arguments to the factory.  At least one of those\nfunctions should be the function to be memoized.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/build-memoizer"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "fifo",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L242",
   :line 242,
   :var-type "function",
   :arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :doc
   "Works the same as the basic memoization function (i.e. `memo`\nand `core.memoize` except when a given threshold is breached.\n\nObserve the following:\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/fifo identity :fifo/threshold 2))\n\n    (id 42)\n    (id 43)\n    (snapshot id)\n    ;=> {[42] 42, [43] 43}\n\nAs you see, the limit of `2` has not been breached yet, but\nif you call again with another value, then it is:\n\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [43] 43}\n\nThat is, the oldest entry `42` is pushed out of the\nmemoization cache.  This is the standard **F**irst **I**n\n**F**irst **O**ut behavior.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/fifo"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "lru",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L292",
   :line 292,
   :var-type "function",
   :arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :doc
   "Works the same as the basic memoization function (i.e. `memo`\nand `core.memoize` except when a given threshold is breached.\n\nObserve the following:\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/lru identity :lru/threshold 2))\n\n    (id 42)\n    (id 43)\n    (snapshot id)\n    ;=> {[42] 42, [43] 43}\n\nAt this point the cache has not yet crossed the set threshold\nof `2`, but if you execute yet another call the story will\nchange:\n\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [43] 43}\n\nAt this point the operation of the LRU cache looks exactly\nthe same at the FIFO cache.  However, the difference becomes\napparent on further use:\n\n    (id 43)\n    (id 0)\n    (snapshot id)\n    ;=> {[0] 0, [43] 43}\n\nAs you see, once again calling `id` with the argument `43`\nwill expose the LRU nature of the underlying cache.  That is,\nwhen the threshold is passed, the cache will expel the\n**L**east **R**ecently **U**sed element in favor of the new.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/lru"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "lu",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L399",
   :line 399,
   :var-type "function",
   :arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :doc
   "Similar to the implementation of memo-lru, except that this\nfunction removes all cache values whose usage value is\nsmallest:\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/lu identity :lu/threshold 3))\n\n    (id 42)\n    (id 42)\n    (id 43)\n    (id 44)\n    (snapshot id)\n    ;=> {[44] 44, [42] 42}\n\nThe **L**east **U**sed values are cleared on cache misses.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/lu"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L171",
   :line 171,
   :var-type "function",
   :arglists ([f] [f seed]),
   :doc
   "Used as a more flexible alternative to Clojure's core `memoization`\nfunction.  Memoized functions built using `memo` will respond to\nthe core.memo manipulable memoization utilities.  As a nice bonus,\nyou can use `memo` in place of `memoize` without any additional\nchanges.\n\nThe default way to use this function is to simply apply a function\nthat will be memoized.  Additionally, you may also supply a map\nof the form `'{[42] 42, [108] 108}` where keys are a vector\nmapping expected argument values to arity positions.  The map values\nare the return values of the memoized function.\n\nYou can access the memoization cache directly via the `:clojure.core.memoize/cache` key\non the memoized function's metadata.  However, it is advised to\nuse the core.memo primitives instead as implementation details may\nchange over time.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo-clear!",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L107",
   :line 107,
   :var-type "function",
   :arglists ([f] [f args]),
   :doc
   "Reaches into an core.memo-memoized function and clears the cache.  This is a\ndestructive operation and should be used with care.\n\nWhen the second argument is a vector of input arguments, clears cache only\nfor argument vector.\n\nKeep in mind that depending on what other threads or doing, an\nimmediate call to `snapshot` may not yield an empty cache.  That's\ncool though, we've learned to deal with that stuff in Clojure by\nnow.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo-clear!"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo-fifo",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L231",
   :line 231,
   :var-type "function",
   :arglists ([f] [f limit] [f limit base]),
   :doc "DEPRECATED: Please use clojure.core.memoize/fifo instead.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo-fifo"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo-lru",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L281",
   :line 281,
   :var-type "function",
   :arglists ([f] [f limit] [f limit base]),
   :doc "DEPRECATED: Please use clojure.core.memoize/lru instead.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo-lru"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo-lu",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L388",
   :line 388,
   :var-type "function",
   :arglists ([f] [f limit] [f limit base]),
   :doc "DEPRECATED: Please use clojure.core.memoize/lu instead.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo-lu"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo-swap!",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L125",
   :line 125,
   :var-type "function",
   :arglists ([f base]),
   :doc
   "Takes a core.memo-populated function and a map and replaces the memoization cache\nwith the supplied map.  This is potentially some serious voodoo,\nsince you can effectively change the semantics of a function on the fly.\n\n    (def id (memo identity))\n    (memo-swap! id '{[13] :omg})\n    (id 13)\n    ;=> :omg\n\nWith great power comes ... yadda yadda yadda.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo-swap!"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memo-ttl",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L342",
   :line 342,
   :var-type "function",
   :arglists ([f] [f limit] [f limit base]),
   :doc "DEPRECATED: Please use clojure.core.memoize/ttl instead.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memo-ttl"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "memoized?",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L102",
   :line 102,
   :var-type "function",
   :arglists ([f]),
   :doc
   "Returns true if a function has an core.memo-placed cache, false otherwise.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/memoized?"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "snapshot",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L92",
   :line 92,
   :var-type "function",
   :arglists ([memoized-fn]),
   :doc
   "Returns a snapshot of a core.memo-placed memoization cache.  By snapshot\nyou can infer that what you get is only the cache contents at a\nmoment in time.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/snapshot"}
  {:raw-source-url
   "https://github.com/clojure/core.memoize/raw/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj",
   :name "ttl",
   :file "src/main/clojure/clojure/core/memoize.clj",
   :source-url
   "https://github.com/clojure/core.memoize/blob/0f82bdd867600e7f526b0688aac6e9b82f70e5ee/src/main/clojure/clojure/core/memoize.clj#L353",
   :line 353,
   :var-type "function",
   :arglists ([f] [f base] [f tkey threshold] [f base key threshold]),
   :doc
   "Unlike many of the other core.memo memoization functions,\n`memo-ttl`'s cache policy is time-based rather than algortihmic\nor explicit.  When memoizing a function using `memo-ttl` you\nshould provide a **T**ime **T**o **L**ive parameter in\nmilliseconds.\n\n    (require '[clojure.core.memoize :as memo])\n\n    (def id (memo/ttl identity :ttl/threshold 5000))\n\n    (id 42)\n    (snapshot id)\n    ;=> {[42] 42}\n\n    ... wait 5 seconds ...\n    (id 43)\n    (snapshot id)\n    ;=> {[43] 43}\n\nThe expired cache entries will be removed on each cache **miss**.",
   :namespace "clojure.core.memoize",
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/ttl"}
  {:name "PluggableMemoization",
   :var-type "type",
   :namespace "clojure.core.memoize",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/PluggableMemoization",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "RetryingDelay",
   :var-type "type",
   :namespace "clojure.core.memoize",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/core.memoize//index.html#clojure.core.memoize/RetryingDelay",
   :source-url nil,
   :raw-source-url nil,
   :file nil})}
