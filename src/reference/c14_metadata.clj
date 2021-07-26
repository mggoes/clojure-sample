(ns reference.c14-metadata
  (:use [clojure.pprint]))

;METADATA
;It is a map of data about a symbol or a collection

;Meta
;Returns the metadata of a symbol
(pprint (meta #'+))

;With Meta
(def m ^:hi [1 2 3])
(pprint (meta m))

(def m2 (with-meta m {:bye true}))
(pprint (meta m2))

;Print Meta
(binding [*print-meta* true]
  (prn m2))

;Vary Meta
;Applies a function to the metadata of the object using the arguments
(def m3 (vary-meta m merge {:bye true}))
(pprint (meta m3))

;Alter Meta
;Atomically sets the metadata
(def ^{:version 1} document "This is text")
(pprint (meta #'document))

(alter-meta! #'document #(update-in % [:version] inc))
(pprint (meta #'document))

;Reset Meta
;Replace all metadata of the symbol
(reset-meta! #'document {:v 1})
(pprint (meta #'document))
