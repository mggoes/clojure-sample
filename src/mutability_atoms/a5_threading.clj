(ns mutability-atoms.a5-threading
  (:use [clojure pprint])
  (:require [mutability-atoms.modelo :as m.model]
            [mutability-atoms.logic :as m.logic]))

(defn chega-em!
  [hospital pessoa]
  (swap! hospital m.logic/chega-em :espera pessoa))

(defn transfere!
  [hospital de para]
  (swap! hospital m.logic/transfere de para))

(defn simula-um-dia
  []
  (let [hospital (atom (m.model/novo-hospital))]
    (chega-em! hospital "Pessoa 1")
    (chega-em! hospital "Pessoa 2")
    (chega-em! hospital "Pessoa 3")
    (chega-em! hospital "Pessoa 4")
    (transfere! hospital :espera :laboratorio1)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :laboratorio2 :laboratorio3)
    (pprint hospital)))

(simula-um-dia)