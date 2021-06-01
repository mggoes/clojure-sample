(ns introduction.a5-maps-and-threading)

;===================================================
;Map
(def estoque {"Mochila" 10, "Camiseta" 5})
(println estoque)

(println "Temos" (count estoque) "elementos")
(println "Chaves são:" (keys estoque))
(println "Valores são:" (vals estoque))

;Keywords (podem ser utilizadas como funcoes para recuperar o valor)
(def estoque {:mochila  10
              :camiseta 5})

(println estoque)

;Associacao
(println (assoc estoque :cadeira 3))
(println (assoc estoque :mochila 1))

;Update
(println estoque)
(println (update estoque :mochila inc))
(println (update estoque :mochila #(- % 3)))

;Desassociacao
(println (dissoc estoque :mochila))

;Mapa aninhado
(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})

(println pedido)

(def pedido (assoc pedido :chaveiro {:quantidade 1, :preco 10}))
(println pedido)

;Acessar valores
(println (pedido :mochila))
(println (get pedido :mochila))
(println (get pedido :cadeira {}))

;Utilizando a chave como funcao para recuperar o valor
(println (:mochila pedido))
(println (:mesa pedido {}))

(println (:quantidade (:mochila pedido)))

;Update IN
(println pedido)
(println (update-in pedido [:mochila :quantidade] inc))

;===================================================
;Threading first (encadeamento de chamadas, forma mais utilizada)
(println (-> pedido
             :mochila
             :quantidade))

(-> pedido
    :mochila
    :quantidade
    println)