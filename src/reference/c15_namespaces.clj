;==============================================================
;NAMESPACES
;Are created by the ns macro
;By default ns macro will create a namespace containing mappings for java.lang classes, clojure.lang.Compiler and clojure.core functions
;Both :require and :use load libs
(ns reference.c15-namespaces
  ;Generates a Java class
  (:gen-class
    :name br.com.reference.Namespaces)

  ;Imports functions from another namespace with an alias
  (:require [clojure.set :as set]

    ;Imports and refers to a specific function from a namespace
            [clojure.walk :refer [keywordize-keys]]

    ;Imports and refers to all functions from a namespace
            [clojure.walk :refer :all])

  ;Imports all functions from another namespace without an alias
  (:use [clojure.pprint :as pp]
        [clojure.instant]

    ;Loads only join function from the namespace clojure.string
        [clojure.string :only [join]])

  ;Imports Java classes from a package
  (:import [java.util Date Random]
           (java.beans Beans)
           (java.time LocalDate))

  ;Excludes functions from clojure.core
  (:refer-clojure :exclude [ancestors printf]))

;[clojure.pprint :as pp]
(pp/pprint (set/subset? #{1 2} #{1 2 3 4 5 6}))

;(clojure.instant)
(pprint (read-instant-date "2020-04-25T15:09:16.437Z"))

;[clojure.walk :refer [keywordize-keys]
(pprint (keywordize-keys {:a 1}))

;[clojure.walk :refer :all]
(pprint (stringify-keys {:a 1}))

;[java.util Date Random]
(pprint (Date.))
(pprint (.nextBoolean (Random.)))

;(java.beans Beans)
(pprint (Beans/isDesignTime))

;(java.time LocalDate)
(pprint (LocalDate/now))

;[clojure.string :only [join]]
(pprint (join "a" "b"))

;(:refer-clojure :exclude [ancestors printf])
;Will throw an error because printf is excluded
;(printf "1 + 2 is %s%n" 3)