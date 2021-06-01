(ns records-protocols.logic
  (:require [records-protocols.model :as r.model]))

(defn agora
  []
  (r.model/to-ms (java.util.Date.)))