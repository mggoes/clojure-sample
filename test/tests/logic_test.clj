(ns tests.logic-test
  ;Importa tudo do clojure.test sem precisar referenciar o namespace
  (:require [clojure.test :refer :all]
            [tests.logic :refer :all]
            [tests.model :as t.model]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/set-fn-validation! true)

;deftest vem da lib padrao de teste do clojure e define uma funcao de teste sem parametros
(deftest cabe-na-fila?-test
  (testing "Que cabe numa fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "Que nao cabe quando a fila esta cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Que nao cabe quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "Que cabe quando a fila ainda nao esta cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1 2]} :espera)))

  (testing "Que nao cabe quando o departamento nao existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]} :raio-x)))))

(deftest chega-em-test
  (let [hospital-cheio {:espera [1 34 54 22 78]}]
    (testing "Aceita pessoas enquanto cabe na fila"
      (is (= {:espera [1 2 3 4 5]}
             (chega-em {:espera [1 2 3 4]} :espera 5)))
      (is (= {:espera [1 2 5]}
             (chega-em {:espera [1 2]} :espera 5))))

    ;(is (= {:hospital {:espera [1 2 3 4 5]} :resultado :sucesso}
    ;       (chega-em {:espera [1 2 3 4]} :espera 5)))
    ;(is (= {:hospital {:espera [1 2 5]} :resultado :sucesso}
    ;       (chega-em {:espera [1 2]} :espera 5))))

    (testing "Nao aceita pessoas quando nao cabe na fila"
      ;Verificando excecoes lancadas
      ;(is (thrown? ExceptionInfo (chega-em hospital-cheio} :espera 90)))
      (is (thrown? IllegalStateException (chega-em hospital-cheio :espera 90)))

      ;# antes da String cria um Pattern
      ;(is (thrown-with-msg? ExceptionInfo #"Nao cabe mais ninguem neste departamento." (chega-em hospital-cheio :espera 90)))
      ;(is (nil? (chega-em hospital-cheio :espera 90)))

      ;Capturando excecoes
      ;ex-data retorna o mapa de informacoes da excecao
      ;(is (try
      ;      (chega-em hospital-cheio :espera 90)
      ;      false
      ;      (catch clojure.lang.ExceptionInfo e
      ;        (= :impossivel-colocar-paciente-na-fila (:tipo (ex-data e))))))

      ;(is (= {:hospital hospital-cheio :resultado :erro}
      ;       (chega-em hospital-cheio :espera 90)
      ;       )))
      )))

(deftest transfere-test
  (testing "Aceita pessoas se cabe"
    (let [hospital-original {:espera (conj t.model/fila-vazia "5") :raio-x t.model/fila-vazia}]
      (is (= {:espera []
              :raio-x ["5"]}
             (transfere hospital-original :espera :raio-x))))

    (let [hospital-original {:espera (conj t.model/fila-vazia "51" "5") :raio-x (conj t.model/fila-vazia "13")}]
      (is (= {:espera ["5"]
              :raio-x ["13" "51"]}
             (transfere hospital-original :espera :raio-x)))))

  (testing "Aceita pessoas se nao cabe"
    (let [hospital-cheio {:espera (conj t.model/fila-vazia "5") :raio-x (conj t.model/fila-vazia "1" "2" "53" "42" "13")}]
      (is (thrown? IllegalStateException (transfere hospital-cheio :espera :raio-x)))))

  (testing "Nao pode invocar transferencia sem hospital"
    (is (thrown? clojure.lang.ExceptionInfo (transfere nil :espera :raio-x))))

  (testing "Condicoes obrigatorias"
    (let [hospital {:espera (conj t.model/fila-vazia "5") :raio-x (conj t.model/fila-vazia "1" "2" "53" "42")}]
      (is (thrown? AssertionError (transfere hospital :nao-existe :raio-x)))
      (is (thrown? AssertionError (transfere hospital :raio-x :nao-existe))))))
