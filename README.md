clojure.core.memoize
========================================

core.memoize is a new Clojure contrib library providing the following features:

* An underlying `PluggableMemoization` protocol that allows the use of customizable and swappable memoization caches that adhere to the synchronous `CacheProtocol` found in [core.cache](http://github.com/clojure/core.cache)

* Memoization builders for implementations of common caching strategies, including:
  - First-in-first-out (`memo-fifo`)
  - Least-recently-used (`memo-lru`)
  - Least-used (`memo-lu`)
  - Time-to-live (`memo-ttl`)
  - Naive cache (`memo`) that duplicates the functionality of Clojure's `memoize` function

* Functions for manipulating the memoization cache of `core.memoize` backed functions



Releases and Dependency Information
========================================

Latest stable release: 0.5.3

* [All Released Versions](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22core.memoize%22)

* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~core.memoize~~~)

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

    [org.clojure/core.memoize "0.5.3"]

[Maven](http://maven.apache.org/) dependency information:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>core.memoize</artifactId>
      <version>0.5.3</version>
    </dependency>



Example Usage
========================================

```clojure
    (use 'clojure.core.memoize)
   
     (def id (memo-lu #(do (Thread/sleep 5000) (identity %)) 3))

    (id 42)
    ; ... waits 5 seconds
    ;=> 42

    (id 42)
    ; instantly
    ;=> 42 
```

Refer to docstrings in the `clojure.core.memoize` namespace.



Developer Information
========================================

* [GitHub project](https://github.com/clojure/core.memoize)

* [Bug Tracker](http://dev.clojure.org/jira/browse/memoize)

* [Continuous Integration](http://build.clojure.org/job/core.memoize/)

* [Compatibility Test Matrix](http://build.clojure.org/job/core.memoize-test-matrix/)



Change Log
====================

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

Copyright (c) Rich Hickey and Michael Fogus, 2012. All rights reserved.  The use and distribution terms for this software are covered by the Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) which can be found in the file epl-v10.html at the root of this distribution. By using this software in any fashion, you are agreeing to be bound bythe terms of this license.  You must not remove this notice, or any other, from this software.

