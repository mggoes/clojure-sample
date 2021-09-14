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

;ATOMS
(def a (atom {:a 1}))
(println @a)

;Reset
(reset! a {:a 0})
(println @a)

;Watch
(add-watch a :some-watch (fn [key watched old new]
                           (println "Key:" key "/ Watched:" watched "/ Old:" old "/ New:" new)))

(swap! a update-in [:a] inc)
(swap! a update-in [:a] inc)
(println @a)

;Validator
(defn simple-validator
  [{:keys [a]}]
  (if (> a 2) (throw (IllegalStateException. "Must be less than or equal to 2"))))
(def b (atom {:a 0} :validator simple-validator))
(swap! b update-in [:a] inc)
(swap! b update-in [:a] inc)
(swap! b update-in [:a] inc)
(println @a)

;REFS
(def r (ref {:a 0}))
(println @r)

;Commute
;During the commit, call the change function again with the most recent
;value from outside the transaction to compare the current value to the
;transaction value
(defn sleep-print-update
  [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state))
    (update-fn state)))
(def counter (ref 0))
(future (dosync (commute counter (sleep-print-update 100 "Thread A" inc))))
(future (dosync (commute counter (sleep-print-update 150 "Thread B" inc))))
(println @counter)

;VARS
;Allows to change values dynamically inside a binding context
;A dynamic var has the *name* form by default
(def ^:dynamic *my-var* "Test")
(println *my-var*)
(binding [*my-var* "Test 2"]
  (println *my-var*))
(println *my-var*)

;Set!
(def ^:dynamic *some-var* nil)
(println *some-var*)
(defn print-and-process
  []
  (println "Starting...")
  (when (thread-bound? #'*some-var*)
    (set! *some-var* 100)))
(binding [*some-var* 1]
  (println *some-var*)
  (print-and-process)
  (println *some-var*))
(println *some-var*)

;Alter var root
(def ^:dynamic *var1* 1)
(println *var1*)
(alter-var-root #'*var1* (fn [_] 10))
(println *var1*)

;With redefs
;Changes var value. The new value will be passed to child threads
(def ^:dynamic *var2* 1)
(println *var2*)
(with-redefs [*var2* 100]
  (doto (Thread. ^Runnable #(println *var2*)) .start .join))
(println *var2*)

;PMAP
;Executes a map function in parallel
(def data (take 10000 (repeatedly (partial rand-int 100))))
(time (dorun (map #(str "Number " %) data)))
(time (dorun (pmap #(str "Number " %) data)))

