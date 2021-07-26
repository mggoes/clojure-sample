(ns reference.c11-transducers)

;TRANSDUCERS
;It is a transformation of one reduce function to another
;Some functions produce a transducer when the input collection is omitted
(def f (filter odd?))
(println f)

(def m (map inc))
(println m)

(def t (take 5))
(println t)

(def xf (comp
          (filter odd?)
          (map inc)))

;Using transducers
(println (transduce xf + (range 5)))
(println (transduce xf + 100 (range 5)))

;Eduction
;Returns a reducible composition of all transducers to be applied to the collection
(def iter (eduction xf (range 5)))
(println (reduce + 0 iter))

;Into
;Transforms an input to an output using a transducer
(println (into [] xf (range 50)))

;Sequence
;Creates a sequence from the application of a transducer
(println (sequence xf (range 50)))
