(ns language.reader-conditionals)

;Allow different dialects to share common code
;The file must have a .cljc extension

;STANDARD READER CONDITIONAL
;Allows to define expressions according to the platform
#?(:clj     (defn execute [] (println "Clojure"))
   :cljs    (defn execute [] (js/console))
   :default (defn execute [] (println "Default")))

(execute)

;SPLICING READER CONDITIONAL
;Allows to define expressions according to the platform inside another expression
(println (list #?@(:clj  [5 6 7 8]
                   :cljs [1 2 3 4])))
