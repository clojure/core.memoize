core.memoize v0.5.2 Release Notes
=================================

core.memoize is a new Clojure contrib library providing the following features:

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
* [Ticket system](http://clojure.atlassian.net/browse/CMEMOIZE)
* [API Reference](https://clojure.github.com/core.memoize)

Changes from v0.5.1
-------------------

The v0.5.2 version of core.memoize is updated to work with the v0.6.1 version of [core.cache](http://github.com/clojure/core.cache/wiki)

Plans
-----

The following capabilities are under design, development, or consideration for future versions of core.memoize:

* LIRS backed memoization
* `SoftCache` backed memoization
* Remove references to Unk
* A [defn-memo](https://github.com/richhickey/clojure-contrib/blob/1c805bd0e515ea57028721ea54e6db4b0c791e20/src/main/clojure/clojure/contrib/def.clj#L143) macro
* A [MapMaker](http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/MapMaker.html) style ctor interface
* test.generative usage
* More documentation and examples

More planning is needed around capabilities not listed nor thought of.

