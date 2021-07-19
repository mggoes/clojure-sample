(ns reference.c17-vars-and-environments)

;==============================================================
;VARS
;Vars are static by default
(def x)
(println x)

(def y 1)
(println y)

;Vars can be marked as dynamic
;This allows per-thread binding, in other words, each thread can bind this var to a different value
(def ^:dynamic a 2)
(println a)

;Per-thread binding
;Bindings created by binding macro cannot be seen by another thread
(binding [a 3]
  (println a))
(println a)

(println #'a)
