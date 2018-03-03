#!/bin/sh
#
# We don't auto-test against Clojure 1.6 because the Cognitect test runner
# requires at least 1.7, so we run those tests manually:

time clj -A:1.6:test -e \
"(require 'clojure.core.memoize-test \
          'clojure.core.memoize.regression-test \
          'clojure.core.memoize.deprecation-test)\
 (clojure.test/run-tests 'clojure.core.memoize-test \
                         'clojure.core.memoize.regression-test \
                         'clojure.core.memoize.deprecation-test) \
 (shutdown-agents)"

versions="1.7 1.8 1.9 master"
for v in $versions
do
  time clj -A:test:runner:$v
done
