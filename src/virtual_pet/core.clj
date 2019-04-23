(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [monger.collection :as mc]
            [monger.core :as mg]
            [virtual-pet.actions :as actions]
            [virtual-pet.http :as http]))


(defn connect-database
  []
  (let [conn (mg/connect)
        db (mg/get-db conn "virtual-pet")]
    {:coll "pets"
     :conn conn
     :db   db}))


(defn is-name-taken
  [dbConnect name username]
  (mc/any? (:db dbConnect) (:coll dbConnect) {:name name :username username}))


(defn create-pet
  [name username]
  (let [dbConnect (connect-database)
        initial-pet (actions/create-pet name username)]
    (if (not (is-name-taken dbConnect name username))
      (try
        (mc/insert (:db dbConnect) (:coll dbConnect) initial-pet)
        (mg/disconnect (:conn dbConnect))
        (http/standard-response "Create initial pet ok")
        (catch Exception e (http/standard-error (str "Error writing the initial pet: " (.getMessage e)))))
      (http/standard-error "Name already exists for this user"))))


(defroutes app
           (GET "/" [] "Hello World")
           (POST "/create-pet/" [] (fn [request] ((let [body (http/get-body request)]
                                                    (create-pet (:name body) (:username body)))))))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))
