(ns language.others)

;COMPLEMENT
;Creates a function that receives the same parameters of the original function, executes it, negates the result and returns it.
(def not-empty? (complement empty?))
(println (not-empty? []))

;COMP
(def my-comp (comp inc *))
(println (my-comp 2 3))

;MEMOIZE
;Keeps a cache and returns immediately when the same arguments are used
(defn slow-function
  [msg]
  (Thread/sleep 1000)
  msg)
(println (slow-function "Done 1"))

(def memoized-slow-function (memoize slow-function))
(println (memoized-slow-function "Done 2"))
(println (memoized-slow-function "Done 2"))
(println (memoized-slow-function "Done 3"))

;EVAL
;Evaluates the form
(println (eval '(+ 1 2)))

;READ-STRING
;Reads a string and produces a data structure
(println (read-string "(+ 1 2)"))
(println (eval (read-string "(+ 1 2)")))
