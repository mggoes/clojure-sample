(ns language.equality)

;= is used to compare symbols, objects, collections, etc
(println (= "name" "name"))
(println (= [0 1 2] [0 2 1]))
(println (= [0 1 2] [0 1 2]))
(println (= [0 1 2] '(0 1 2)))

;== is only used for numbers
(println (== 0 0.0))


