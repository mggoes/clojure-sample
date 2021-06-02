(ns generators-property-tests.core
  (:use clojure.pprint)
  (:require [clojure.test.check.generators :as gen]
            [generators-property-tests.model :as g.model]
            [schema-generators.generators :as g]))

(println (gen/sample gen/boolean 3))
(println (gen/sample gen/small-integer))
(println (gen/sample gen/string-alphanumeric 5))

(println (gen/sample (gen/vector gen/small-integer 3) 5))
(println (gen/sample (gen/vector gen/small-integer 1 3) 5))

(println (g/sample 5 g.model/PacienteID))
(pprint (g/sample 5 g.model/Departamento))
(pprint (g/sample 5 g.model/Hospital))