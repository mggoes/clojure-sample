(ns schemas.a2-custom-schemas
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;===================================================
;Definindo um schema customizado
(def Paciente
  "Schema de um paciente"
  {:id s/Num :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 1 :nome "Paciente 1"}))

;Erro de validacao do schema
;(pprint (s/validate Paciente {:id 1 :name "Paciente 1"}))
;(pprint (s/validate Paciente {:id 1}))
;(pprint (s/validate Paciente {:id 1 :nome "Paciente 1" :plano []}))

;Definindo o schema de retorno da funcao
;Ou seja, o retorno da funcao deve ser compativel com o esquema especificado
(s/defn novo-paciente :- Paciente
  [id :- s/Num nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 1 "Paciente 1"))

;===================================================
;Predicados
(defn estritamente-positivo?
  [x]
  (> x 0))

;(def EstritamentoPositivo (s/pred estritamente-positivo?))
;Definindo uma nome para o predicado para ser exibido em casos de erros de validacao
(def EstritamentoPositivo (s/pred estritamente-positivo? 'estritamente-positivo))

(pprint (s/validate EstritamentoPositivo 15))
;Erros de validacao do schema
;(pprint (s/validate EstritamentoPositivo 0))
;(pprint (s/validate EstritamentoPositivo -15))

;===================================================
;Composicao de schemas
(def Paciente
  "Schema de um paciente"
  {:id (s/constrained s/Int pos?) :nome s/Str})

(pprint (s/validate Paciente {:id 1 :nome "Paciente 1"}))
;(pprint (s/validate Paciente {:id -1 :nome "Paciente 1"}))
