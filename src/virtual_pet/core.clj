(ns virtual-pet.core
  (:gen-class))


(def constants "TODO: Put in database"
  {:base {:age 0
          :happiness 3
          :hunger 3
          :sickness 0}
   :thresholds ""})


(def pet "TODO: Put in database"
  [:age 0
   :happiness 3
   :hunger 3
   :name "JeremyGotchi"
   :sickness 0])


(defn create-pet "TODO: API endpoint to create a pet in the database"
  [name]
  [:age (get-in constants [:base :age])
   :happiness (get-in constants [:base :happiness])
   :hunger (get-in constants [:base :hunger])
   :name name
   :sickness (get-in constants [:base :sickness])])


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
