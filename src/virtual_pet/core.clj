(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [config :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [monger.collection :as mc]
            [mount.core :refer [defstate]]
            [virtual-pet.actions :as actions]
            [virtual-pet.db :refer [db]]
            [virtual-pet.http :as http]))


(defn is-name-taken
  [name username]
  (mc/any? db (get-in env [:mongo :coll]) {:name name :username username}))


(defn create-pet
  [request]
  (let [body (http/get-body request)
        name (:name body)
        username (:username body)
        initial-pet (actions/template-pet name username)]
    (if (not (is-name-taken name username))
      (try
        (mc/insert db coll initial-pet)
        (http/standard-response "Create initial pet ok")
        (catch Exception e (http/standard-error (str "Error writing the initial pet: " (.getMessage e)))))
      (http/standard-error "Name already exists for this user"))))


(defroutes app
           (GET "/" [] "Hello World")
           (POST "/create-pet/" [] (fn [request] (create-pet request))))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))
