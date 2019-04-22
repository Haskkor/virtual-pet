(ns virtual-pet.core-test
  (:require [clojure.test :refer :all]
            [virtual-pet.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


;(grow-older {:birth-date (time/now) :creation-date (time/now) :current-value 3 :last-check (time/minus (time/now) (time/days 2))})
;(feed-pet "Meal" pet)
;(heal-pet pet)
;(pet-pet {:current-value 3 :last-check (time/now)})
;(ground-pet pet)
;(clean-pet {:current-value 1 :last-check (time/now)})
;(change-lights {:current-value true :last-check (time/now)})
;(get-full-stats pet)
;(get-happiness {:current-value 3 :last-check (time/now)})
