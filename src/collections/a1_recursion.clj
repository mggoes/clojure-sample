(ns collections.a1-recursion)

(def items ["item1" "item2" "item3"])
(map println items)

(println (first items))
(println (rest items))
(println (next items))
(println (rest []))
(println (next []))
(println (seq []))
(println (seq [1 2 3]))

;===================================================
;Recursividade
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      ;Executa as funcoes informadas
      (do
        (funcao primeiro)
        (meu-mapa funcao (rest sequencia))))))
(meu-mapa println items)
(meu-mapa println ["item1" false "item2" "item3"])
(meu-mapa println [])
(meu-mapa println nil)

;===================================================
;Tail Recursion - deve ser o retorno da funcao
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      ;Executa as funcoes informadas
      (do
        (funcao primeiro)
        ;Otimiza a execucao da funcao recursiva transformando em um loop
        (recur funcao (rest sequencia))))))
(meu-mapa println (range 10000))
