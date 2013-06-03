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

Changes from v0.5.2
-------------------

The v0.5.3 version of core.memoize is updated to work with the v0.6.3 version of [core.cache](http://github.com/clojure/core.cache/wiki).  In addition, the following bug has been fixed:

 * [CMEMOIZE-5](http://dev.clojure.org/jira/browse/CMEMOIZE-5): Changed to never assume that the value retrieved from the cache is non-nil.  This was causing an occassional issue with TTL caches that timed out between checking for a value and retrieving it.


Plans
-----

The following capabilities are under design, development, or consideration for future versions of core.memoize:

* LIRS backed memoization
* `SoftCache` backed memoization
* A [defn-memo](https://github.com/richhickey/clojure-contrib/blob/1c805bd0e515ea57028721ea54e6db4b0c791e20/src/main/clojure/clojure/contrib/def.clj#L143) macro
* A [MapMaker](http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/MapMaker.html) style ctor interface
* test.generative usage
* More documentation and examples

More planning is needed around capabilities not listed nor thought of.

