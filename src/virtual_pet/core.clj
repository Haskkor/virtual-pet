(ns virtual-pet.core
  (:gen-class)
  (:require [clj-time.core :as time]))


(defn grow-older "Take a pet current age and change the value if need be"
  [current-age]
  (when (> (time/in-days (time/interval (:last-check current-age) (time/now))) 0)
    (merge current-age {:current-value (inc (:current-value current-age))
                        :last-check    (time/now)})))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


;(grow-older {:birth-date (time/now) :creation-date (time/now) :current-value 3 :last-check (time/minus (time/now) (time/days 2))})
;(feed-pet "Meal" pet)
;(heal-pet pet)
;(pet-pet {:current-value 3 :last-check (time/now)})
;(ground-pet pet)
;(clean-pet {:current-value 1 :last-check (time/now)})
;(change-lights {:current-value true :last-check (time/now)})
;(get-full-stats pet)
;(get-happiness {:current-value 3 :last-check (time/now)})
