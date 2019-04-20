(ns virtual-pet.status)


(defn get-full-stats
  [pet]
  (let [name (:name pet)
        age (get-in pet [:age :current-value])
        wght (:weight pet)
        lght (get-in pet [:lights :current-value])
        hg (get-in pet [:hunger :current-value])
        hp (get-in pet [:happiness :current-value])
        sk (get-in pet [:sickness :current-value])
        dt (get-in pet [:dirtiness :current-value])
        ag (get-in pet [:anger :current-value])]
    (clojure.string/join "" [name ": " (str age) " day(s) old, " (str wght) " grams. Lights on: " (str lght)
                             ". Hunger: " (str hg) ". Happiness: " (str hp) ". Sickness: " (str sk) ". Dirtiness: "
                             (str dt) ". Anger: " (str ag) "."])))


(defn get-base-stats
  [pet]
  (let [name (:name pet)
        age (get-in pet [:age :current-value])
        wght (:weight pet)
        lght (get-in pet [:lights :current-value])]
    (clojure.string/join "" [name ": " (str age) " day(s) old, " (str wght) " grams. Lights on: " (str lght)])))


(defn get-hunger
  [current-hunger]
  (let [hg (:current-value current-hunger)]
    (clojure.string/join ["Hunger: " (str hg) "."])))


(defn get-happiness
  [current-happiness]
  (let [hp (:current-value current-happiness)]
    (clojure.string/join ["Happiness: " (str hp) "."])))


(defn get-sickness
  [current-sickness]
  (let [sk (:current-value current-sickness)]
    (clojure.string/join ["Sickness: " (str sk) "."])))


(defn get-dirtiness
  [current-dirtiness]
  (let [dt (:current-value current-dirtiness)]
    (clojure.string/join ["Dirtiness: "(str dt) "."])))


(defn get-anger
  [current-anger]
  (let [ag (:current-value current-anger)]
    (clojure.string/join ["Anger: " (str ag) "."])))
