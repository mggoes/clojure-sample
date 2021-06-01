(ns records-protocols.a3-component
  (:use clojure.pprint)
  (:require [records-protocols.logic :as r.logic]))

(defn carrega-paciente
  [id]
  (println "Carregando paciente" id)
  (Thread/sleep 1000)
  {:id id :carregado-em (r.logic/agora)})

;(pprint (carrega-paciente 15))
;(pprint (carrega-paciente 30))

;defn- indica que essa funcao estara acessivel apenas dentro desse namespace
(defn- carrega-se-nao-existe
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))

;(pprint (carrega-se-nao-existe {} 15 carrega-paciente))
;(pprint (carrega-se-nao-existe {15 {:id 15}} 15 carrega-paciente))

;===================================================
;Componente
(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache
  [cache carregadora]
  Carregavel
  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))

(def pacientes (->Cache (atom {}) carrega-paciente))
(pprint pacientes)

(carrega! pacientes 15)
(carrega! pacientes 30)
(carrega! pacientes 15)
(pprint pacientes)