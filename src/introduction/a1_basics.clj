;Namespace eh como se fosse o pacote do arquivo
(ns introduction.a1-basics)
;Em clojure a funcao -main e o ponto de entrada da aplicacao
;Para executar a aplicacao basta executar o comando java clojure.main -m[--main] [namespace]. Ex.: java clojure.main -m user
(defn -main []
  ;O parentese significa a invocacao de uma funcao
  ;A primeira instrucao depois do parentese eh a operacao a ser executada
  (println "Bem vindo ao sistema de estoque")

  ;Root binding - definindo um valor global
  (def total-de-produtos 15)

  (println total-de-produtos)
  (def total-de-produtos 13)
  (println total-de-produtos)
  (println (+ 13 3))

  (def total-de-produtos (+ total-de-produtos 3))
  (println total-de-produtos)

  ;===================================================
  ;Vetor
  ;A virgula e opcional
  (def estoque ["Mochila", "Camiseta"])
  (println estoque)
  ;Recuperando um elemento do vetor
  (println (estoque 0))
  (println (estoque 1))
  ;Contando os elementos do vetor
  (println (count estoque))
  ;Adicionando um elemento no vetor, a funcao conj nao altera o vetor original, ela retorna um novo vetor
  (conj estoque "Cadeira")
  (println estoque)
  (def estoque (conj estoque "Cadeira"))
  (println estoque)

  ;===================================================
  ;Funcao
  (defn imprime-mensagem []
    (println "------------------------")
    (println "Bem vindo(a) ao estoque!"))
  (imprime-mensagem)

  ;Para definir uma funcao eh utilizado o operador defn nome_da_funcao [argumentos]
  (defn valor-descontado                                    ;Nome
    "Retorna o valor descontado: Valor-bruto * 0.9"         ;Doc
    [valor-bruto]                                           ;Argumentos
    (* valor-bruto 0.9))                                    ;Corpo
  (println (valor-descontado 100))

  (defn valor-descontado
    [valor-bruto]
    (* valor-bruto (- 1 0.10)))
  (println (valor-descontado 100)))