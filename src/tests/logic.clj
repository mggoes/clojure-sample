(ns tests.logic
  (:require [tests.model :as t.model]
            [schema.core :as s]))

;(defn cabe-na-fila?
;  [hospital departamento]
;  (-> hospital
;      departamento
;      count
;      (< 5)))

;(defn cabe-na-fila?
;  [hospital departamento]
;  ;when-let eh utilizado quando nao precisamos do else
;  (when-let [fila (get hospital departamento)]
;    (-> fila
;        count
;        (< 5))))

(defn cabe-na-fila?
  [hospital departamento]
  ;A macro some-> continua a execucao do threading enquanto o resultado nao for nil
  ;Caso alguma funcao retorne nil, a execucao eh interrompida e nil eh retornado
  (some-> hospital
          departamento
          count
          (< 5)))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (IllegalStateException. "Nao cabe mais ninguem neste departamento."))))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (ex-info "Nao cabe mais ninguem neste departamento." {:paciente pessoa}))))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (ex-info "Nao cabe mais ninguem neste departamento." {:paciente pessoa :tipo :impossivel-colocar-paciente-na-fila}))))

;(defn- tenta-colocar-na-fila
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
;    {:hospital novo-hospital :resultado :sucesso}
;    {:hospital hospital :resultado :erro}))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (IllegalStateException. "Nao cabe mais ninguem neste departamento."))))

(s/defn atende :- t.model/Hospital
  [hospital :- t.model/Hospital departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima :- t.model/PacienteID
  [hospital :- t.model/Hospital departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

;===================================================
(defn mesmo-tamanho?
  [hospital outro-hospital de para]
  (= (+ (count (get outro-hospital de)) (count (get outro-hospital para)))
     (+ (count (get hospital de)) (count (get hospital para)))))

;Pre-condicoes / Pos-condicoes
;Sao contratos de entrada e saida da funcao
(s/defn transfere :- t.model/Hospital
  [hospital :- t.model/Hospital de :- s/Keyword para :- s/Keyword]
  {
   ;Definindo pre-condicoes antes da execucao da funcao
   :pre  [(contains? hospital de) (contains? hospital para)]

   ;Definindo pos-condicoes depois da execucao da funcao
   ;As pos-condicoes possuem acesso ao retorno da funcao atraves do simbolo %
   :post [(mesmo-tamanho? hospital % de para)]
   }
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))