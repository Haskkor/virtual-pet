(ns virtual-pet.db
  (:require [monger.core :as mg]
            [mount.core :refer [defstate]]
            [config :refer [env]]))


(defstate conn
          :start (mg/connect {:host (get-in env [:mongo :host]) :port (get-in env [:mongo :port])})
          :stop (mg/disconnect conn))


(defstate db
          :start (mg/get-db conn (get-in env [:mongo :db])))
