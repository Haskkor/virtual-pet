(defproject virtual-pet "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.15.0"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot virtual-pet.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
