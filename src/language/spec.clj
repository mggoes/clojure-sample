(ns language.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :refer :all])
  (:import (java.util Date)))

;SPEC is meant to be used to specify the structure of data and validate it
(println (s/conform even? 1000))

;VALID?
(println (s/valid? even? 10))

;DEF
(s/def :spec/date inst?)
(println (s/valid? :spec/date (Date.)))

(doc :spec/date)

;AND
(s/def :num/int-even (s/and int? even?))
(println (s/valid? :num/int-even 10))

;OR
(s/def :domain/int-or-string (s/or
                               :int int?
                               :string string?))
(println (s/valid? :domain/int-or-string 10))
(println (s/valid? :domain/int-or-string "string"))
(println (s/valid? :domain/int-or-string 10.0))
(println (s/conform :domain/int-or-string "string"))

;NILABLE
(println (s/valid? (s/nilable string?) nil))

;EXPLAIN
(s/explain :num/int-even 11)

;KEYS
(s/def :acc/person (s/keys
                     :req [:acc/name :acc/id]
                     :opt [:acc/email]))
(println (s/valid? :acc/person {:acc/name  "Name"
                                :acc/id    12345
                                :acc/email "email@email.com"}))
(println (s/valid? :acc/person {:acc/name "Name"
                                :acc/id   12345}))
(s/explain :acc/person {:acc/name "Name"})

;MERGE
(s/def :animal/common (s/keys :req [:animal/kind :animal/says]))
(s/def :animal/dog (s/merge :animal/common
                     (s/keys :req [:dog/tail?])))
(s/explain :animal/dog {:animal/kind "dog"
                        :animal/says "woof"
                        :dog/tail?   true})
(s/explain :animal/dog {:animal/kind "dog"
                        :dog/tail?   true})

;MULTI-SPEC
(s/def :event/type keyword?)
(s/def :event/timestamp int?)
(s/def :search/url string?)
(s/def :error/message string?)
(s/def :error/code int?)

(defmulti event-type :event/type)
(defmethod event-type :event/search [_]
  (s/keys :req [:event/type :event/timestamp :search/url]))
(defmethod event-type :event/error [_]
  (s/keys :req [:event/type :event/timestamp :error/message :error/code]))

(s/def :event/event (s/multi-spec event-type :event/type))

(s/explain :event/event
  {:event/type      :event/search
   :event/timestamp 1463970123000
   :search/url      "https://clojure.org"})

(s/explain :event/event
  {:event/type      :event/error
   :event/timestamp 1463970123000
   :error/message   "Invalid host"
   :error/code      500})

(s/explain :event/event
  {:event/type :event/restart})

(s/explain :event/event
  {:event/type :event/search
   :search/url 200})

;COLL-OF
(s/def :coll/validate (s/coll-of keyword? :kind vector? :count 3))
(s/explain :coll/validate [:a :b :c])
(s/explain :coll/validate [:a :b])

;TUPLE
(s/def :geom/point (s/tuple double? double? double?))
(s/explain :geom/point [1.5 2.5 -0.5])
(s/explain :geom/point [1.5 2.5 5])

;MAP-OF
(s/def :map/validate (s/map-of keyword? int?))
(s/explain :map/validate {:a 1 :b 2})
(s/explain :map/validate {:a 1 :b 2.5})
(s/explain :map/validate {:a 1 "b" 2})

;CAT
(s/def :cook/ingredient (s/cat :quantity number? :unit keyword?))
(s/explain :cook/ingredient [2 :teaspoon])
(s/explain :cook/ingredient [11])

;* + ?
(s/def :ex/seq-of-keywords (s/* keyword?))
(s/explain :ex/seq-of-keywords [:a :b :c])

(s/def :ex/odds-then-maybe-even (s/cat
                                  :odds (s/+ odd?)
                                  :even (s/? even?)))
(s/explain :ex/odds-then-maybe-even [1 3 5 100])
(s/explain :ex/odds-then-maybe-even [1])
(s/explain :ex/odds-then-maybe-even [100])

;DESCRIBE
(println (s/describe :ex/odds-then-maybe-even))
