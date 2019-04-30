(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [mount.core :as mount]
            [virtual-pet.actions :as actions]
            [virtual-pet.db :as db-actions]
            [virtual-pet.status :as status]))


(defroutes app
           (GET "/" [] "Hello World")
           (GET "/full-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-full-stats (db-actions/get-pet pet-name username)))
           (GET "/base-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-base-stats (db-actions/get-pet pet-name username)))
           (GET "/hunger-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-specific-stat (db-actions/get-pet pet-name username) "hunger"))
           (GET "/happiness-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-specific-stat (db-actions/get-pet pet-name username) "happiness"))
           (GET "/sickness-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-specific-stat (db-actions/get-pet pet-name username) "sickness"))
           (GET "/dirtiness-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-specific-stat (db-actions/get-pet pet-name username) "dirtiness"))
           (GET "/anger-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-specific-stat (db-actions/get-pet pet-name username) "anger"))
           (POST "/create-pet/" [] (fn [request] (actions/create-pet request)))


           (POST "/feed-pet/" [] (fn [request] (actions/feed-pet request))))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))


(mount/start)
