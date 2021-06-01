(ns schemas.a4_dynamic_maps
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'positive-integer))
(def Plano [s/Keyword])

(pprint (s/validate Plano [:raio-x]))

;===================================================
;Schema com chave opcional
;Keywords no schema sao obrigatorias
(def Paciente
  {:id                          PosInt
   :nome                        s/Str
   :plano                       Plano
   ;s/optional-key torna a chave opcional
   (s/optional-key :nascimento) s/Str})

(pprint (s/validate Paciente {:id 1 :nome "Paciente 1" :plano [:raio-x :ultrassom]}))
(pprint (s/validate Paciente {:id 1 :nome "Paciente 1" :plano []}))
(pprint (s/validate Paciente {:id 1 :nome "Paciente 1" :plano nil}))
(pprint (s/validate Paciente {:id 1 :nome "Paciente 1" :plano [] :nascimento "01/01/2021"}))

;Nesse caso, como o schema nao possui uma keyword, a chave nao eh obrigatoria
(def Pacientes {PosInt Paciente})
(pprint (s/validate Pacientes {}))

(let [paciente1 {:id 1 :nome "Paciente 1" :plano [:raio-x :ultrassom]}
      paciente2 {:id 2 :nome "Paciente 2" :plano []}]
  (pprint (s/validate Pacientes {1 paciente1}))
  (pprint (s/validate Pacientes {1 paciente1 2 paciente2}))
  ;Erro de validacao
  ;(pprint (s/validate Pacientes {-1 paciente1}))
  ;(pprint (s/validate Pacientes {1 15}))
  )