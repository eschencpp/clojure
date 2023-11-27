(ns server
  (:import [java.io BufferedReader InputStreamReader PrintWriter]
           [java.net ServerSocket])
  (:require [clojure.core.async :as async]))

;; Start new lightweight thread to handle client
(defn handle-client [client-socket]
  (async/go
    ;; Define input and output reader
    (let [in (BufferedReader. (InputStreamReader. (.getInputStream client-socket)))
          out (PrintWriter. (.getOutputStream client-socket))]
      (loop []
        (let [msg (.readLine in)]
          (when msg
            (println "Received message:" msg)
            (let [[operator & operands] (clojure.string/split msg #" ")
                  result (cond
                            (= operator "+") (apply + (map #(Integer. %) operands))
                            (= operator "-") (apply - (map #(Integer. %) operands))
                            (= operator "*") (apply * (map #(Integer. %) operands))
                            (= operator "/") (apply / (map #(Double. %) operands))
                            :else "Invalid operator. Please enter in format [+,-,*,or /] [num] [num]")]
              (println "Result:" result)
              (.println out (str result))
              (.flush out))
            (recur)))))))

(defn -main []
  ;; Start server socket
  (let [server-socket (ServerSocket. 8080)]
    (println "Server started, listening on port 8080")
    ;; Start new thread to listen for client connections
    (async/go
      (while true
        (let [client-socket (.accept server-socket)]
          (println "User connected")
          ;; Send client connection to be handled by other thread
          (handle-client client-socket)))))
  ;; Keep program running indefinitely
  (while true))

(-main)