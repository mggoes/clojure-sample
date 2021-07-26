(ns reference.c7-data-structures)

;NIL
;Same as null
(println nil)

;NUMBERS
;Longs
(println 10)

;Ration
;Will print 22/7, rather than a truncated value
(println (/ 22 7))
(println (/ 10 3))

;BigInt
;We use a postfix N
(println 10N)

;BigDecimal
;We use a postfix M
(println 10M)

;Auto-promotion
;Operations suffixed with apostrophe (') will auto-promote to BigInt upon overflow
(println (+' 1 1))
(println (-' 1 1))

;STRINGS
(println "Some String")

;CHARACTERS
(println \c)

;KEYWORDS
(println :some-key)
(println :prefix/some-key)

;Keywords implements IFn interface and we can use invoke() to retrieve a value from a map with an optional default value
(println (:some-key {:a 1 :b 2} 30))
;Or
(println (get {:a 1 :b 2} :some-key 30))

;SYMBOLS
(def sym "")
(println #'sym)

;COLLECTIONS
;Lists
(def lst '(1 2 3))
(println lst)
(println (count lst))

;Vectors
(def v [1 2 3])
(println v)
(println (count v))
;Implements IFn
(println (v 1))

;Maps
(def m {:a "A" :b "B" :c "C"})
(println m)
(println (count m))
(println (:b m))
;Implements IFn
(println (m :b))

;STRUCTMAPS (Replaced by records)
;Creates a base structure
(defstruct base :fred :ricky)

;Creates a map based on a structure
(def smap (struct-map base :fred "Fred" :lucy "Lucy"))
(println smap)

;It is not possible to remove a base key from a struct map
;(dissoc smap :fred)

;ARRAYMAPS
;Creates an ordered map by keys
(def arrmap (array-map :a "A" :b "B"))
(println arrmap)

;SETS
(def some-set #{1 2 3 4 5})
(println (conj some-set 6))
(println (count some-set))
(println (disj some-set 1))
(println (contains? some-set 1))
(println (get some-set 4))
;Implements IFn
(println (some-set 4))

;Apply unwraps a sequence and apply the function to them as individual arguments
(println (apply sorted-set some-set))
