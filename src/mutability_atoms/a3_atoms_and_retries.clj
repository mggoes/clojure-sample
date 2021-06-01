(ns mutability-atoms.a3-atoms-and-retries
  (:use [clojure pprint])
  (:require [mutability-atoms.logic :as m.logic]
            [mutability-atoms.modelo :as m.model]))

;Root Binding
;Qualquer thread tera acesso ao valor desse simbolo
(def nome "Matheus")

;===================================================
;Atomo
(defn testa-atomao
  []
  (let [hospital-teste (atom {:espera m.model/fila-vazia})]
    (println hospital-teste)
    (pprint hospital-teste)
    ;Recuperando o valor de dentro do atomo
    (pprint (deref hospital-teste))
    ;@ e um atalho para o deref
    (pprint @hospital-teste)

    (pprint (assoc @hospital-teste :laboratorio1 m.model/fila-vazia))
    (pprint @hospital-teste)

    ;Swap altera o valor do atomo
    ;Por padrao funcoes com ! possuem um efeito colateral
    (swap! hospital-teste assoc :laboratorio1 m.model/fila-vazia)
    (pprint @hospital-teste)

    (swap! hospital-teste assoc :laboratorio2 m.model/fila-vazia)
    (pprint @hospital-teste)

    (swap! hospital-teste update :laboratorio1 conj "111")
    (pprint @hospital-teste)))

;(testa-atomao)

;===================================================
;Thread com atomos
(defn chega-em-malvado!
  [hospital pessoa]
  (swap! hospital m.logic/chega-em-pausado-logando :espera pessoa)
  (println "Apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (m.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo)