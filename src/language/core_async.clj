(ns language.core-async
  (:require [clojure.core.async :as a :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]]))

;GO
;Executes concurrently on a separate thread from a thread pool
(go (println "First go block!"))

;CHANNELS
(def echo-channel (chan))

;Retrieves a message from channel
(go (while true
      (println (<! echo-channel))))

;Adds a message to channel
(>!! echo-channel "ketchup")

;THREAD
;Executes on another thread and returns a channel
;It is intended to be used with long-term processing
(thread (println (<!! echo-channel)))
(>!! echo-channel "mustard")

;<! and >! must be used inside a go block
;<!! and >!! can be used outside a go block

;CLOSE
;(close! echo-channel)

;ALTS
;Receives a vector of channels and try to retrieve a value from them concurrently
;Returns the value and the channel that returned that value
(defn slow-fn
  [message channel]
  (go (Thread/sleep (rand 100))
      (>! channel message)))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]

  (slow-fn "Message 1" c1)
  (slow-fn "Message 2" c2)
  (slow-fn "Message 3" c3)

  (let [[value winning-channel] (alts!! [c1 c2 c3])]
    (println value)
    (println winning-channel)))

;Timeout
(let [c1 (chan)
      timeout-channel (timeout 5)]

  (slow-fn "Message 1" c1)

  (let [[value winning-channel] (alts!! [c1 timeout-channel])]
    (if value
      (do (println value)
          (println winning-channel))
      (println "Timed out!"))))

;Putting a value
(let [c1 (chan)
      c2 (chan)]

  (go (<! c2))

  (let [[value winning-channel] (alts!! [c1 [c2 "Some message!"]])]
    (println value)
    (println winning-channel)))
