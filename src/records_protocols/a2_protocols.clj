(ns records-protocols.a2-protocols
  (:use clojure.pprint))

(defrecord PacienteParticular [id nome nascimento])
(defrecord PacientePlanoDeSaude [id nome nascimento plano])

;(defn deve-assinar-pre-autorizacao?
;  [paciente procedimento valor]
;  (if (= PacienteParticular (type paciente))
;    (>= valor 50)
;    (if (= PacientePlanoDeSaude (type paciente))
;      (let [plano (get paciente :plano)]
;        (not (some #(= % procedimento) plano)))
;      true)))

;===================================================
;Protocolos - Equivalentes a interfaces em java
(defprotocol Cobravel
  ;Definindo a funcao desse protocolo
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

;Extendendo um protocolo e implementando a funcao
(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

;Definindo um record ja implementando um protocolo
(defrecord PacienteTeste
  [id nome nascimento plano]
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 1 "Paciente Particular" "10/01/1993")
      plano (->PacientePlanoDeSaude 1 "Paciente Plano De Saude" "10/01/1993" [:raio-x :ultrassom])]
  ;Chama a implementacao do PacienteParticular
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))

  ;Chama a implementacao do PacientePlanoDeSaude
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 5000))
  (pprint (deve-assinar-pre-autorizacao? plano :coleta 5000)))

;Adicionando comportamentos em tipos ja existentes
(defprotocol Datavel
  (to-ms [this]))

(extend-type java.lang.Number
  Datavel
  (to-ms [this]
    this))
(pprint (to-ms 56))

(extend-type java.util.Date
  Datavel
  (to-ms [this]
    (.getTime this)))
(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Datavel
  (to-ms [this]
    (to-ms (.getTime this))))
(pprint (to-ms (java.util.GregorianCalendar.)))