(ns records-protocols.model)

(defprotocol Datavel
  (to-ms [this]))

(extend-type java.lang.Number
  Datavel
  (to-ms [this]
    this))

(extend-type java.util.Date
  Datavel
  (to-ms [this]
    (.getTime this)))

(extend-type java.util.Calendar
  Datavel
  (to-ms [this]
    (to-ms (.getTime this))))