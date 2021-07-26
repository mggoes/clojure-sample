(ns reference.c6-other-functions)

;PRINTING
(println *out*)
(prn "Test")
(pr "Test")
(prn "Test")

(def s "===> ")
(println (prn-str s "Test"))

;REGEX SUPPORT

;Creating a regext with #""
(def regex #"[0-9]+")

;Returns a sequence os matches
(println (re-seq regex "abs123def345ghi567"))

;Returns the next match
(println (re-find regex "abs123def345ghi567"))

(println (re-matches #"hello.*" "hello world"))
