(ns reference.c21-reducers
  (:use [clojure.pprint])
  (:require [clojure.core.reducers :as r]))

;REDUCERS
;Reduces a collection, sequence or vector
;May (or may not) use Java's fork/join framework

;FOLD
;Reduces a collection
(pprint (r/fold + (r/filter even? (r/map inc [1 1 1 2]))))

;INTO
;Merges the items into the collection
(pprint (into [] (r/filter even? (r/map inc (range 10)))))

;FOLDCAT
;Reduces and merges the items of the collections
(pprint (r/foldcat (r/filter even? (r/map inc (range 10)))))
