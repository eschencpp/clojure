(ns client)

;; Define server IP address and port
(def IP-address "localhost")
(def port 8080)

;; Send a message to the server and get output
(defn send-command [socket command]
  (let [out (java.io.DataOutputStream. (.getOutputStream socket))
        in  (java.io.BufferedReader. (java.io.InputStreamReader. (.getInputStream socket)))]

    ;; Send message to server
    (.writeBytes out (str command "\n"))
    (.flush out)

    ;; Read the response from the server
    (let [response (.readLine in)]
      (println "From server:" response)

      ;; If message is "exit" close the socket connection
      (when (= "exit" command)
        (println "Closing socket connection.")
        (.close socket)))))

(defn -main []
  ;; Create a socket connection to the server
  (let [socket (java.net.Socket. IP-address port)]
    (println "Connected to: IP: " IP-address " port: " port)
    ;; Enter a loop to continuously read user input and send commands to the server
    (while true
      (print "Enter message: ")
      (.flush *out*) ;; Explicitly flush the output stream to show the prompt
      (let [user-input (clojure.core/read-line)]
        (when user-input
          (send-command socket user-input))))))

(-main)