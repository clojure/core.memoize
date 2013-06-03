core.memoize v0.5.3 Release Notes
=================================

core.memoize is a Clojure contrib library providing the following features:

* An underlying `PluggableMemoization` protocol that allows the use of customizable and swappable memoization caches that adhere to the synchronous `CacheProtocol` found in [core.cache](http://github.com/clojure/core.cache)

* Memoization builders for implementations of common caching strategies, including:
  - First-in-first-out (`memo-fifo`)
  - Least-recently-used (`memo-lru`)
  - Least-used (`memo-lu`)
  - Time-to-live (`memo-ttl`)
  - Naive cache (`memo`) that duplicates the functionality of Clojure's `memoize` function

* Functions for manipulating the memoization cache of `core.memoize` backed functions

Places
------

* [Source code](https://github.com/clojure/core.memoize)
* [Ticket system](http://dev.clojure.org/jira/browse/CMEMOIZE)
* [API Reference](https://clojure.github.com/core.memoize)
