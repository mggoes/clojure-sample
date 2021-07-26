(ns reference.c2-repl-and-main)

;CLJ
;Command clj invoke clojure.main
;Run a clojure file: clj -M c2_repl_and_main.clj
(println "Clojure REPL and main")

;Passing arguments
;Run a clojure file: clj -M c2_repl_and_main.clj arg1 arg2 arg3
(println *command-line-args*)

;Run using java command: java -cp clojure.jar clojure.main -m reference.c2-repl-and-main
;https://clojure.github.io/clojure/clojure.main-api.html#clojure.main/main
(defn -main
  []
  (println "Main function"))
