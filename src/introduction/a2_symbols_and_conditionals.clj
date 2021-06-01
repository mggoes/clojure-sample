(ns introduction.a2-symbols-and-conditionals)

;===================================================
;Valores locais
(defn valor-descontado
  [valor-bruto]
  (if (> valor-bruto 100)
    ;let define valores em um contexto local
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      ;println aceita varios parametros e os concatena com espaco em branco
      (println "Calculando desconto de" desconto)
      (- valor-bruto desconto))
    valor-bruto))

(defn -main []
  ;Recuperando o tipo do valor
  (println (class 90.0))
  ;clojure.lang.BigInt
  (println (class 90N))
  ;java.math.BigDecimal
  (println (class 90M))
  (println (valor-descontado 1000))
  (println (valor-descontado 100))

  ;===================================================
  ;Condicionais
  (println (> 500 100))
  (println (< 500 100))
  (println (if nil "true" "false"))

  (if (> 500 100)
    (println "maior")
    ;Else
    (println "menor ou igual"))

  (if (< 500 100)
    (println "maior")
    (println "menor ou igual")))