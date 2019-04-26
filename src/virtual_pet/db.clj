(ns virtual-pet.db
  (:require [monger.core :as mg]
            [mount.core :refer [defstate]]))


(defstate conn
          :start (mg/connect {:host "mongo" :port 27017})
          :stop (mg/disconnect conn))


(defstate db
          :start (mg/get-db conn "virtual-pet"))
