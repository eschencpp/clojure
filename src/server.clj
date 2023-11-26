(ns server
  (:import [java.io BufferedReader InputStreamReader PrintWriter]
           [java.net ServerSocket])
  (:require [clojure.core.async :as async]))

(defn handle-client [client-socket]
  (async/go
    (let [in (BufferedReader. (InputStreamReader. (.getInputStream client-socket)))
          out (PrintWriter. (.getOutputStream client-socket))]
      (loop []
        (let [msg (.readLine in)]
          (when msg
            (println "Received message:" msg)
            (println "Sending back acknowledgment")
            (.println out "Message received")
            (.flush out)
            (recur)))))))

(defn -main []
  (let [server-socket (ServerSocket. 8080)]
    (println "Server started, listening on port 8080")
    (async/go
      (while true
        (let [client-socket (.accept server-socket)]
          (println "User connected")
          (handle-client client-socket)))))
  (while true))

(-main)