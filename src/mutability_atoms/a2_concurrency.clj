(ns mutability-atoms.a2-concurrency
  (:use [clojure pprint])
  (:require [mutability-atoms.modelo :as m.model]
            [mutability-atoms.logic :as m.logic]))

;===================================================
;Threads
(defn testando-threads
  []
  (def hospital (m.model/novo-hospital))
  ;Classe. - Cria uma instancia de uma classe Java invocando o construtor
  ;.funcao - Chama um metodo de uma instancia
  ;Passando parametros para o contrutor em Java
  (.start (Thread. (fn [] (println "oi1"))))
  (.start (Thread. (fn [] (println "oi2"))))
  (.start (Thread. (fn [] (println "oi3"))))
  (.start (Thread. (fn [] (println "oi4"))))
  (.start (Thread. (fn [] (println "oi5"))))
  (.start (Thread. (fn [] (println "oi6")))))

;(println (testando-threads))

(defn chega-em-malvado
  [pessoa]
  (def hospital (m.logic/chega-em-pausado hospital :espera pessoa))
  (println "Apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (def hospital (m.model/novo-hospital))
  (.start (Thread. (fn [] (chega-em-malvado "111"))))
  (.start (Thread. (fn [] (chega-em-malvado "222"))))
  (.start (Thread. (fn [] (chega-em-malvado "333"))))
  (.start (Thread. (fn [] (chega-em-malvado "444"))))
  (.start (Thread. (fn [] (chega-em-malvado "555"))))
  (.start (Thread. (fn [] (chega-em-malvado "666"))))
  ;Chamando um metodo estatico
  (.start (Thread. (fn [] (Thread/sleep 4000)
                     (pprint hospital)))))

(simula-um-dia-em-paralelo)
