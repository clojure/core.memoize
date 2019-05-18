core.memoize v0.5.5 Release Notes
=================================

[core.memoize](https://github.com/clojure/core.memoize) is a Clojure contrib library providing the following features:

* An underlying `PluggableMemoization` protocol that allows the use of customizable and swappable memoization caches that adhere to the synchronous `CacheProtocol` found in [core.cache](http://github.com/clojure/core.cache)

* Memoization builders for implementations of common caching strategies, including:
  - First-in-first-out (`clojure.core.memoize/fifo`)
  - Least-recently-used (`clojure.core.memoize/lru`)
  - Least-used (`clojure.core.memoize/lu`)
  - Time-to-live (`clojure.core.memoize/ttl`)
  - Naive cache (`memo`) that duplicates the functionality of Clojure's `memoize` function

* Functions for manipulating the memoization cache of `core.memoize` backed functions

Usage
-----

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

    [org.clojure/core.memoize "0.5.5"]

[Maven](http://maven.apache.org/) dependency information:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>core.memoize</artifactId>
      <version>0.5.5</version>
    </dependency>

Places
------

* [Source code](https://github.com/clojure/core.memoize)
* [Ticket system](http://clojure.atlassian.net/browse/CMEMOIZE)
* [API Reference](https://clojure.github.com/core.memoize)

Changes from v0.5.4
-------------------

The v0.5.5 version of core.memoize works with the v0.6.3 version of [core.cache](http://github.com/clojure/core.cache/wiki).  In addition, the following bugs have been fixed:

  * Deprecated `memo-*` API
  * Added new API of form `(cache-type function <base> <:cache-type/threshold int>)`

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

