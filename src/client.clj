(ns client
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async]))

(def IP-address "localhost")
(def port 8080)

(defn send-command [socket command]
  (let [out (java.io.DataOutputStream. (.getOutputStream socket))
        in  (java.io.BufferedReader. (java.io.InputStreamReader. (.getInputStream socket)))]
    (println "Input:" command)
    (.writeBytes out (str command "\n"))
    (.flush out)
    (let [response (.readLine in)]
      (println "Output:" response))))

(defn -main []
  (let [socket (java.net.Socket. IP-address port)]
    (while true
      (let [user-input (clojure.core/read-line)]
        (when user-input
          (send-command socket user-input))))))

(-main)