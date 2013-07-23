# LU memoization

The least-used memoization strategy (sometimes called "Least Frequently Used") is a [variant of LRU](./LRU.md) that evicts items that are used least frequently once its threshold has been exceeded.

> In simple terms, the LU cache will remove the element in the cache that has been accessed the least, regardless of time.

## General use

To create a core.memoize LU-backed memoized function use the `clojure.core.memoize/lu` function with an optional seed map or a `:lu/threshold` parameter:

    (memo/lu function <seed> <:lu/threshold number>)

Example code is as follows:

```clojure
(ns your.lib
  (:require [clojure.core.memoize :as memo]))

(def memoized-fun
  (memo/lu identity {} :lu/threshold 3))
```

The default `:lu/threshold` value is 32 and refers to the number of elements in the cache required before the LU logic is applied.

Please read the [clojure.core.cache information regarding LU caches](https://github.com/clojure/core.cache/wiki/LU) for more detailed information, use cases and usage patterns.

As always, you should measure your system's characteristics to determine the best eviction strategy for your purposes.
