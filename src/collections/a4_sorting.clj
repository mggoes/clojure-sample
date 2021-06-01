(ns collections.a4-sorting
  (:require [collections.db :as c.db]
            [collections.logic :as c.logic]))

(println (c.db/todos-os-pedidos))

(println (c.logic/resumo-por-usuario (c.db/todos-os-pedidos)))

(let [pedidos (c.db/todos-os-pedidos)
      resumo (c.logic/resumo-por-usuario pedidos)]
  (println "Resumo" resumo)
  (println "Ordenando" (sort-by :preco-total resumo))
  (println "Reverte" (reverse (sort-by :preco-total resumo)))
  (println "Ordenando por id" (sort-by :usuario-id resumo))
  (println (get-in pedidos [0 :items :mochila :quantidade])))

(defn resumo-por-usuario-ordenado
  [pedidos]
  (->> pedidos
       c.logic/resumo-por-usuario
       (sort-by :preco-total)
       reverse))

(let [pedidos (c.db/todos-os-pedidos)
      resumo (resumo-por-usuario-ordenado pedidos)]
  (println "Resumo" resumo)
  (println "Primeiro" (first resumo))
  (println "Segundo" (second resumo))
  (println "Rest" (rest resumo))
  (println "Total" (count resumo))
  (println "Class" (class resumo))
  (println "Nth" (nth resumo 1))
  (println "Take" (take 2 resumo)))

(let [pedidos (c.db/todos-os-pedidos)
      resumo (resumo-por-usuario-ordenado pedidos)]
  (println "> 500 filter =>" (filter #(> (:preco-total %) 500) resumo))
  (println "> 500 filter empty not=>" (not (empty? (filter #(> (:preco-total %) 500) resumo))))
  (println "> 500 some=>" (some #(> (:preco-total %) 500) resumo)))