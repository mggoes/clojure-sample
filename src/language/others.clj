(ns language.others)

;COMPLEMENT
;Creates a function that receives the same parameters of the original function, executes it, negates the result and returns it.
(def not-empty? (complement empty?))
(println (not-empty? []))

;COMP
(def my-comp (comp inc *))
(println (my-comp 2 3))

;MEMOIZE
;Keeps a cache and returns immediately when the same arguments are used
(defn slow-function
  [msg]
  (Thread/sleep 1000)
  msg)
(println (slow-function "Done 1"))

(def memoized-slow-function (memoize slow-function))
(println (memoized-slow-function "Done 2"))
(println (memoized-slow-function "Done 2"))
(println (memoized-slow-function "Done 3"))

;EVAL
;Evaluates the form
(println (eval '(+ 1 2)))

;READ-STRING
;Reads a string and produces a data structure
(println (read-string "(+ 1 2)"))
(println (eval (read-string "(+ 1 2)")))

;FUTURE
;Creates and executes a task on another thread
(def task (future (Thread/sleep 3000)
            (println "Done!")
            "Task done!"))
(println @task)
(println (realized? task))

;DELAY
;Creates a task to be executed on another thread when deref or force is called
(def message (delay (Thread/sleep 2000)
               "Some message!"))
(println (force message))
(println @message)

;PROMISE
;Creates a promise that can be read. A result can be provided once later
(def pro (promise))
(deliver pro "Promise")
(println @pro)
