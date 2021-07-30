(ns guides.learn
  (:import (java.io StringWriter)
           (java.util UUID)))

;ANONYMOUS FUNCTIONS WITH VARIADIC
;(fn [x y & zs] (println x y zs))
(def message #(println %1 %2 %&))
(message "Hello" "user")
(message "Hello" "user1" "user2")
(message "Hello" "user1" "user2" "user3")

;SORTED SET
(println (sorted-set "Bravo" "Charlie" "Sigma" "Alpha" "Alpha"))

;INTO
(println (into #{"a" "b"} ["c" "d"]))

;CASE
(def x 10)
(println (case x
           1 "x is one"
           5 "x is five"
           11 "x is ten"
           "x is a number"))

;DOSEQ
;Returns nil
(println (doseq [letter [:a :b]
                 number (range 3)]
           (println [letter number])))

;FOR
;Returns a list
(println (for [letter [:a :b]
               number (range 3)]
           [letter number]))

;WITH-OPEN
;Similar to a try-with-resources
;Calls close() on all bindings at the end
(def writter (StringWriter.))
(with-open [w writter]
  (.write w "some text"))
(println (.toString writter))

;UUID
;Double dot notation
(println (.. (UUID/randomUUID) toString))
