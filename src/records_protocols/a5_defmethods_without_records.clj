(ns records-protocols.a5-defmethods-without-records
  (:use clojure.pprint)
  (:require [records-protocols.logic :as r.logic]))

(defn tipo-de-autorizador
  [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)]
    ;cond eh semelhante a um switch case
    (cond (= :urgente situacao) :sempre-autorizado
          (contains? paciente :plano) :plano-de-saude
          :else :credito-minimo)))

(defmulti deve-assinar-pre-autorizacao? tipo-de-autorizador)

(defmethod deve-assinar-pre-autorizacao? :sempre-autorizado
  [_]
  false)

(defmethod deve-assinar-pre-autorizacao? :plano-de-saude
  [pedido]
  (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))

(defmethod deve-assinar-pre-autorizacao? :credito-minimo
  [pedido]
  (>= (:valor pedido 0) 50))

(let [particular {:id 1 :nome "Paciente Particular" :nascimento "10/01/1993" :situacao :urgente}
      plano {:id 2 :nome "Paciente Plano De Saude" :nascimento "10/01/1993" :situacao :urgente :plano [:raio-x :ultrassom]}]
  (pprint (deve-assinar-pre-autorizacao? {:paciente particular :valor 1000 :procedimento :coleta}))
  (pprint (deve-assinar-pre-autorizacao? {:paciente plano :valor 1000 :procedimento :coleta})))

(let [particular {:id 1 :nome "Paciente Particular" :nascimento "10/01/1993" :situacao :normal}
      plano {:id 2 :nome "Paciente Plano De Saude" :nascimento "10/01/1993" :situacao :normal :plano [:raio-x :ultrassom]}]
  (pprint (deve-assinar-pre-autorizacao? {:paciente particular :valor 1000 :procedimento :coleta}))
  (pprint (deve-assinar-pre-autorizacao? {:paciente plano :valor 1000 :procedimento :coleta})))