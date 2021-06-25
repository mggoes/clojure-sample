(ns reference.c1-reader
  (:import (java.time LocalDateTime)))

;==============================================================
;Forms

;Strings
(println "Some string")
(println "Some
multiline string")

;Numbers
(println 1)

;BigInt
(println 1N)

;Characters
(println \c)
(println \space \a)

;Nil
(println nil)

;Booleans
(println true false)

;Symbolic
(println ##Inf ##NaN)

;Keywords
(println :key :ns/key)

;Lists
(println '(1 2 3))

;Vectors
(println [1 2 3])

;Maps
(println {:a 1
          :b 2})

;Map namespace
(def some-map #:person{:name    "Matheus"
                       :last    "Goes"
                       :address #:address{:name "Principal"}})
(println some-map)

;Sets
(println #{1 2 3})

;==============================================================
;Macro characters

;Quote (')
(println 'scaped)

;Character (\)
(print \= \newline \a \newline \= \newline)

;Comment (;)
;Single line comment

;Deref (@)
(def some-atom (atom {}))
(println @some-atom)
(println (deref some-atom))

;Metadata (^)
;Map associated to some kind of objects
(def v ^{:a 1 :b 2} [1 2 3])
(println v)
(println (meta v))

(def s "abc")
(println (.contains ^String s "a"))
(println (.contains ^{:tag java.lang.String} s "a"))

;Creates a map where :dynamic is the key and true is the value. ^{:dynamic true}
(def v2 ^:dynamic [1 2 3])
(println v2)

;Dispatch (#)
;Makes reader to user another reader macro table
(println #{})

;Regex (#"")
(def r #"[\d]+")
(def m (.matcher r "123"))
(println (.matches m))

;Var quote (#')
(println #'v)
(println (var v))

;Anonymous function (#())
(def a #(println % %2 %3 %&))
(a "Anonymous" "function" "test" "test" "test 2")

;Ignore (#_)
#_(println "Ignored")

;Syntax quote
(def x 5)
(def lst '(a b c))
(println `(fred x ~x lst ~@lst 7 8 :nine))
;(reference.c1-reader/fred reference.c1-reader/x 5 reference.c1-reader/lst a b c 7 8 :nine)

;==============================================================
;Tagged literals
(defn dot-string
  [s]
  (str "." s))

(defn log
  [p]
  (println "==========>" p)
  p)

(defn local-date-time
  [p]
  (LocalDateTime/parse p))

(println (dot-string #ref/log "test"))
(println #ref/dot-string "test")

(def y #ref/log "some value")
(println y)

(def t #ref/local-date-time "2021-06-25T12:00:00")
(println t)
(println (class t))

;Built-in tagged literals
;#inst
(def i #inst "2021-06-25T12:00:00")
(println i)
(println (class i))

;#uuid
(def uuid #uuid "1d62f457-6432-43dd-b429-e5776d870c56")
(println uuid)
(println (class uuid))

;Rebinding tagged literals
(binding [*data-readers* {'inst local-date-time}]
  (def i #inst "2022-06-25T12:00:00")
  (println i)
  (println (class i)))

;Default data reader function
;Used when no data reader function is defined for a tagged literal
(defn default
  [tag value]
  (println "Invoked tag:" tag "with value" value)
  value)

(set! *default-data-reader-fn* default)

(def value #some-tag "value")
(println value)

;==============================================================
