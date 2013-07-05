# LRU memoization

The least-recently-used memoization strategy is one that evicts items that are accessed least frequently once its threshold has been exceeded.

> In simple terms, the LRU cache will remove the element in the cache that has not been accessed in the longest time.

## General use

To create a core.memoize LRU-backed memoized function use the `clojure.core.memoize/lru` function with an optional seed map or a `:lru/threshold` parameter:

    (memo/lru function <seed> <:lru/threshold number>)

Example code is as follows:

```clojure
    (ns your.lib
      (:require [clojure.core.memoize :as memo]))

    (def memoized-fun
      (memo/lru identity {} :lru/threshold 3))
```

The default `:lru/threshold` value is 32 and refers to the number of elements in the cache required before the LRU logic is applied.

Please read the [clojure.core.cache information regarding LRU caches](https://github.com/clojure/core.cache/wiki/LRU) for more detailed information, use cases and usage patterns.

As always, you should measure your system's characteristics to determine the best eviction strategy for your purposes.
