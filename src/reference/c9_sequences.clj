(ns reference.c9-sequences)

;SEQUENCE
(println (seq [1 2 3 4 5]))
(println (lazy-seq [1 2 3 4 5]))

(def s (seq [1 2 3 4 5]))
(println (first s))
(println (rest s))
(println (cons 0 s))
