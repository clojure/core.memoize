core.memoize v0.5.1 Release Notes
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

core.memoize is based on a library named Unk, found at http://github.com/fogus/unk that is planned for deprecation.


Absorb
------

You can use core.memoize in your [Leiningen](https://github.com/technomancy/leiningen) and [Cake](https://github.com/flatland/cake) projects with the following `:dependencies` directive in your `project.clj` file:

    [org.clojure/core.memoize "0.5.1"]

For Maven-driven projects, use the following slice of XML in your `pom.xml`'s `<dependencies>` section:

    <dependency>
	  <groupId>org.clojure</groupId>
	  <artifactId>core.memoize</artifactId>
	  <version>0.5.1</version>
	</dependency>

Enjoy!


Places
------

* [Source code](https://github.com/clojure/core.memoize)
* [Ticket system](http://dev.clojure.org/jira/browse/CMEMOIZE)
* [Announcement](http://groups.google.com/group/clojure/browse_frm/thread/69d08572ab265dc7)
* Examples and documentation -- in progress

Changes from Unk
-------------------

The v0.5.1 version of core.memoize is based almost wholly on the final version of Unk, with the following changes:

* All cache factory functions have been moved to core.cache
* The `SoftCache` backed implementation was buggy and removed for now

Plans
-----

The following capabilities are under design, development, or consideration for future versions of core.memoize:

* LIRS backed memoization
* A [defn-memo](https://github.com/richhickey/clojure-contrib/blob/1c805bd0e515ea57028721ea54e6db4b0c791e20/src/main/clojure/clojure/contrib/def.clj#L143) macro
* A [MapMaker](http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/MapMaker.html) style ctor interface
* Reimplementation of a cache based on soft references
* test.generative usage
* Deprecation of Unk
* Documentation and examples

More planning is needed around capabilities not listed nor thought of.
