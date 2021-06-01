(ns schemas.a3_schema_composition
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;===================================================
;Schemas compostos
;s/pred permite especificar uma funcao de validacao
(def PosInt (s/pred pos-int? 'positive-integer))

(def Paciente
  {:id PosInt :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- PosInt nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 1 "Paciente 1"))
;(pprint (novo-paciente -1 "Paceinte 1"))

(defn maior-ou-igual-a-zero?
  [x]
  (>= x 0))
;s/constrained permite especificar um tipo e uma pos-condicao (funcao) de validacao
(def ValorFinanceiro (s/constrained s/Num maior-ou-igual-a-zero?))

(def Pedido {:paciente     Paciente
             :valor        ValorFinanceiro
             :procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente valor :- ValorFinanceiro procedimento :- s/Keyword]
  {:paciente paciente :valor valor :procedimento procedimento})

(pprint (novo-pedido (novo-paciente 1 "Paciente 1") 50.50 :raio-x))
;(pprint (novo-pedido (novo-paciente 1 "Paciente 1") -50.50 :raio-x))

;===================================================
;Schemas com sequencias
(def Numeros [s/Num])
(pprint (s/validate Numeros [15]))
(pprint (s/validate Numeros [15 13 14]))
(pprint (s/validate Numeros [1 2 3 4 5 6 7 8 9]))
(pprint (s/validate Numeros []))
(pprint (s/validate Numeros nil))

(def Plano [s/Keyword])
(pprint (s/validate Plano [:raio-x]))

(def Paciente
  {:id PosInt :nome s/Str :plano Plano})

(pprint (s/validate Paciente {:id 1 :nome "Paciente 1" :plano [:raio-x :ultrassom]}))