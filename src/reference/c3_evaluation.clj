(ns reference.c3-evaluation)

;Empty list
(println (class ()))

;Evaluating a form
(eval (println "Test"))
(eval (def v [1 2 3]))
(println v)
