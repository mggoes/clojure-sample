(ns reference.c5-macros
  (:use [clojure.pprint]))

;Macros
;Allows us to extend compiler with user code and define syntactic constructs
;https://www.braveclojure.com/writing-macros/

;MACROEXPAND
;Expands until the raw form and return the resulting construct
(pprint (macroexpand '(when (= 1 1) (println "True"))))
(pprint (macroexpand '(and true true)))

;MACROS
;Macros receive arguments as unevaluated data
(defmacro infix
  "Infix macro"
  ;"infixed" argument is not evaluated before macro call
  [infixed]
  (println "Before" infixed)
  ;Rearranging code to produce a list that will be evaluated by clojure
  ;Ex.: (1 + 1) -> (+ 1 1)
  (let [result (list (second infixed) (first infixed) (last infixed))]
    (println "After" result)
    ;The result of the macro will be evaluated by clojure
    result))

;Parameters passed to macros are not evaluated
(println (infix (1 + 1)))
(pprint (macroexpand '(infix (1 + 1))))

;DESTRUCTURING
;Destructuring arguments just like functions
(defmacro infix-2
  [[operand1 op operand2]]
  ;Macro must return a list to be evaluated
  (list op operand1 operand2))

(println (infix-2 (1 + 1)))

;SIMPLE QUOTING (')
;Returns the unevaluated symbol
(defmacro my-print-whoopsie
  [expression]
  (list 'let ['result expression]
    (list 'println 'result)
    'result))

(println (my-print-whoopsie "whoopsie"))

(defmacro unless
  "Inverted 'if'"
  [test & branches]
  ;(if (test) (inverted branches))
  (conj (reverse branches) test 'if))

(println (macroexpand '(unless (true)
                         (println "First")
                         (println "Last"))))

;SYNTAX QUOTING (`)
;Scapes all the form
;Always include namespace
;clojure.core/+
(println `+)
(println `(+ 1 2))
(println '(+ 1 2))

;In syntax quoting, tilde (~) is a escape character and makes the form to be evaluated
;(clojure.core/+ 1 2)
(println `(+ 1 ~(inc 1)))

(defmacro code-critic
  [bad good]
  `(do (println "This is a bad code:" '~bad)
     (println "This is a good code:" (quote ~good))))

(code-critic (1 + 1) (+ 1 1))
(println (macroexpand '(code-critic (1 + 1) (+ 1 1))))

;UNQUOTE SPLICING (~@)
;Unwraps a list and put the result directly into the code. Ex: (1 2 3) -> 1 2 3
;(clojure.core/+ (1 2 3)) -> (clojure.core/+ 1 2 3)
(println `(+ ~(list 1 2 3)))
(println `(+ ~@(list 1 2 3)))

(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic2
  [bad good]
  `(do ~@(map #(apply criticize-code %)
           [["Great squid of Madrid, this is bad code:" bad]
            ["Sweet gorilla of Manila, this is good code:" good]])))

(println (macroexpand '(code-critic2 (1 + 1) (+ 1 1))))

;GENSYM
;Produces a symbol
(defmacro test-gensym
  [& code]
  (let [symbol (gensym 'test-gensym_)]
    `(let [~symbol "some symbol!"]
       ~@code
       (println "End" ~symbol))))

(test-gensym (println "First message"))
(println (macroexpand '(test-gensym (println "First message"))))

;Auto gensym
;Makes Clojure generates an auto gensym to each symbol containing a hash (#) at the end
;Clojure ensures that each instance of the same symbol with hash (#) will resolve to the same auto generated symbol inside the list
(println `(test# test# test2#))
