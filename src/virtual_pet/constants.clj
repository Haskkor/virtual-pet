(ns virtual-pet.constants
  (:require [clj-time.core :as time]))


(def constants "TODO: Put in database"
  {:base       {:age       0
                :anger     0
                :dirtiness 0
                :happiness 3
                :hunger    3
                :lights    true
                :sickness  0}
   :thresholds {:dirtiness 5
                :happiness 5}
   :food       {:meal  {:type        "Meal"
                        :weight-gain 1}
                :candy {:type        "Candy"
                        :weight-gain 2}}})


(def pet "REFERENCE ONLY: Types"
  {:age       {:birth-date    (time/now)
               :creation-date (time/now)
               :current-value 0
               :last-check    (time/now)}
   :anger     {:current-value 0
               :last-check    (time/now)}
   :dirtiness {:current-value 0
               :last-check    (time/now)}
   :happiness {:current-value 3
               :last-check    (time/now)}
   :hunger    {:current-value 3
               :last-check    (time/now)}
   :lights    {:current-value true
               :last-check    (time/now)}
   :name      "JeremyGotchi"
   :sickness  {:current-value 0
               :last-check    (time/now)}
   :weight    10})

