(ns records-protocols.a4-defmulti-defmethod
  (:use clojure.pprint)
  (:require [records-protocols.logic :as r.logic]))

(defrecord PacienteParticular [id nome nascimento situacao])
(defrecord PacientePlanoDeSaude [id nome nascimento situacao plano])

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(defn nao-eh-urgente?
  [paciente]
  (not= :urgente (:situacao paciente :normal)))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (and (>= valor 50) (nao-eh-urgente? paciente))))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (and (not (some #(= % procedimento) plano)) (nao-eh-urgente? paciente)))))

(let [particular (->PacienteParticular 1 "Paciente Particular" "10/01/1993" :normal)
      plano (->PacientePlanoDeSaude 1 "Paciente Plano De Saude" "10/01/1993" :normal [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 5000))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta 5000)))

(let [particular (->PacienteParticular 1 "Paciente Particular" "10/01/1993" :urgente)
      plano (->PacientePlanoDeSaude 1 "Paciente Plano De Saude" "10/01/1993" :urgente [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 5000))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta 5000)))

;===================================================
;Funcao multipla
;Na definicao da funcao multipla informamos uma funcao que sera chamada para decidir qual a funcao defmethod sera chamada
(defmulti deve-assinar-pre-autorizacao-multi? class)

(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular
  [paciente]
  (println "Paciente Particular")
  true)

(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude
  [paciente]
  (println "Paciente Plano De Saude")
  false)

(let [particular (->PacienteParticular 1 "Paciente Particular" "10/01/1993" :urgente)
      plano (->PacientePlanoDeSaude 1 "Paciente Plano De Saude" "10/01/1993" :urgente [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-multi? particular))
  (pprint (deve-assinar-pre-autorizacao-multi? plano)))

(defn minha-funcao
  [p]
  (println p)
  (class p))

(defmulti multi-teste minha-funcao)
;(multi-teste "Teste")

;Funcao de definicao da estrategia customizada
(defn tipo-de-autorizador
  [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgente situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente))))

(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)
(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado
  [pedido]
  false)
(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteParticular
  [pedido]
  (>= (:valor pedido 0)))
(defmethod deve-assinar-pre-autorizacao-do-pedido? PacientePlanoDeSaude
  [pedido]
  (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))

(let [particular (->PacienteParticular 1 "Paciente Particular" "10/01/1993" :urgente)
      plano (->PacientePlanoDeSaude 1 "Paciente Plano De Saude" "10/01/1993" :urgente [:raio-x :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular :valor 1000 :procedimento :coleta}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano :valor 1000 :procedimento :coleta})))