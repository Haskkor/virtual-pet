(ns virtual-pet.http
  (:require [cheshire.core :as json]))


(defn standard-response
  [message]
  {:status  200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    message})


(defn standard-error
  [message]
  {:status  500
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    message})


(defn get-body
  [request]
  (let [body (slurp (.bytes (:body request)) :encoding "UTF-8")]
    (json/parse-string body true)))