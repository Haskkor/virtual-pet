(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [monger.core :as mg]))


(let [conn (mg/connect)
      db   (mg/get-db conn "monger-test")])


(defroutes app
           (GET "/" [] "Hello World"))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))
