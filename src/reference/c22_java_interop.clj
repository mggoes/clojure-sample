(ns reference.c22-java-interop)

;CLASS ACCESS
;All classes in java.lang are automatically imported to every namespace
(println Number)
(println java.lang.String)
(println java.time.LocalDate)

;MEMBER ACCESS
;Call a method
(println (.toUpperCase "Clojure"))
(println (.getName String))

;Access a member
(println (.-x (java.awt.Point. 1 2)))

;Call a static method
(println (System/getProperty "java.vm.version"))

;Access a constant
(println Math/PI)

;DOT FORM
;Call a method
;(. instance-expr member-symbol)
(def d (java.time.LocalDate/now))
(println (. d getMonth))

;Access a constant
;(. Classname-symbol member-symbol)
(println (. java.time.LocalDate EPOCH))

;Access a member
;(. instance-expr -field-symbol)
(println (. (java.awt.Point. 1 2) -x))

;Call a method with args
;(. instance-expr (method-symbol args*)) or (. instance-expr method-symbol args*)
(println (. d get java.time.temporal.ChronoField/DAY_OF_MONTH))

;Call a static method with args
;(. Classname-symbol (method-symbol args*)) or (. Classname-symbol method-symbol args*)
(println (. java.time.LocalDate parse "2021-07-27"))

;Make a call on the result of the first call
;(.. Classname-symbol member+)
(println (.. System (getProperties) (get "os.name")))

;Chained calls
;(doto instance-expr (instanceMethodName-symbol args*)*)
(println (doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2)))

;Call constructor
(println (new Integer 10))
;Or
(println (Integer. 10))

;PROXY
(def p (proxy [Runnable] [] (run [] (println "Running..."))))
(.run p)

;TYPE HINTS
(defn len
  [^String x]
  (.length x))
(println (len "Clojure"))
