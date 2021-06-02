(ns generators-property-tests.logic
  (:require [generators-property-tests.model :as g.model]
            [schema.core :as s]))

(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
          (< 5)))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (IllegalStateException. "Nao cabe mais ninguem neste departamento."))))

(s/defn atende :- g.model/Hospital
  [hospital :- g.model/Hospital departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima :- (s/maybe g.model/PacienteID)
  [hospital :- g.model/Hospital departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(defn mesmo-tamanho?
  [hospital outro-hospital de para]
  (= (+ (count (get outro-hospital de)) (count (get outro-hospital para)))
     (+ (count (get hospital de)) (count (get hospital para)))))

(s/defn transfere :- g.model/Hospital
  [hospital :- g.model/Hospital de :- s/Keyword para :- s/Keyword]
  {:pre  [(contains? hospital de) (contains? hospital para)]
   :post [(mesmo-tamanho? hospital % de para)]}
  (if-let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))
    hospital))

(defn total-pacientes
  [hospital]
  (reduce + (map count (vals hospital))))