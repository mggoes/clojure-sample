(ns language.core-functions)

;Complement creates a function that receives the same parameters of the original function, executes it, negates the result and returns it.
(def not-empty? (complement empty?))
(println (not-empty? []))
