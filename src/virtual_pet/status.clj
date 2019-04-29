(ns virtual-pet.status
  (:require [virtual-pet.http :as http]))


(defn get-full-stats
  [pet]
  (if (some? pet)
    (let [name (:name pet)
          age (get-in pet [:age :current-value])
          wght (:weight pet)
          lght (get-in pet [:lights :current-value])
          hg (get-in pet [:hunger :current-value])
          hp (get-in pet [:happiness :current-value])
          sk (get-in pet [:sickness :current-value])
          dt (get-in pet [:dirtiness :current-value])
          ag (get-in pet [:anger :current-value])]
      (http/standard-response (clojure.string/join "" [name ": " (str age) " day(s) old, " (str wght) " grams. Lights on: " (str lght)
                                                       ". Hunger: " (str hg) ". Happiness: " (str hp) ". Sickness: " (str sk) ". Dirtiness: "
                                                       (str dt) ". Anger: " (str ag) "."])))
    (http/standard-error "Pet does not exit for this user")))



(defn get-base-stats
  [pet]
  (let [name (:name pet)
        age (get-in pet [:age :current-value])
        wght (:weight pet)
        lght (get-in pet [:lights :current-value])]
    (clojure.string/join "" [name ": " (str age) " day(s) old, " (str wght) " grams. Lights on: " (str lght)])))




;; TESTING
(defn get-specific-stat
  [pet stat-name]
  (let [st (get-in pet [(keyword stat-name) :current-value])]
    (clojure.string/join [stat-name (str hg) "."])))





(defn get-hunger
  [pet]
  (let [hg (get-in pet [:hunger :current-value])]
    (clojure.string/join ["Hunger: " (str hg) "."])))


(defn get-happiness
  [pet]
  (let [hp (get-in pet [:happiness :current-value])]
    (clojure.string/join ["Happiness: " (str hp) "."])))


(defn get-sickness
  [pet]
  (let [sk (get-in pet [:sickness :current-value])]
    (clojure.string/join ["Sickness: " (str sk) "."])))


(defn get-dirtiness
  [pet]
  (let [dt (get-in pet [:dirtiness :current-value])]
    (clojure.string/join ["Dirtiness: " (str dt) "."])))


(defn get-anger
  [pet]
  (let [ag (get-in pet [:dirtiness :current-value])]
    (clojure.string/join ["Anger: " (str ag) "."])))
