# TTL memoization

The time-to-live cache is one that evicts items that are older than a time-to-live threshold (in milliseconds).

## General use

To create a core.memoize TTL-backed memoized function use the `clojure.core.memoize/ttl` function with an optional seed map or a `:ttl/threshold` parameter:

    (memo/ttl function <seed> <:ttl/threshold number>)

Example code is as follows:

```clojure
(ns your.lib
  (:require [clojure.core.memoize :as memo]))

(def memoized-fun
  (memo/ttl identity {} :ttl/threshold 3))
```

The default `:ttl/threshold` value is 2 seconds before the TTL logic is applied.

Please read the [clojure.core.cache information regarding TTL caches](https://github.com/clojure/core.cache/wiki/TTL) for more detailed information, use cases and usage patterns.

As always, you should measure your system's characteristics to determine the best eviction strategy for your purposes.
