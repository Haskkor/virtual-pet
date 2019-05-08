(ns virtual-pet.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [clojurewerkz.quartzite.scheduler :as scheduler]
            [clojurewerkz.quartzite.triggers :as triggers]
            [clojurewerkz.quartzite.jobs :as jobs]
            [clojurewerkz.quartzite.jobs :refer [defjob]]
            [clojurewerkz.quartzite.schedule.calendar-interval :refer [schedule with-interval-in-minutes]]
            [mount.core :as mount]
            [org.httpkit.server :refer [run-server]]
            [virtual-pet.actions :as actions]
            [virtual-pet.db :as db-actions]
            [virtual-pet.life :refer [live]]
            [virtual-pet.status :as status]))


(mount/start)


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
           (GET "/sleeping-stats-pet/:pet-name/user/:username" [pet-name username]
             (status/get-specific-stat (db-actions/get-pet pet-name username) "sleeping"))
           (POST "/create-pet/" [] (fn [request] (actions/create-pet request)))
           (POST "/feed-pet/" [] (fn [request] (actions/feed-pet request)))
           (POST "/heal-pet/" [] (fn [request] (actions/heal-pet request)))
           (POST "/pet-pet/" [] (fn [request] (actions/pet-pet request)))
           (POST "/ground-pet/" [] (fn [request] (actions/ground-pet request)))
           (POST "/clean-pet/" [] (fn [request] (actions/clean-pet request)))
           (POST "/change-lights/" [] (fn [request] (actions/change-lights request))))


(defjob NoOpJob
        [ctx]
        (live))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))
        s (-> (scheduler/initialize) scheduler/start)
        j (jobs/build
            (jobs/of-type NoOpJob)
            (jobs/with-identity (jobs/key "jobs.noop.1")))
        t (triggers/build
            (triggers/with-identity (triggers/key "triggers.1"))
            (triggers/start-now)
            (triggers/with-schedule (schedule (with-interval-in-minutes 1))))]
    (scheduler/schedule s j t)
    (run-server app {:port port})
    (println (str "Listening on port " port))))
