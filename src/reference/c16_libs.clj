(ns reference.c16-libs
  (:import java.util.Date
           java.util.Calendar)
  ;or
  ;(:import (java.util Date Calendar))
  (:use [clojure.string :only [join]])
  (:require [clojure.java.io :as jio]))

;==============================================================

;(:import java.util.Date
;           java.util.Calendar)
(println (Date.))

;(:import java.util.Date
;           java.util.Calendar)
(println (Calendar/getInstance))

;(:use [clojure.string :only [join]])
(println (join "," [1 2 3]))

;(:require [clojure.java.io :as jio])
(println (jio/as-url "http://clojure.org"))

;Both requires are equivalent
;(require 'java.util.Random 'java.util.Date 'Calendar)
;(require '(java.util Random Date Calendar))
