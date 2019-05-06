(ns virtual-pet.life
  (:require [clj-time.core :as time]
            [virtual-pet.db :as db-actions]
            [virtual-pet.utils :as utils]))


(defn grow-older "Take a pet current age and change the value if need be"
  [pet]
  (when (> (time/in-days (time/interval (get-in pet [:age :last-check]) (time/now))) 0)
    (utils/deep-merge pet {:age {:current-value (inc (get-in pet [:age :current-value]))
                                 :last-check    (time/now)}})))


(defn live "Apply all the life functions to a pet"
  []
  (let [pets (db-actions/get-all-pets)]
    (mapv #(-> %
               grow-older
               grow-older
               grow-older
               db-actions/update-pet)
          pets)))