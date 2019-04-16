(ns virtual-pet.core
  (:gen-class)
  (:require [clj-time.core :as time]
            [clj-time.local :as local-time]
            [clojure.string :as str]))


(def constants "TODO: Put in database"
  {:base       {:age       0
                :happiness 3
                :hunger    3
                :sickness  0}
   :thresholds ""
   :food       {:meal  {:type        "Meal"
                        :weight-gain 1}
                :candy {:type        "Candy"
                        :weight-gain 2}}})


(def pet "REFERENCE ONLY: Types"
  {:age       {:birth-date    (time/now)
               :creation-date (time/now)
               :current-value 0
               :last-check    (time/now)}
   :happiness {:current-value 3
               :last-check    (time/now)}
   :hunger    {:current-value 3
               :last-check    (time/now)}
   :name      "JeremyGotchi"
   :sickness  {:current-value 0
               :last-check    (time/now)}
   :weight    10})


(defn create-pet "TODO: API endpoint to create a pet in the database"
  [name]
  {:age       {:birth-date    (time/now)
               :creation-date (time/now)
               :current-value (get-in constants [:base :age])
               :last-check    (time/now)}
   :happiness {:current-value (get-in constants [:base :happiness])
               :last-check    (time/now)}
   :hunger    {:current-value (get-in constants [:base :hunger])
               :last-check    (time/now)}
   :name      name
   :sickness  {:current-value (get-in constants [:base :sickness])
               :last-check    (time/now)}
   :weight    10})


(defn grow-older "Take a pet current age and change the value if need be"
  [current-age]
  (when (> (time/in-days (time/interval (:last-check current-age) (time/now))) 0)
    (merge current-age {:current-value (inc (:current-value current-age))
                        :last-check    (time/now)})))


(defn feed-pet "Feed the pet a given type of food"
  [food pet]
  (let [hg (get-in pet [:hunger :current-value])
        is-candy (= food (get-in constants [:food :candy :type]))
        hp (get-in pet [:happiness :current-value])]
    (merge pet {:hunger    {:current-value (if (> hg 0) (dec hg) hg)
                            :last-check    (time/now)}
                :weight    (+ (:weight pet) (get-in constants [:food (keyword (str/lower-case food)) :weight-gain]))
                :happiness (if is-candy
                             {:current-value (inc hp)
                              :last-check    (time/now)}
                             (:happiness pet))})))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


;(grow-older {:birth-date (time/now) :creation-date (time/now) :current-value 3 :last-check (time/minus (time/now) (time/days 2))})
;(feed-pet "Meal" pet)
