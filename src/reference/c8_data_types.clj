(ns reference.c8-data-types
  (:import (java.lang Runnable)
           (java.lang.annotation Retention RetentionPolicy)
           (javax.annotation.processing SupportedOptions)))

;DEFPROTOCOL
;Similar to a Java interface
(defprotocol Polite
  ;Functions must provide at least one argument
  (greet [this] "Function to greet"))

;DEFTYPE
;Generates bytecode for a named class
;Supports mutable fields
;Provides a function ->Greeter to initialize the deftype
(deftype Greeter [^:unsynchronized-mutable message]
  Polite
  ;Implementing a method
  (greet
    ;A self reference must be provided
    [_]
    (println message)
    ;Changing field value
    (set! message "Test")))

(def g (Greeter. "Hello"))
(.greet g)
(.greet g)
(println (->Greeter "Test"))
(println (extends? Polite Greeter))

;DEFRECORD
;Similar to deftype
;Provides a complete implementation of a persistent map
;Fields are immutable
;Provides a function map->Greeter2 that takes a map and initializes the record
(defrecord Greeter2 [message name]
  Polite
  (greet
    [_]
    (println message name)))

(def r (Greeter2. "Hello" "User"))
(.greet r)
(println (->Greeter2 "Hello" "User"))
(println (map->Greeter2 {:message "Hello" :name "User"}))

;defrecord supports a different reader form to initialize the record #full.ns.name{:a 1 :b 2}
(def r2 #reference.c8_data_types.Greeter2{:message "Hellon 2" :name "User 2"})
(println r2)

;REIFY
;Similar to an anonymous inner class in Java
;Creates a instance of the specified type to be used inside a context
;Only protocols or interfaces are supported
(def r3 (reify Polite
          (greet
            [_]
            (println "Reify test"))))
(.greet r3)

;JAVA ANNOTATIONS
;It's possible to add annotations to types, fields and methods generated by deftype, defrecord and definterface
(defrecord ^{Retention        RetentionPolicy/RUNTIME
             SupportedOptions ["type"]} Greeter3 [^{String           true
                                                    Retention        RetentionPolicy/RUNTIME
                                                    SupportedOptions ["field"]} message]
  Polite
  (^{Retention        RetentionPolicy/RUNTIME
     SupportedOptions ["method"]} greet
    [this]
    (println message)))

(def g3 (Greeter3. "Some message"))
(.greet g3)
(println (seq (.getAnnotations Greeter3)))
(println (seq (.getAnnotations (.getField Greeter3 "message"))))
(println (seq (.getAnnotations (.getMethod Greeter3 "greet" nil))))
