(ns mutability-atoms.a1-vector-lists-sets-queues
  (:use [clojure pprint])
  (:require [mutability-atoms.modelo :as m.model]
            [mutability-atoms.logic :as m.logic]))

;===================================================
;Vector []
(defn testa-vetor
  []
  (let [espera [111 222]]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))

(testa-vetor)

;===================================================
;Linked List '()
(defn testa-lista
  []
  (let [espera '(111 222)]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))

(testa-lista)

;===================================================
;Set #{}
(defn testa-conjunto
  []
  (let [espera #{111 222}]
    (println espera)
    (println (conj espera 111))
    (println (conj espera 333))
    (println (conj espera 444))
    ;pop nao funciona em sets
    ;(println (pop espera))
    ))

(testa-conjunto)

;===================================================
;Queue
(defn testa-fila
  []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println (seq espera))
    (println (seq (conj espera "333")))
    (println (seq (pop espera)))
    (println (peek espera))
    ;Imprime a fila de forma legivel
    (pprint espera)))

(testa-fila)