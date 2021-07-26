(ns reference.c20-atoms
  (:use [clojure.pprint]))

;ATOM
;Similar to refs and agents
;Does not need transactions to change the value
(def a (atom {}))
(pprint a)

;SWAP
;Changes atom value
;Receives a function whose return will be the new value of the atom
(defn change-value
  [a k v]
  (assoc a k v))

(swap! a change-value :p1 "Param 1")
(pprint a)
