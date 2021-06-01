(ns mutability-atoms.logic)

(defn cabe-na-fila?
  [hospital departamento]
  (-> hospital
      (get departamento)
      count
      (< 5)))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    ;Lancando excecao
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (do (Thread/sleep 1000)
        (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "Tentando adicionar a pessoa" pessoa)
  (if (cabe-na-fila? hospital departamento)
    (do (Thread/sleep (* (rand) 2000))
        (println "Dando o update na" pessoa)
        (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))

(defn atende-completo
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)})

;===================================================
;juxt - Cria uma funcao que executa outras funcoes e retorna o resultado da aplicacao de cada uma dentro de um vetor
(defn atende-completo-chamando-ambos
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc departamento fila-atualizada)]
    {:paciente pessoa
     :hospital hospital-atualizado}))