(ns virtual-pet.life
  (:require [clj-time.core :as time])
  (:require [clj-time.coerce :as coerce]
            [virtual-pet.db :as db-actions]
            [virtual-pet.utils :as utils]))


(defn grow-older "Take a pet current age and change the value if need be"
  [pet]
  (if (> (time/in-days (time/interval (coerce/from-string (get-in pet [:age :last-check])) (time/now))) 0)
    (utils/deep-merge pet {:age {:current-value (inc (get-in pet [:age :current-value]))
                                 :last-check    (str (time/now))}})
    pet))


(defn loose-weight "Change the weight value over time"
  [pet]
  (let [hg (get-in pet [:hunger :current-value])
        t (get-in pet [:hunger :last-check])]
    (if (and (>= hg 3) (> (time/in-minutes (time/interval (coerce/from-string t)
                                                          (time/now))) 30))
      (utils/deep-merge pet {:hunger {:current-value (dec hg)
                                      :last-check    (str (time/now))}})
      pet)))


(defn loose-happiness "Change the happiness value over time"
  [pet]
  (let [hp (:happiness pet)
        hg (get-in pet [:hunger :current-value])
        sk (get-in pet [:sickness :current-value])
        hpt (get-in pet [:happiness :last-check])
        sl (get-in pet [:sleeping :current-value])
        slt (get-in pet [:sleeping :last-check])
        lt (get-in pet [:lights :current-value])]
    (cond (> (time/in-minutes (time/interval (coerce/from-string hpt) (time/now))) 30)
          (when (>= 3 hg) (merge hp {:current-value (- (:current-value hp) 2)
                                     :last-check    (str (time/now))}))
          (when (>= 3 sk) (merge hp {:current-value (dec (:current-value hp))
                                     :last-check    (str (time/now))}))



          (when (and (not lt) (not sl)) (merge hp {:current-value (- (:current-value hp) 2)
                                                   :last-check    (str (time/now))})))



    (if (> (time/in-hours (time/interval (coerce/from-string hpt) (time/now))) 2)
      (merge hp {:current-value (dec (:current-value hp)) :last-check (str (time/now))}))
    (utils/deep-merge pet {:happiness hp})))


(defn increase-hunger "Change the hunger value over time"
  [pet]
  (if (> (time/in-days (time/interval (coerce/from-string (get-in pet [:hunger :last-check])) (time/now))) 0)
    (utils/deep-merge pet {:age {:current-value (inc (get-in pet [:hunger :current-value]))
                                 :last-check    (str (time/now))}})
    pet))


(defn check-lights "Change values based on the lights status"
  [pet]
  ())


(defn live "Apply all the life functions to a pet"
  []
  (let [pets (db-actions/get-all-pets)]
    (mapv #(-> %
               grow-older
               loose-weight
               loose-happiness
               increase-hunger
               check-lights
               db-actions/update-pet)
          pets)))
