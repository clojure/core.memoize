(defproject core.memoize "0.5.3-SNAPSHOT"
  :description "A memoization library for Clojure."
  :dependencies [[org.clojure/clojure "1.5.0-master-SNAPSHOT"]
                 [org.clojure/core.cache "0.6.3"]]
  :plugins [[lein-swank "1.4.4"]
            [lein-marginalia "0.7.1"]]
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :source-paths ["src/main/clojure"]
  :test-paths   ["src/test/clojure"])
