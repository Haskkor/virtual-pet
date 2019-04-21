(ns virtual-pet.core
  (:gen-class)
  (:require [clj-time.core :as time]
            [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]))


(defn grow-older "Take a pet current age and change the value if need be"
  [current-age]
  (when (> (time/in-days (time/interval (:last-check current-age) (time/now))) 0)
    (merge current-age {:current-value (inc (:current-value current-age))
                        :last-check    (time/now)})))


(defroutes app
           (GET "/" [] "Hello World"))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server app {:port port})
    (println (str "Listening on port " port))))


;(grow-older {:birth-date (time/now) :creation-date (time/now) :current-value 3 :last-check (time/minus (time/now) (time/days 2))})
;(feed-pet "Meal" pet)
;(heal-pet pet)
;(pet-pet {:current-value 3 :last-check (time/now)})
;(ground-pet pet)
;(clean-pet {:current-value 1 :last-check (time/now)})
;(change-lights {:current-value true :last-check (time/now)})
;(get-full-stats pet)
;(get-happiness {:current-value 3 :last-check (time/now)})
