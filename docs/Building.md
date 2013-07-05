Building core.memoize
=====================

1. Clone the [core.memoize git repository](http://github.com/clojure/core.memoize) or download its [source archive](https://github.com/clojure/core.memoize/zipball/master)

2. Run `mvn package` to generate a Jar file

3. Run `mvn install` to install the Jar into your local Maven repository

To test that the build succeeded try:

    mvn clojure:repl

This will launch a Clojure REPL.  Try the following to exercise core.memoize:

```clojure
(require '[clojure.core.memoize :as memo])

(def f (memo/memo #(do (Thread/sleep 5000) %)))

(f 42)
;; wait 5 seconds
;;=> 42
```

Subsequent calls of the `f` function with the value `42` should return instantly.
