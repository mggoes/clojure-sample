(ns introduction.a6-map-reduce-filter)

;===================================================
;Desestruracao de colecoes
(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})
(defn imprime-e-15
  ;Desestruturando o valor na entrada em 2
  [[chave valor]]
  (println chave "e" valor)
  valor)
(println (map imprime-e-15 pedido))

(defn preco-dos-produtos
  [[chave valor]]
  (* (:quantidade valor) (:preco valor)))
(println (map preco-dos-produtos pedido))
(println (reduce + (map preco-dos-produtos pedido)))

;Para ignorar um valor nao utilizado pode-se aplicar underline (_)
(defn preco-dos-produtos
  [[_ valor]]
  (* (:quantidade valor) (:preco valor)))
(defn total-pedido
  [pedido]
  (reduce + (map preco-dos-produtos pedido)))
(println (total-pedido pedido))

;===================================================
;Threading last
(defn total-pedido
  [pedido]
  (->> pedido
       (map preco-dos-produtos)
       (reduce +)))
(println (total-pedido pedido))

(defn preco-total-do-produto
  [produto]
  (* (:quantidade produto) (:preco produto)))
(defn total-pedido
  [pedido]
  (->> pedido
       vals
       (map preco-total-do-produto)
       (reduce +)))
(println (total-pedido pedido))

;===================================================
;Filter
(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}
             :chaveiro {:quantidade 1}})
(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))
(println (filter gratuito? (vals pedido)))

(defn gratuito?
  [[_ item]]
  (<= (get item :preco 0) 0))
(println (filter gratuito? pedido))

(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))
(println (filter (fn [[_ item]] (gratuito? item)) pedido))
;Recuperando o segundo parametro
(println (filter #(gratuito? (second %)) pedido))

(defn pago?
  [item]
  (not (gratuito? item)))
(println (pago? {:preco 50}))
(println ((comp not gratuito?) {:preco 50}))

;Compondo funcoes
(def pago? (comp not gratuito?))
(println (pago? {:preco 50}))