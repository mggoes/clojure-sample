(ns mutability-atoms.a5-mutability
  (:use [clojure pprint])
  (:require [mutability-atoms.modelo :as m.model]
            [mutability-atoms.logic :as m.logic]))

;(let [hospital (m.model/novo-hospital)]
;  (pprint hospital))

;===================================================
;Mutabilidade
(defn simula-um-dia
  []
  (def hospital (m.model/novo-hospital))
  (def hospital (m.logic/chega-em hospital :espera "111"))
  (def hospital (m.logic/chega-em hospital :espera "222"))
  (def hospital (m.logic/chega-em hospital :espera "333"))
  ;(pprint hospital)

  (def hospital (m.logic/chega-em hospital :laboratorio1 "444"))
  (def hospital (m.logic/chega-em hospital :laboratorio3 "555"))
  ;(pprint hospital)

  (def hospital (m.logic/atende hospital :laboratorio1))
  (def hospital (m.logic/atende hospital :espera))
  ;(pprint hospital)

  (def hospital (m.logic/chega-em hospital :espera "666"))
  (def hospital (m.logic/chega-em hospital :espera "777"))
  (def hospital (m.logic/chega-em hospital :espera "888"))
  (pprint hospital)
  (def hospital (m.logic/chega-em hospital :espera "999"))
  (pprint hospital))

(simula-um-dia)