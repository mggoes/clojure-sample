(ns reference.c19-agents
  (:use [clojure.pprint])
  (:import (java.util.concurrent SynchronousQueue)))

;AGENT
;Supports asynchronous changes according to the result of an action
(defn change-agent
  [a k v]
  (println "================> [" (.getName (Thread/currentThread)) "]" "Sleeping...")
  (Thread/sleep 3000)
  (println "================> [" (.getName (Thread/currentThread)) "]" "Woke up!")
  (assoc a k v))

(def a (agent {:p1 "Param 1"}))
(pprint a)

;SEND
;Changes the agent's value
;Function is executed in another thread
;The new value of the agent will be the result of the action
(pprint (send a change-agent :p2 "Params 2"))
(pprint a)

(Thread/sleep 3000)
(pprint a)
