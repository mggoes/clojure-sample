(ns reference.c4-special-forms)

;==============================================================
;Special forms - ? (optional) / * (0 or more) / + (1 or more)

;DEF
;(def symbol doc-string? init?)
(def ^{:private true
       :doc     "short doc"
       :tag     java.lang.Integer} s "Some documentation" 10)
(println s)
(println (meta #'s))

;IF
;(if test then else?)
(if s
  (println "Symbol has value!")
  (println "Symbol has no value!"))

(def s2 false)
(if s2
  (println "Symbol has value!")
  (println "Symbol is false!"))

(println (if s2
           (println "Symbol has value!")))

;DO
;(do expr*)
(println (do
           (println "Expr 1")
           (println "Expr 2")
           (println "Expr 3")
           10))

;LET
;(let [binding*] expr*)
(let [x 1
      y s]
  (println (class x))
  (println (class y)))

;QUOTE
;(quote form)
(println '(a b c))

;VAR
;(var symbol)
;Returns the symbol, not the value!
(def v "var")
(println #'v)
(println (var v))

;FN
;(fn name? [params*] condition-map? expr*)
;(fn name? ([params*] condition-map? expr*)+)
(def f2 (fn f2 [] 2))
(def f1 (fn [] 1))
(def f3 (fn f3 [p1] p1))
;& more is like a vararg
(def f4 (fn f4 [p1 & more] [p1 more]))
(def f5 (fn f5 [p1] {:pre  [(pos? p1)]
                     :post [(> (* p1 2) 10)]} p1))

(println (f1))
(println (f2))
(println (f3 3))
(println (f4 4))
(println (f4 4 1))
(println (f4 4 1 2))
(println (f5 6))

;LOOP
;(loop [binding*] expr*)
(loop [a 1
       b 2]
  (println (* a b)))

;RECUR
;(recur expr*)
(defn pretty-print
  [n]
  (println "==========>" n)
  (if (< n 5)
    (recur (inc n))))

(pretty-print 0)

;THROW
;(throw expr)
(defn do-throw
  []
  (throw (ex-info "Throwing some exception" {:error/key :ex-info})))

;(do-throw)

;TRY - CATCH - FINALLY
;(try expr* catch* finally?)
(try
  (println "Calling function...")
  (do-throw)
  (catch java.lang.IllegalStateException e
    (println (ex-data e)))
  (catch clojure.lang.ExceptionInfo e
    (println (ex-data e)))
  (finally
    (println "Finally")))

;==============================================================
;Binding forms

;SEQUENTIAL DESTRUCTURING
;(& d) is like a vararg
;(:as) binds to the entire sequence
;In sequential cases destructuring will match the target positions
(let [[a b c & d :as e] [1 2 3 4 5 6 7]]
  (println a b c d e))

(let [[[x1 y1] [x2 y2]] [[1 2] [3 4]]]
  (println x1 y1 x2 y2))

;ASSOCIATIVE DESTRUCTURING
;(:as) binds to the entire sequence
;(:or) provides default values
(def some-map {:a 5 :c 3})

(let [{a :a b :b c :c :as m :or {a 1 b 2}} some-map]
  (println a b c m))

;To avoid redundancy, we can use :keys to bind entries to symbols with the same name
(let [{:keys [a b c] :or {a 1 b 2} :as m} some-map]
  (println a b c m))

;Using a prefix or auto-resolved prefixes
(let [m {:x/a 1, :y/b 2 ::x 10}
      {:keys [x/a y/b ::x]} m]
  (println a b m x))

;Using a namespace to lookup symbols in the map
(let [m #:c4{:a 1 :b 2}
      {:c4/keys [a b]} m]
  (println a b))

;NESTED DESTRUCTURING
(let [m {:a 1 :b 2 :v [3 4 5 6]}
      {a :a b :b [c d e f] :v g :g :or {g 7}} m]
  (println a b c d e f g))



