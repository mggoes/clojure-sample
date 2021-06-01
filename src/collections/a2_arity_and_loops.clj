(ns collections.a2-arity-and-loops)

;===================================================
;Aridade
(def items ["item1" "item2" "item3"])

(defn conta
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 items))
(println (conta 0 []))

;A execucao sera de acordo com a quantidade de parametros
(defn minha-funcao
  ([p1] (println p1))
  ([p1 p2] (println p1 p2)))

(minha-funcao 1)
(minha-funcao 1 2)

(defn conta
  ([elementos]
   (conta 0 elementos))
  ([total-ate-agora elementos]
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))

(println (conta items))
(println (conta []))

;===================================================
;Loop
(defn conta
  [elementos]
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      ;A recorrencia sera feita no loop
      ;Como o loop declara 2 simbolos passamos 2 parametros para a funcao recur
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

(println (conta items))
(println (conta []))

;===================================================
;For
(for [x [1, 2, 3, 4, 5]
      :let [y (* x 2)]
      :while (<= y 10)
      :when (> y 5)]
  (println y))
