clojure.core.memoize
========================================

[core.memoize](https://github.com/clojure/core.memoize) is a new Clojure contrib library providing the following features:

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

Latest stable release: 0.5.5

* [All Released Versions](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22core.memoize%22)

* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~core.memoize~~~)

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

    [org.clojure/core.memoize "0.5.5"]

[Maven](http://maven.apache.org/) dependency information:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>core.memoize</artifactId>
      <version>0.5.5</version>
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

* [Bug Tracker](http://dev.clojure.org/jira/browse/CMEMOIZE)

* [Continuous Integration](http://build.clojure.org/job/core.memoize/)

* [Compatibility Test Matrix](http://build.clojure.org/job/core.memoize-test-matrix/)



Change Log
====================

* Release 0.5.5 on 2013.06.14
  * Deprecated `memo-*` APIs
  * Adds new API of form `(cache-type function <base><:cache-type/threshold int>)`
* Release 0.5.4 on 2013.06.03
  * Fixes [CMEMOIZE-5](http://dev.clojure.org/jira/browse/CMEMOIZE-5)
  * Fixes [CMEMOIZE-2](http://dev.clojure.org/jira/browse/CMEMOIZE-2)
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

