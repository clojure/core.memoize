# FIFO memoization

A First-In-First-Out memoization cache is one that uses queuing logic for its backing store, expunging the elements at the front of the queue when a predetermined threshold is exceeded.

> In simple terms, the FIFO memoization cache will remove the element that has been in the cache the longest.

## General use

To create a core.memoize FIFO-backed memoized function use the `clojure.core.memoize/fifo` function with an optional seed map or a `:fifo/threshold` parameter:

    (memo/fifo function <seed> <:fifo/threshold number>)

Example code is as follows:

```clojure
    (ns your.lib
      (:require [clojure.core.memoize :as memo]))

    (def memoized-fun
      (memo/fifo identity {} :fifo/threshold 3))
```

The default `:fifo/threshold` value is 32 and refers to the number of elements in the cache required before the FIFO logic is applied.

Please read the [clojure.core.cache information regarding FIFO caches](https://github.com/clojure/core.cache/wiki/FIFO) for more detailed information, use cases and usage patterns.

As always, you should measure your system's characteristics to determine the best eviction strategy for your purposes.
