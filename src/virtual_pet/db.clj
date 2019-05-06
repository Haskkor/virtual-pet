(ns virtual-pet.db
  (:require [monger.collection :as mc]
            [monger.core :as mg]
            [mount.core :refer [defstate]]
            [virtual-pet.life :refer [live]]))


(defstate conn "Create the connection"
          :start (mg/connect {:host "mongo" :port 27017})
          :stop (mg/disconnect conn))


(defstate db "Get the database"
          :start (mg/get-db conn "virtual-pet"))


(defn is-name-taken "Check if a pet already exists with a given name for a given user"
  [name username]
  (mc/any? db "pets" {:name name :username username}))


(defn get-pet "Retrieve a pet"
  [name username]
  (mc/find-one-as-map db "pets" {:name name :username username}))


(defn update-pet "Update a pet"
  [pet]
  (let [id (:_id pet)]
    (mc/update db "pets" {:_id id} pet)))


(defn get-all-pets "Get all the pets"
  []
  (mc/find-maps db "pets"))
