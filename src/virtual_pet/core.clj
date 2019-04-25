(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [monger.collection :as mc]
            [monger.core :as mg]
            [mount.core :refer [defstate]]
            [virtual-pet.actions :as actions]
            [virtual-pet.http :as http]))



;; http://clojuremongodb.info/articles/integration.html

;; first test
(defstate conn :start (create-conn)
               :stop (disconnect conn))


;; second test
(defstate settings
  :start {:mongo-uri "mongodb://localhost/my-database"})

(defn- mongo-connect
  [{:keys [mongo-uri]}]
  (mg/connect-via-uri mongo-uri))

(defn- mongo-disconnect
  [{:keys [conn] :as mongo-client}]
  (mg/disconnect conn))

(defstate mongo-client
  :start (mongo-connect settings)
  :stop (mongo-disconnect mongo-client))

(defn db [] (:db mongo-client))






(defn connect-database
  []
  (let [conn (mg/connect {:host "mongo" :port 27017})
        db (mg/get-db conn "virtual-pet")]
    {:coll "pets"
     :conn conn
     :db   db}))


(defn is-name-taken
  [dbConnect name username]
  (mc/any? (:db dbConnect) (:coll dbConnect) {:name name :username username}))


(defn create-pet
  [request]
  (let [body (http/get-body request)
        name (:name body)
        username (:username body)
        dbConnect (connect-database)
        initial-pet (actions/template-pet name username)]
    (if (not (is-name-taken dbConnect name username))
      (try
        (mc/insert (:db dbConnect) (:coll dbConnect) initial-pet)
        (mg/disconnect (:conn dbConnect))
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
