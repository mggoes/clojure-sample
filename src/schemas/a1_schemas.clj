(ns schemas.a1-schemas
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente nao possui id" {:paciente paciente}))))

(defn adiciona-visita
  [visitas paciente novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(defn imprime-relatorio-do-paciente
  [visitas paciente]
  (println "Visitas do paciente" paciente "sao" (get visitas paciente)))

(defn testa-uso-de-pacientes
  []
  (let [paciente1 {:id 1 :nome "Paciente 1"}
        paciente2 {:id 2 :nome "Paciente 2"}
        paciente3 {:id 3 :nome "Paciente 3"}
        pacientes (reduce adiciona-paciente {} [paciente1 paciente2 paciente3])
        visitas {}
        visitas (adiciona-visita visitas 1 ["01/01/2021"])
        visitas (adiciona-visita visitas 2 ["01/01/2021" "02/01/2021"])
        visitas (adiciona-visita visitas 1 ["03/01/2021"])]
    (pprint pacientes)
    (pprint visitas)
    (imprime-relatorio-do-paciente visitas paciente1)))

;(testa-uso-de-pacientes)

;===================================================
;Validacao de Schemas
(pprint (s/validate Long 15))
;Erro de validacao de schema
;(pprint (s/validate Long "Teste"))

;Ligando a validacao global de schema
(s/set-fn-validation! true)

;Definindo uma funcao com a macro da lib prismatic schema
(s/defn teste-simples
  ;(:-) Define um schema para utilizar na validacao
  [x :- Long]
  (println x))

(teste-simples 15)
;Erro de validacao de schema
;(teste-simples "Teste")

;Validando o schema em tempo de execucao
(s/defn imprime-relatorio-do-paciente
  [visitas paciente :- Long]
  (println "Visitas do paciente" paciente "sao" (get visitas paciente)))
;Erro de validacao de schema
;(testa-uso-de-pacientes)

(s/defn novo-paciente
  [id :- Long nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 1 "Paciente 1"))
;(pprint (novo-paciente "Paciente 1" 1))