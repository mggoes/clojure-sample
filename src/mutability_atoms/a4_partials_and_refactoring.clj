(ns mutability-atoms.a4-partials-and-refactoring
  (:use [clojure pprint])
  (:require [mutability-atoms.logic :as m.logic]
            [mutability-atoms.modelo :as m.model]))

(defn chega-sem-malvado!
  [hospital pessoa]
  (swap! hospital m.logic/chega-em :espera pessoa)
  (println "Apos inserir" pessoa))

;===================================================
(defn simula-um-dia-em-paralelo-com-mapv
  "Simulacao utilizando um mapv para forcar a execucao do map"
  []
  (let [hospital (atom (m.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]
    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv)

;===================================================
(defn simula-um-dia-em-paralelo-com-mapv-refatorada
  "Simulacao utilizando um mapv para forcar a execucao do map"
  []
  (let [hospital (atom (m.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        inicia-thread-de-chegada #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]
    (mapv inicia-thread-de-chegada pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-refatorada)

;===================================================
(defn inicia-thread-de-chegada
  ([hospital]
   (fn [pessoa] (inicia-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-preparando
  "Simulacao utilizando um mapv para forcar a execucao do map"
  []
  (let [hospital (atom (m.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        inicia (inicia-thread-de-chegada hospital)]
    (mapv inicia pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-preparando)

;===================================================
;Partials
(defn inicia-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-mapv-com-partial
  "Simulacao utilizando um mapv para forcar a execucao do map"
  []
  (let [hospital (atom (m.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        ;Prepara a funcao de forma parcial
        inicia (partial inicia-thread-de-chegada hospital)]
    ;Gracas ao partial, a funcao "inicia" sera chamada com os parametros iniciais + os parametros adicionais
    (mapv inicia pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-com-partial)

;===================================================
;doseq
(defn inicia-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-doseq
  []
  (let [hospital (atom (m.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]
    (doseq [pessoa pessoas]
      (inicia-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-doseq)

;===================================================
;dotimes
(defn simula-um-dia-em-paralelo-com-dotimes
  []
  (let [hospital (atom (m.model/novo-hospital))]
    (dotimes [pessoa 6]
      (inicia-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo-com-dotimes)