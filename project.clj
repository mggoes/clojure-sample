(defproject clojure-sample "0.1.0-SNAPSHOT"
  :description "Clojure Sample"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/test.check "1.1.0"]
                 [prismatic/schema "1.1.12"]
                 [prismatic/schema-generators "0.1.3"]
                 [org.clojure/core.async "1.3.618"]]

  ;Makes leiningen to add this namespace/class as the entry point for the program (adds a meta-inf/manifest.mf)
  ;lein uberjar
  :main ^{:skip-aot false} language.main)
;Para usar uma namespace especifica no REPL basta chamar a funcao use. Ex.: (use 'introcution.basics)
;:repl-options {:init-ns introduction.init})
