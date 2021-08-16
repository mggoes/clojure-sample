(ns language.threading)

;THREAD FIRST
(def person {:name "Socrates" :age 39})

(defn transform
  [p]
  (-> p
    (assoc :hair-color :gray)
    (update :age inc)))

(println (transform person))
(println (-> person
           :name
           name
           clojure.string/upper-case))

;THREAD LAST
(defn calculate
  []
  (->> (range 10)
    (filter odd?)
    (map #(* % %))
    (reduce +)))
(println (calculate))

;AS->
;Makes a binding to the value and allows to use this value at any position
(println (as-> [:foo :bar] value
           (map name value)
           (first value)
           (.substring value 1)))

;COND->
;Takes (test, expr) pairs to thread the value
(defn describe-number [n]
  (cond-> []
    (odd? n) (conj "odd")
    (even? n) (conj "even")
    (zero? n) (conj "zero")
    (pos? n) (conj "positive")))
(println (describe-number 3))
