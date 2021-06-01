(ns mutability-atoms.a6-refs-and-dosyncs
  (:use [clojure pprint])
  (:require [mutability-atoms.modelo :as m.model]))

(defn chega-em
  [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

;===================================================
;Ref - Semelhante a um atomo, porem para realizar operacoes eh necessario estar dentro de uma transacao
(defn cabe-na-fila?
  [fila]
  (-> fila
      count
      (< 5)))

(defn chega-em!
  "Troca de referencia via ref-set"
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    ;Alterando o valor do ref com ref-set
    (ref-set fila (chega-em @fila pessoa))))

(defn chega-em!
  "Troca de referencia via alter"
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    ;Alterando o valor do ref com alter
    (alter fila chega-em pessoa)))

(defn simula-um-dia
  []
  ;Criando um ref
  (let [hospital {:espera       (ref m.model/fila-vazia)
                  :laboratorio1 (ref m.model/fila-vazia)
                  :laboratorio2 (ref m.model/fila-vazia)
                  :laboratorio3 (ref m.model/fila-vazia)}]
    ;Iniciando uma transacao com dosync
    (dosync
      (chega-em! hospital "Pessoa 1")
      (chega-em! hospital "Pessoa 2")
      (chega-em! hospital "Pessoa 3")
      (chega-em! hospital "Pessoa 4")
      (chega-em! hospital "Pessoa 5")
      (chega-em! hospital "Pessoa 6")
      )
    (pprint hospital)))

;(simula-um-dia)

;===================================================
;future - Recebe expressoes e executa em outra thread guardando o valor retornado
(defn async-chega-em!
  [hospital pessoa]
  (future (Thread/sleep (rand 5000))
          (dosync
            (println "Tentando" pessoa)
            (chega-em! hospital pessoa))))

(defn simula-um-dia-async
  []
  ;Criando um ref
  (let [hospital {:espera       (ref m.model/fila-vazia)
                  :laboratorio1 (ref m.model/fila-vazia)
                  :laboratorio2 (ref m.model/fila-vazia)
                  :laboratorio3 (ref m.model/fila-vazia)}
        futures (mapv #(async-chega-em! hospital %) (range 10))]
    (future
      (dotimes [_ 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))))

(simula-um-dia-async)



