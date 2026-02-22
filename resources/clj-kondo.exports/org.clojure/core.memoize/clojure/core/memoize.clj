(ns clojure.core.memoize)

(defmacro def-deprecated
  "Like defn but adds memo- prefix."
  [sym & tail]
  (list* 'defn (symbol (str "memo-" sym)) tail))
