(ns red-elvis.predator-free
  (:gen-class)
   (:require [org.httpkit.server :as app-server]
            [compojure.core :refer [defroutes GET]]
            [ring.util.response :refer [response]]
            [red-elvis.handlers :as handler]))

(defonce app-server-instance (atom nil))

(defroutes app
  (GET "/" [] handler/traps-overview-page))

(defn app-server-start
  "Start the application server and log the time of start."

  [http-port]
  (println (str (java.util.Date.)
                " INFO: Starting server on port: " http-port))
  (reset! app-server-instance
          (app-server/run-server #'app {:port http-port})))

(defn app-server-stop
  "Gracefully shutdown the server, waiting 100ms.  Log the time of shutdown"
  []
  (when-not (nil? @app-server-instance)
    (@app-server-instance :timeout 100)
    (reset! app-server-instance nil)
    (println (str (java.util.Date.)
                  " INFO: Application server shutting down..."))))

(defn app-server-restart
  "Convenience function to stop and start the application server"

  [http-port]
  (app-server-stop)
  (app-server-start http-port))

(defn -main
 "Select a value for the http port the app-server will listen to
  and call app-server-start

  The http port is either an argument passed to the function,
  an operating system environment variable or a default value."

  [& [http-port]]
  (let [http-port (Integer. (or http-port (System/getenv "PORT") "8888"))]
    (app-server-start http-port))
  )


(comment

  ;; Start application server - via `-main` or `app-server-start`
  (-main)
  (app-server-start 8888)

  ;; Stop / restart application server
  (app-server-stop)
  (app-server-restart 8888)

  ;; Get PORT environment variable from Operating System
  (System/getenv "PORT")

  ;; Get all environment variables
  ;; use a data inspector to view environment-variables name
  (def environment-variables
    (System/getenv))

  ;; Check values set in the default system properties
  (def system-properties
    (System/getProperties))

  )
