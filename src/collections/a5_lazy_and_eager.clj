(ns collections.a5-lazy-and-eager
  (:require [collections.db  :as c.db]
            [collections.logic :as c.logic]))

;===================================================
;Keep
(defn gastou-bastante?
  [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (c.db/todos-os-pedidos)
      resumo (c.logic/resumo-por-usuario pedidos)]
  ;Mantem os valores retornados pela funcao
  (println "Keep" (keep gastou-bastante? resumo))
  (println "Filter" (filter gastou-bastante? resumo)))

;===================================================
;Lazy / Eager
(println (range 10))
(println (take 2 (range 10000000000000)))

(defn filtro1
  [x]
  (println "filtro1" x)
  x)
(println (map filtro1 (range 10)))

(defn filtro2
  [x]
  (println "filtro2" x)
  x)
(println (map filtro2 (map filtro1 (range 10))))

(->> (range 10)
     (map filtro1)
     (map filtro2)
     println)

(->> (range 50)
     (map filtro1)
     (map filtro2)
     println)

;mapv aplica a funcao de mapeamento e devolve um vetor
(->> (range 50)
     (mapv filtro1)
     (mapv filtro2)
     println)

(->> [0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9]
     (map filtro1)
     (map filtro2)
     println)

;===================================================
;Linked List
(->> '(0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9)
     (map filtro1)
     (map filtro2)
     println)