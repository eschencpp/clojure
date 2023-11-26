(ns test
  (:require [clojure.pprint :refer [pprint]])) ; This is optional but can be helpful for formatting output

(defmacro my-macro [x]
  `(println "The value of x is:" ~x))

(defn my-function [x]
  (println "This is the function being called.")
  (println "The value of x is:" x))

(def x 5)

(defn func-a []
  (println "Inside func-a: " x)
  )

(defn func-b []
  (binding [x 10]
    (println "Inside func-a: " x)))

(defmacro when-example [test & body]
  `(if ~test
     (do ~@body)))


(defn -main []
  (when true))

(-main)
