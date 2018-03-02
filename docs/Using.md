# Using core.memoize

*note: see the page on [including core.memoize](./Including.md) before you begin this section*

## Basic usage pattern

To use the memoization cache implementations you first need to require the proper namespace:

```clojure
(require '[clojure.core.memoize :as memo])
```

Next you can create some function that you'd like to memoize.  For illustrative purposes I'll create one that is intentionally slow:

```clojure
(defn slowly [n]
  (Thread/sleep 5000)
  n)
```

I can then memoize this function using `core.memoize/memo` which is an augmented replacement for `clojure.core/memoize`:

```clojure
(def once-slowly (memo/memo slowly))

(once-slowly 42)
;; wait 5 seconds
;;=> 42

(once-slowly 42)
;; instantly
;;=> 42
```

One advantage of using the `clojure.core.memoize/memo` function over the one provided in Clojure is that the former allows you to evict certain entries as needed, shown next.

### Evicting cached entries

To explicitly evict an element in a memoization cache, use the `clojure.core.memoize/memo-clear!` function:

```clojure
(memo/memo-clear! once-slowly [42])

(once-slowly 42)
;; wait 5 seconds
;;=> 42
```

The argument to `clojure.core.memoize/memo-clear!` is a vector of the precise argument values that you'd like to clear.  You can not pass the argument vector to instead clear the entire memoization cache for a function.

### Overriding the cache keys

By default, the entire argument list is used as a key into the cached results. Sometimes you will want to cache a function where one or more of its arguments don't affect the results directly and you would rather ignore them from a cache key point of view.

You can do that by specifying metadata on the function being cached, telling the memoization functions how to turn the arguments into a cache key:

```clojure
(defn ^{:clojure.core.memoize/args-fn rest}
  fetch-user-from-db
  [db-spec user-id]
  ...)

(def get-user (memo/ttl #'fetch-user-from-db))
```

This tells memoization that the cache key for any given call is `(rest args)` so the `db-spec` argument will not be considered as part of the cache key. The supplied `args-fn` can perform any transformation from an argument list to a cache key but it is expected the most common use will be to remove one or more arguments from the cache key.

Note: because you want memoization to read the metadata from your function, you must pass the Var in, rather than just the function name.

## Using a memoization cache strategy

You could also use a memoization strategy that will expire its entries based on an expiration parameter, shown below:

```clojure
(def sometimes-slowly (memo/ttl slowly :ttl/threshold 10000))

(sometimes-slowly 42)
;; wait 5 seconds
;;=> 42

(sometimes-slowly 42)
;; instantly
;;=> 42
```

The `:ttl/threshold` flag states that entries in the memoization cache older than 10,000 milliseconds (10 seconds) should be evicted from the internal cache.  If you wait some time after running the code above, you should see the function call slow down again:

```clojure
(sometimes-slowly 42)
;; wait 5 seconds
;;=> 42
```

For specific information about eviction policies and thresholds, view the specific documentation for each cache type listed in the next section.

## Builtin cache implementations

core.memoize comes with a number of builtin memoization cache implementations, including (*click through for specific information*):

* [FIFO cache](./FIFO.md)
* [LRU cache](./LRU.md)
* [LU cache](./LU.md)
* [TTL cache](./TTL.md)

Enjoy.
