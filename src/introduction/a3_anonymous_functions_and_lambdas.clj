(ns introduction.a3-anonymous-functions-and-lambdas)

(defn valor-descontado
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

;(println (valor-descontado 1000))
;(println (valor-descontado 100))

;Eh uma boa pratica nomes de funcoes que retornam um valor booleano possuirem interrogacao no final
(defn aplica-desconto?
  [valor-bruto]
  (if (> valor-bruto 100)
    true))

;(println (aplica-desconto? 1000))
;(println (aplica-desconto? 100))

(defn valor-descontado
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

;(println (valor-descontado 1000))
;(println (valor-descontado 100))

(defn aplica-desconto?
  [valor-bruto]
  (when (> valor-bruto 100)
    true))

;(println (valor-descontado 1000))
;(println (valor-descontado 100))

(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

;(println (valor-descontado 1000))
;(println (valor-descontado 100))

;===================================================
;Higher Order Functions
;Passando funcao como parametro

(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

;(println (valor-descontado mais-caro-que-100? 1000))        ;Passando funcao como parametro
;(println (valor-descontado mais-caro-que-100? 100))

;===================================================
;Funcoes anonimas
;Utiliza-se apenas o fn
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 1000))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 100))

;Abreviando a declaracao da funcao anonima ou lambda
(println (valor-descontado #(> %1 100) 1000))
;$(operacao %[indice-do-parametro] 100)
(println (valor-descontado #(> %1 100) 100))
;Quando a funcao recebe apenas 1 parametro, pode-se omitir o indice
(println (valor-descontado #(> % 100) 100))