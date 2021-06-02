(ns generators-property-tests.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [clojure.string :refer (join)]
            [generators-property-tests.logic :refer :all]
            [generators-property-tests.model :as g.model]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [schema.core :as s]
            [schema-generators.generators :as g]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test
  (testing "Que cabe numa fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "Que cabe pessoas em filas de tamanho ate 4 inclusive"
    ;Gerando vetores de inteiros automaticamente
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4))]
      (is (cabe-na-fila? {:espera fila} :espera))))

  (testing "Que nao cabe quando a fila esta cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Que nao cabe quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "Que cabe quando a fila ainda nao esta cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1 2]} :espera)))

  (testing "Que nao cabe quando o departamento nao existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]} :raio-x)))))

;Teste generativo
;defspec define a quantidade de vezes que um teste sera executado
(defspec coloca-uma-pessoa-em-filas-menores-que-5 10
         ;prop/for-all eh semelhante ao let
         ;Define simbolos que podem utilizados
         ;Nao eh necessario chamar a funcao gen/sample pois o defspec ja faz essa tarefa
         (prop/for-all [fila (gen/vector gen/string-alphanumeric 0 4)
                        pessoa gen/string-alphanumeric]
                       (is (= {:espera (conj fila pessoa)}
                              (chega-em {:espera fila} :espera pessoa)))))

;fmap aplica uma funcao em um gerador
(def nome-aleatorio (gen/fmap join (gen/vector gen/char-alphanumeric 5 10)))

(defn transforma-vetor-em-fila
  [vetor]
  (reduce conj g.model/fila-vazia vetor))

(def fila-nao-cheia-gen (gen/fmap transforma-vetor-em-fila (gen/vector nome-aleatorio 0 4)))

;(defn transfere-ignorando-erro
;  [hospital para]
;  (try
;    (transfere hospital :espera para)
;    (catch Exception e
;      (cond
;        (= :fila-cheia (:type (ex-data e))) hospital
;        :else (throw e))
;      hospital)))

(defn transfere-ignorando-erro
  [hospital para]
  (try
    (transfere hospital :espera para)
    (catch IllegalStateException _
      hospital)))

(defspec transfere-tem-que-manter-a-quantidade-de-pessoas 5
         (prop/for-all [espera (gen/fmap transforma-vetor-em-fila (gen/vector nome-aleatorio 0 50))
                        raio-x fila-nao-cheia-gen
                        ultrassom fila-nao-cheia-gen
                        vai-para (gen/vector (gen/elements [:raio-x :ultrassom]) 0 50)]
                       (let [hospital-inicial {:espera espera :raio-x raio-x :ultrassom ultrassom}
                             hospital-final (reduce transfere-ignorando-erro hospital-inicial vai-para)]
                         (= (total-pacientes hospital-inicial)
                            (total-pacientes hospital-final)))))

(defn adiciona-fila-de-espera
  [[hospital fila]]
  (assoc hospital :espera fila))

(def hospital-gen (gen/fmap adiciona-fila-de-espera (gen/tuple (gen/not-empty (g/generator g.model/Hospital)) fila-nao-cheia-gen)))

(def chega-em-gen (gen/tuple (gen/return chega-em)
                             (gen/return :espera)
                             nome-aleatorio
                             (gen/return 1)))

(defn adiciona-inexistente-ao-departamento
  [departamento]
  (keyword (str departamento "-inexistente")))

(defn transfere-gen
  [hospital]
  (let [departamentos (keys hospital)
        departamentos-inexistentes (map adiciona-inexistente-ao-departamento departamentos)
        todos-os-departamentos (concat departamentos departamentos-inexistentes)]
    (gen/tuple (gen/return transfere)
               (gen/elements todos-os-departamentos)
               (gen/elements todos-os-departamentos)
               (gen/return 0))))

(defn acao-gen
  [hospital]
  (gen/one-of [chega-em-gen (transfere-gen hospital)]))

(defn acoes-gen
  [hospital]
  (gen/not-empty (gen/vector (acao-gen hospital) 1 100)))

(defn executa-uma-acao
  [situacao [funcao param1 param2 diferenca-se-sucesso]]
  (let [hospital (:hospital situacao)
        diferenca-atual (:diferenca situacao)]
    (try
      (let [hospital-novo (funcao hospital param1 param2)]
        {:hospital  hospital-novo
         :diferenca (+ diferenca-se-sucesso diferenca-atual)})
      (catch IllegalStateException _
        situacao)
      (catch AssertionError _
        situacao))))

(defspec simula-um-dia-do-hospital-nao-perde-pessoas 10
         (prop/for-all [hospital-inicial hospital-gen]
                       (let [acoes (gen/generate (acoes-gen hospital-inicial))
                             situacao-inicial {:hospital hospital-inicial :diferenca 0}
                             total-de-pacientes-inicial (total-pacientes hospital-inicial)
                             situacao-final (reduce executa-uma-acao situacao-inicial acoes)
                             total-de-pacientes-final (total-pacientes (:hospital situacao-final))]
                         (println total-de-pacientes-inicial total-de-pacientes-final)
                         (is (= (- total-de-pacientes-final (:diferenca situacao-final)) total-de-pacientes-inicial)))))
