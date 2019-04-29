(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [monger.collection :as mc]
            [mount.core :as mount]
            [virtual-pet.actions :as actions]
            [virtual-pet.db :refer [db conn]]
            [virtual-pet.http :as http]
            [virtual-pet.status :as status]))


(defn is-name-taken
  [name username]
  (mc/any? db "pets" {:name name :username username}))


(defn create-pet
  [request]
  (let [body (http/get-body request)
        name (:name body)
        username (:username body)
        initial-pet (actions/template-pet name username)]
    (println db)
    (println name)
    (if (not (is-name-taken name username))
      (try
        (mc/insert db "pets" initial-pet)
        (http/standard-response "Create initial pet ok")
        (catch Exception e (http/standard-error (str "Error writing the initial pet: " (.getMessage e)))))
      (http/standard-error "Name already exists for this user"))))


(defn get-pet
  [name username]
  (mc/find-one-as-map db "pets" {:name name :username username}))


(defroutes app
           (GET "/" [] "Hello World")
           (GET "/full-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-full-stats (get-pet pet-name username)))
           (GET "/base-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-base-stats (get-pet pet-name username)))
           (GET "/hunger-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-hunger (get-pet pet-name username)))
           (GET "/happiness-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-happiness (get-pet pet-name username)))
           (GET "/sickness-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-sickness (get-pet pet-name username)))
           (GET "/dirtiness-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-dirtiness (get-pet pet-name username)))
           (GET "/anger-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-anger (get-pet pet-name username)))
           (POST "/create-pet/" [] (fn [request] (create-pet request))))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))


(mount/start)
