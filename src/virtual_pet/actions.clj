(ns virtual-pet.actions
  (:require [clj-time.core :as time]
            [clojure.string :as str]
            [monger.collection :as mc]
            [virtual-pet.constants :as const]
            [virtual-pet.db :as db-actions]
            [virtual-pet.http :as http])
  (:import (org.bson.types ObjectId)))


(defn template-pet
  [name username]
  {:age       {:birth-date    (str (time/now))
               :creation-date (str (time/now))
               :current-value (get-in const/constants [:base :age])
               :last-check    (str (time/now))}
   :anger     {:current-value 0
               :last-check    (str (time/now))}
   :dirtiness {:current-value 0
               :last-check    (str (time/now))}
   :happiness {:current-value (get-in const/constants [:base :happiness])
               :last-check    (str (time/now))}
   :hunger    {:current-value (get-in const/constants [:base :hunger])
               :last-check    (str (time/now))}
   :_id       (ObjectId.)
   :lights    {:current-value true
               :last-check    (str (time/now))}
   :name      name
   :sickness  {:current-value (get-in const/constants [:base :sickness])
               :last-check    (str (time/now))}
   :username  username
   :weight    10})


(defn create-pet "Create a new pet"
  [request]
  (let [body (http/get-body request)
        name (:name body)
        username (:username body)
        initial-pet (template-pet name username)]
    (if (not (db-actions/is-name-taken name username))
      (try
        (mc/insert db "pets" initial-pet)
        (http/standard-response "Create initial pet ok")
        (catch Exception e (http/standard-error (str "Error writing the initial pet: " (.getMessage e)))))
      (http/standard-error "Name already exists for this user"))))


(defn feed-pet "Feed the pet a given type of food"
  [food pet]
  (let [hg (get-in pet [:hunger :current-value])
        is-candy (= food (get-in const/constants [:food :candy :type]))
        hp (get-in pet [:happiness :current-value])]
    (merge pet {:hunger    {:current-value (if (> hg 0) (dec hg) hg)
                            :last-check    (time/now)}
                :weight    (+ (:weight pet) (get-in const/constants [:food (keyword (str/lower-case food))
                                                                     :weight-gain]))
                :happiness (if is-candy
                             {:current-value (inc hp)
                              :last-check    (time/now)}
                             (:happiness pet))})))


(defn heal-pet "Give medicine to the pet"
  [pet]
  (let [hp (get-in pet [:happiness :current-value])
        sk (get-in pet [:sickness :current-value])]
    (merge pet {:sickness  {:current-value (if (> sk 0) (dec sk) sk)
                            :last-check    (time/now)}
                :happiness {:current-value (if (= sk 0) (dec hp) hp)
                            :last-check    (time/now)}})))


(defn pet-pet "Pet the pet"
  [current-happiness]
  (let [hp (:current-value current-happiness)]
    {:current-value (if (< hp (get-in const/constants [:thresholds :happiness])) (inc hp) hp)
     :last-check    (time/now)}))


(defn ground-pet "Ground the pet"
  [pet]
  (let [ag (get-in pet [:anger :current-value])
        hp (get-in pet [:happiness :current-value])]
    (merge pet {:anger     {:current-value (if (> ag 0) (dec ag) ag)
                            :last-check    (time/now)}
                :happiness {:current-value (if (= ag 0) (dec hp) hp)
                            :last-check    (time/now)}})))


(defn clean-pet
  [current-dirtyness]
  (let [dt (:current-value current-dirtyness)]
    {:current-value (if (< dt (get-in const/constants [:thresholds :dirtiness])) (dec dt) dt)
     :last-check    (time/now)}))


(defn change-lights
  [current-lights]
  {:current-value (not (:current-value current-lights))
   :last-check    (time/now)})
