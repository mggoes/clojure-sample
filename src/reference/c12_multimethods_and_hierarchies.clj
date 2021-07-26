(ns reference.c12-multimethods-and-hierarchies
  (:import (java.util ArrayList Map HashMap Collection)))

;HIERARCHIES
(println ::test)

;DERIVE
;Defines a relationship between parent and child
;Must be namespaced
;In this case, rect has a relationship with shape
(derive ::rect ::shape)
(derive ::rect ::shape2)
(derive ::square ::shape)

;PARENTS
;Returns the immediate parents
(println (parents ::rect))
(println (parents ::square))
(println (parents ArrayList))

;ANCESTORS
;Returns the immediate and indirect parents
(println (ancestors ::shape))
(println (ancestors ::rect))
(println (ancestors ArrayList))

;DESCENDANTS
;Returns the immediate and indirect children
(println (descendants ::shape))

;ISA?
;Checks if child is derived from parent
(println (isa? ::square ::shape))
(println (isa? ::rect ::shape2))

;MULTIMETHOD
;Uses isa? for dispatch value matches
(derive Map ::collection)
(derive Collection ::collection)

;Uses a function to define which method will be invoked
(defmulti foo class)
(defmethod foo ::collection [c] :a-collection)
(defmethod foo String [s] :a-string)

(println (foo []))
(println (foo (HashMap.)))
(println (foo "bar"))

;PREFER-METHOD
;It is used to disambiguating
(defmulti bar (fn [x y] [x y]))
(defmethod bar [::rect ::shape] [x y] :rect-shape)
(defmethod bar [::shape ::rect] [x y] :shape-rect)
;(println (bar ::rect ::rect)) ;Will throw an exception

;This will set "bar [::rect ::shape]" with higher priority over "bar [::shape ::rect]"
(prefer-method bar [::rect ::shape] [::shape ::rect])
(println (bar ::rect ::rect))

;DEFAULT
(defmethod bar :default [x y] :default)
(println (bar ::test ::test))
