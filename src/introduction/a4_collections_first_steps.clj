(ns introduction.a4-collections-first-steps)

(def precos [30 700 1000])

;===================================================
;Recuperando um valor pelo indice
(println (precos 0))
(println (get precos 0))
(println (get precos 2))
;Retorna nil
(println (get precos 5))
;IndexOutOfBoundsException
;(println (precos 5))
;Valor padrao
(println (get precos 17 999))

(println (conj precos 5))
(println precos)

;===================================================
;Incrementando um valor pelo indice
(println (inc 5))
(println (update precos 0 inc))

;===================================================
;Decrementando um valor pelo indice
(println (dec 5))
(println (update precos 1 dec))

;===================================================
(defn soma-1
  [valor]
  (println "Estou somando 1 em" valor)
  (+ valor 1))

(println (update precos 0 soma-1))

(defn soma-3
  [valor]
  (println "Estou somando 3 em" valor)
  (+ valor 3))

(println (update precos 0 soma-3))

;===================================================
;Map
(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

;Mapeia todos os elemento de precos com a funcao valor-descontado
(println (map valor-descontado precos))

;===================================================
;Filter
;Cria um range de 0 a 9
(println (range 10))
;Filtra os numeros pares
(println (filter even? (range 10)))

(println precos)
(println (filter aplica-desconto? precos))

(println (map valor-descontado (filter aplica-desconto? precos)))

;===================================================
;Reduce
(println precos)
(println (reduce + precos))

(defn minha-soma
  [valor-1 valor-2]
  (println "Somando" valor-1 "com" valor-2)
  (+ valor-1 valor-2))

(println (reduce minha-soma precos))
(println (reduce minha-soma (range 10)))
(println (reduce minha-soma [15]))

;Valor inicial
(println (reduce minha-soma 0 precos))
(println (reduce minha-soma 0 [15]))

(println (reduce minha-soma 0 []))
;ArityException
;(println (reduce minha-soma []))
