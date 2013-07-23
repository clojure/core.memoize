core.memoize
============

## Table of Topics

* Overview (this page)
* [Including core.memoize in your projects](./Including.md)
* [Example usages of core.memoize](./Using.md)
* [Building core.memoize](./Building.md)

## The problem

Value caching is sometimes needed. This need is often driven by the desire is to avoid calculating expensive operations such as inherently costly algorithms more often than necessary.  The naive solution for this need is to perform some expensive operation once and cache the result.  Therefore, whenever the same calculation is needed in the future it can be retrieved from cache more quickly than simply recalculating from scratch.

Clojure provides a default way to cache the results of function calls using the `memoize` function:

```clojure
(defn slow-calc []
  (Thread/sleep 5000)
  42)

(def memo-calc (memoize slow-calc))

(memo-calc)
;; wait 5 seconds
;;=> 42

(memo-calc)
;; instantly
;;=> 42
```

While appropriate for many problems, the naive caching provided by `memoize` can consume available memory as it never releases stored values.  Therefore, the ideal situation is to expunge stored results that have expired, meant for single-use or less likely to be needed again.  There are many general-purpose and domain-specific strategies for efficient cache population and eviction. The core.memoize library provides implementations of common caching strategies for use in memoization scenarios.

## Overview

core.memoize is a Clojure contrib library providing the following features:

* Implementations of some common memoization caching strategies, including:
  - [First-in-first-out](./FIFO.md)
  - [Least-recently-used](./LRU.md)
  - [Least-used](./LU.md)
  - [Time-to-live](./TTL.md)

*The implementation of core.memoize is based on and heavily influenced by the excellent ['Memoize done right'](http://kotka.de/blog/2010/03/memoize_done_right.html) by Meikel Brandmeyer*

## Places

* [Source code](https://github.com/clojure/core.memoize)
* [Ticket system](http://dev.clojure.org/jira/browse/CMEMOIZE)
* [Examples and documentation](http://github.com/clojure/core.memoize/wiki)
