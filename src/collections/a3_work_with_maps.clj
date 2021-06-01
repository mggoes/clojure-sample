(ns collections.a3-work-with-maps
  (:require
    [collections.db]
    [collections.db :as c.db]))

(println (c.db/todos-os-pedidos))
(println (collections.db/todos-os-pedidos))

;===================================================
;Group by
(println (group-by :usuario (c.db/todos-os-pedidos)))

(println (map count (vals (group-by :usuario (c.db/todos-os-pedidos)))))

(->> (c.db/todos-os-pedidos)
     (group-by :usuario)
     vals
     (map count)
     println)

(defn conta-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id       usuario
   :total-de-pedidos (count pedidos)})

(->> (c.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)