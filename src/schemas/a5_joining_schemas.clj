(ns schemas.a5_joining_schemas
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'positive-integer))
(def Plano [s/Keyword])
(def Paciente
  {:id                          PosInt
   :nome                        s/Str
   :plano                       Plano
   (s/optional-key :nascimento) s/Str})
(def Pacientes {PosInt Paciente})
(def Visitas
  {PosInt [s/Str]})

(s/defn adiciona-paciente :- Pacientes
  [pacientes :- Pacientes paciente :- Paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente nao possui id" {:paciente paciente}))))

(s/defn adiciona-visita :- Visitas
  [visitas :- Visitas paciente :- PosInt novas-visitas :- [s/Str]]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(s/defn imprime-relatorio-do-paciente
  [visitas :- Visitas paciente :- PosInt]
  (println "Visitas do paciente" paciente "sao" (get visitas paciente)))

(defn testa-uso-de-pacientes
  []
  (let [paciente1 {:id 1 :nome "Paciente 1" :plano []}
        paciente2 {:id 2 :nome "Paciente 2" :plano []}
        paciente3 {:id 3 :nome "Paciente 3" :plano []}
        pacientes (reduce adiciona-paciente {} [paciente1 paciente2 paciente3])
        visitas {}
        visitas (adiciona-visita visitas 1 ["01/01/2021"])
        visitas (adiciona-visita visitas 2 ["01/01/2021" "02/01/2021"])
        visitas (adiciona-visita visitas 1 ["03/01/2021"])]
    (pprint pacientes)
    (pprint visitas)
    (imprime-relatorio-do-paciente visitas 2)))

(testa-uso-de-pacientes)