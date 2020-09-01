clojure.core.memoize
========================================

[core.memoize](https://github.com/clojure/core.memoize) is a Clojure contrib library providing the following features:

* An underlying `PluggableMemoization` protocol that allows the use of customizable and swappable memoization caches that adhere to the synchronous `CacheProtocol` found in [core.cache](http://github.com/clojure/core.cache)

* Memoization builders for implementations of common caching strategies, including:
  - First-in-first-out (`clojure.core.memoize/fifo`)
  - Least-recently-used (`clojure.core.memoize/lru`)
  - Least-used (`clojure.core.memoize/lu`)
  - Time-to-live (`clojure.core.memoize/ttl`)
  - Naive cache (`memo`) that duplicates the functionality of Clojure's `memoize` function

* Functions for manipulating the memoization cache of `core.memoize` backed functions



Releases and Dependency Information
========================================

This project follows the version scheme MAJOR.MINOR.COMMITS where MAJOR and MINOR provide some relative indication of the size of the change, but do not follow semantic versioning. In general, all changes endeavor to be non-breaking (by moving to new names rather than by breaking existing names). COMMITS is an ever-increasing counter of commits since the beginning of this repository.

Latest stable release: 1.0.236

* [All Released Versions](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22core.memoize%22)

* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~core.memoize~~~)

[CLI/`deps.edn`](https://clojure.org/reference/deps_and_cli) dependency information:
```clojure
org.clojure/core.memoize {:mvn/version "1.0.236"}
```

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

    [org.clojure/core.memoize "1.0.236"]

[Maven](http://maven.apache.org/) dependency information:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>core.memoize</artifactId>
      <version>1.0.236</version>
    </dependency>



Example Usage
========================================

```clojure
    (ns my.cool.lib
      (:require clojure.core.memoize))

    (def id (clojure.core.memoize/lu
	          #(do (Thread/sleep 5000) (identity %))
			  :lu/threshold 3))

    (id 42)
    ; ... waits 5 seconds
    ;=> 42

    (id 42)
    ; instantly
    ;=> 42
```

Refer to docstrings in the `clojure.core.memoize` namespace for more information.



Developer Information
========================================

* [GitHub project](https://github.com/clojure/core.memoize)

* [Bug Tracker](http://clojure.atlassian.net/browse/CMEMOIZE)

* [Continuous Integration](http://build.clojure.org/job/core.memoize/)

* [Compatibility Test Matrix](http://build.clojure.org/job/core.memoize-test-matrix/)



Change Log
====================
* Release 1.0.236 on 2020.04.13
  * Switch to 1.0.x versioning [CMEMOIZE-29](http://clojure.atlassian.net/browse/CMEMOIZE-29).
  * Update `core.cache` dependency version from 0.8.2 to 1.0.207.
  * Fixes [CMEMOIZE-9](http://clojure.atlassian.net/browse/CMEMOIZE-9) - adds `memo-reset!` and deprecates 2-arity version of `memo-swap!`; adds 3+-arity version of `memo-swap!` to behave more like a `swap!` operation on the underlying cache
* Release 0.8.2 on 2019.11.01 (to match core.cache again)
  * Update `core.cache` dependency version from 0.7.2 to 0.8.2.
  * Fixes [CMEMOIZE-28](http://clojure.atlassian.net/browse/CMEMOIZE-28) - provides `memoizer` as a more convenient way to build custom cached functions that may provide a seed hash map of arguments to return values. `build-memoizer` should be considered deprecated at this point.
  * Fixes [CMEMOIZE-27](http://clojure.atlassian.net/browse/CMEMOIZE-27) - the `seed` function on `PluggableMemoization` now makes elements derefable (this case was missed when [CMEMOIZE-18](http://clojure.atlassian.net/browse/CMEMOIZE-18) was fixed)
* Release 0.7.2 on 2019.06.13
  * Fixes [CMEMOIZE-26](http://clojure.atlassian.net/browse/CMEMOIZE-26) - zero-arity function cache could not be replaced by `memo-swap!` (discovered by Teemu Kaukoranta)
  * Updated core.cache dependency version from 0.7.1 to 0.7.2
  * Updated test matrix locally to include Clojure 1.10.1, 1.11 master
* Release 0.7.1 on 2018.03.02
  * Fixes [CMEMOIZE-15](http://clojure.atlassian.net/browse/CMEMOIZE-15) - edge case where cache miss/lookup cross an eviction boundary (Ryan Fowler/Colin Jones)
  * Updated core.cache dependency version from 0.7.0 to 0.7.1 (for TTLCacheQ bug fix)
* Release 0.7.0 on 2018.03.01
  * Fixes [CMEMOIZE-22](http://clojure.atlassian.net/browse/CMEMOIZE-22) - add `:clojure.core.memoize/args-fn` metadata support for memoizing functions which have one or more arguments that should not contribute to the cache key for calls
  * Fixes [CMEMOIZE-20](http://clojure.atlassian.net/browse/CMEMOIZE-20) - add `lazy-snapshot` function
  * Fixes [CMEMOIZE-18](http://clojure.atlassian.net/browse/CMEMOIZE-18) - automatically makes seed map values `deref`-able to match documentation and comply with core.memoize's world view
  * Cleanup/improve/fix tests
  * Add multi-version testing locally via Leiningen
  * Jump to 0.7.0 to match core.cache since these two libraries are so closely in sync
* Release 0.5.9 on 2016.03.28
  * Updated core.cache dependency version from 0.6.4 to 0.6.5
* Release 0.5.8 on 2015.11.06
  * Fixes [CMEMOIZE-21](http://clojure.atlassian.net/browse/CMEMOIZE-21) - race condition in delay
* Release 0.5.7 on 2015.01.12
  * Fixes [CMEMOIZE-8](http://clojure.atlassian.net/browse/CMEMOIZE-8)
  * Fixes [CMEMOIZE-13](http://clojure.atlassian.net/browse/CMEMOIZE-13)
  * Updated core.cache dependency version from 0.6.3 to 0.6.4
* Release 0.5.6 on 2013.06.28
  * Added optional args to `memo-clear!`.
  * Widened contract on factory functions to accept any callable.
* Release 0.5.5 on 2013.06.14
  * Deprecated `memo-*` APIs
  * Adds new API of form `(cache-type function <base><:cache-type/threshold int>)`
* Release 0.5.4 on 2013.06.03
  * Fixes [CMEMOIZE-5](http://clojure.atlassian.net/browse/CMEMOIZE-5)
  * Fixes [CMEMOIZE-2](http://clojure.atlassian.net/browse/CMEMOIZE-2)
* Release 0.5.3 on 2013.03.18
  * Works with core.cache v0.6.3
* Release 0.5.2 on 2012.07.13
  * Works with core.cache v0.6.1
* Release 0.5.1 on 2011.12.13
  * Removed SoftCache memoization
* Release 0.5.0 on 2011.12.13
  * Rolled in basis of Unk


Copyright and License
========================================

Copyright (c) Rich Hickey and Michael Fogus, 2012, 2013. All rights reserved.  The use and distribution terms for this software are covered by the Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) which can be found in the file epl-v10.html at the root of this distribution. By using this software in any fashion, you are agreeing to be bound bythe terms of this license.  You must not remove this notice, or any other, from this software.
