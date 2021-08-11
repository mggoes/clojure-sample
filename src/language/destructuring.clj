(ns language.destructuring)

;SEQUENTIAL DESTRUCTURING
;(& d) is like a vararg
;(:as) binds to the entire sequence
;In sequential cases destructuring will match the target positions
(let [[a b c & d :as e] [1 2 3 4 5 6 7]]
  (println a b c d e))

;Remaining values
(let [[a b & rest] [1 2 3 4 5]]
  (println a b rest))

;Ignoring values
(let [[a b _ _ c] [1 2 3 4 5]]
  (println a b c))

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

;strs
(def m {"first-name" "Joe" "last-name" "Smith"})
(let [{:strs [first-name last-name]} m]
  (println first-name last-name))

;syms
(def m {'first-name "Joe" 'last-name "Smith"})
(let [{:syms [first-name last-name]} m]
  (println first-name last-name))

;NESTED DESTRUCTURING
(let [m {:a 1 :b 2 :v [3 4 5 6]}
      {a :a b :b [c d e f] :v g :g :or {g 7}} m]
  (println a b c d e f g))
