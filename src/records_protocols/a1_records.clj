(ns records-protocols.a1-records
  (:use clojure.pprint))

(defn adiciona-paciente
  [pacientes paciente]
  ;Executa o corpo do let caso o simbolo tenha um valor
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente nao possui id" {:paciente paciente}))))

(defn testa-uso-de-pacientes
  []
  (let [pacientes {}
        p1 {:id 1 :nome "Paciente 1" :nascimento "01/06/2021"}
        p2 {:id 2 :nome "Paciente 2" :nascimento "01/06/2021"}
        p3 {:nome "Paciente 3" :nascimento "01/06/2021"}]
    (pprint (adiciona-paciente pacientes p1))
    (pprint (adiciona-paciente pacientes p2))
    (pprint (adiciona-paciente pacientes p3))))

;(testa-uso-de-pacientes)

;===================================================
;Record - Gracas a interoperabilidade, cria uma classe java imutavel em tempo de compilacao
(defrecord Paciente [id nome nascimento])

;Forcando o tipo do simbolo
(defrecord Paciente2 [^Long id ^String nome nascimento])

;Criando um paciente com record
;Utiliza-se a forma ->Nome_do_record
(println (->Paciente 1 "Paciente 1" "01/06/2021"))
(pprint (->Paciente 1 "Paciente 1" "01/06/2021"))

;Chamando o construtor
(pprint (Paciente. 1 "Paciente 1" "01/06/2021"))

;Criando um simbolo atraves de um mapa
(map->Paciente {:id 1 :nome "Paciente 1" :nascimento "01/06/2021"})

(let [p1 (->Paciente 1 "Paciente 1" "01/06/2021")]
  (println (:id p1))
  (println (vals p1))
  (println (class p1))
  (println (record? p1))
  (println (.nome p1)))

;Adicionando itens no mapa
(pprint (map->Paciente {:id 1 :nome "Paciente 1" :nascimento "01/06/2021" :rg "111111111"}))

;Criando um paciente sem o id
(pprint (map->Paciente {:nome "Paciente 1" :nascimento "01/06/2021" :rg "111111111"}))

(pprint (assoc (Paciente. nil "Paciente 1" "01/06/2021") :id 2))
(pprint (class (assoc (Paciente. nil "Paciente 1" "01/06/2021") :id 2)))

;Checando igualdade
(println (= (->Paciente 1 "Paciente 1" "01/06/2021") (->Paciente 1 "Paciente 1" "01/06/2021")))