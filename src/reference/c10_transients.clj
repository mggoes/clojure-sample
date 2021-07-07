(ns reference.c10-transients)

;==============================================================
;TRANSIENTS
;Are fast!
;Provides high performance optimization
;Shares structure with persistent source
;Works with vectors, hash maps and hash sets
;Cannot be used after a persistent! call
(defn vrange [n]
  (loop [i 0 v []]
    (if (< i n)
      (recur (inc i) (conj v i))
      v)))

(defn vrange2 [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))

(time (vrange 1000000))
(time (vrange2 1000000))

(def t (transient [1 2 3 4 5]))
(println t)

(def t (conj! t 6))
(println t)

(dotimes [n 5]
  (println (t n)))

(def v (persistent! t))
(println v)

;It will throw an exception because transient vector is being used after persistent! call
;(dotimes [n 5]
;  (println (t n)))