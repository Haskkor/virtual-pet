(ns virtual-pet.life
  (:require [clj-time.core :as time]
            [virtual-pet.utils :as utils]))


(defn grow-older "Take a pet current age and change the value if need be"
  [current-age]
  (when (> (time/in-days (time/interval (:last-check current-age) (time/now))) 0)
    (utils/deep-merge current-age {:current-value (inc (:current-value current-age))
                                   :last-check    (time/now)})))
