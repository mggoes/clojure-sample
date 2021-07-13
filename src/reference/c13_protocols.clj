(ns reference.c13-protocols
  (:import (clojure.lang PersistentArrayMap)))

;==============================================================
;PROTOCOLS
;Function definitions must take at least one argument
(defprotocol MyProtocol
  "Some doc String"
  (foo [x] "foo docs")
  ;Defining multiple arity
  (bar-me [x] [x y] "bar-me docs"))

(deftype Foo [a b c]
  MyProtocol
  (foo [x] a)
  (bar-me [x] b)
  (bar-me [x y] (+ y c)))

(def f (Foo. 1 2 3))
(println (foo f))
(println (bar-me f))
(println (bar-me f 3))

;Extend
;It is possible to extend types with protocols using a map
(extend String
  MyProtocol
  {:foo    (fn [x] (str x "_foo"))
   :bar-me (fn ([x] (str x "_bar_me1"))
             ([x y] (str x "_bar_me2")))})

(println (foo "Some String"))
(println (bar-me "Some String"))
(println (bar-me "Some String" "Another"))

;Extend type
;More convenient for inline definitions
(extend-type Long
  MyProtocol
  (foo [x] (str "foo " x))
  (bar-me ([x] (str "bar-me " x)) ([x y] (str "bar-me " x " " y))))

(println (foo 10))
(println (bar-me 10))
(println (bar-me 10 20))

;Extends via metadata
(defprotocol Component
  ;Configures this protocol to be extended via metadata
  :extend-via-metadata true
  (start [c] [c a]))

(def m {:name "db"})
;Defining function start in the metadata
;This will make the PersistentMap m to extend Component protocol
(def com (with-meta m {`start (fn ([c] "one arg")
                                ([c a] "two args"))}))
(println (start com))
(println (start com "com"))

;or
(def m2 ^{`start (constantly "started 2")} {:name "db"})
(def com2 m2)
(println (start com2))