(ns reference.c18-refs-and-transactions
  (:use [clojure.pprint]))

;==============================================================
;REF
(def params (ref {}))
(pprint @params)

;ALTER
;Changes the value of the ref
;Muste be called within a transaction
(defn change!
  [m key value]
  (assoc m key value))

;Starts a transaction
(dosync
  (alter params change! :p1 "Param 1"))
(pprint @params)

;REF-SET
;Similar to alter
;Changes the value of the ref
(dosync
  (ref-set params (assoc @params :p2 "Param 2")))
(pprint @params)

;ENSURE
;Protects the ref from modification by another transaction
(def t1 (future
          (dosync
            (println "t1 is ensuring the ref...")
            (ensure params)
            (println "t1 is going to sleep...")
            (Thread/sleep 5000)
            (println "t1 woke up! Change ref value...")
            (alter params change! :p3 "Param 3")
            (println "t1 changed ref value!"))
          @params))

(def t2 (future
          (dosync
            (println "t2 is ensuring the ref...")
            (ensure params)
            (println "t2 is going to sleep...")
            (Thread/sleep 5000)
            (println "t2 woke up! Change ref value...")
            (alter params change! :p4 "Param 4")
            (println "t2 changed ref value!"))
          @params))

(pprint @t1)
(pprint @t2)
