(ns leiningen.new.splat
  (:require [clojure.java.io :as io]
            [leiningen.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "splat"))

(defn splat
  "Create a new CLJS Single Page Application"
  [name]

  (println "Creating a new CLJS Single Page Application...")

  (let [data {:name name
              :sanitized (name-to-path name)}]
    (->files data
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["secrets.edn" (pr-str {})]
             "resources"

             ["bin/build.sh" (render "build.sh" data) :executable true]
             ["bin/build-base.sh" (render "build-base.sh" data) :executable true]

             ["Dockerfile" (render "Dockerfile" data)]
             ["Dockerfile.base" (render "Dockerfile.base" data)]
             [".dockerignore" (render "dockerignore" data)]

             ["src/clj/{{sanitized}}/user.clj" (render "user.clj" data)]
             ["src/clj/{{sanitized}}/api/main.clj" (render "main.clj" data)]
             ["src/clj/{{sanitized}}/api/figwheel.clj" (render "figwheel.clj" data)]
             ["src/clj/{{sanitized}}/api/page.clj" (render "page.clj" data)]
             ["src/clj/{{sanitized}}/api/server.clj" (render "server.clj" data)]

             ["src/cljc/{{sanitized}}/routes.cljc" (render "routes.cljc" data)]

             ["src/cljs/{{sanitized}}/ui/main.cljs" (render "main.cljs" data)]

             ["dev-resources/public/static/less/main.less" (render "main.less" data)]))

  (println "Created!")
  (println "To start the application, run `lein dev-api` and `lein dev-ui` (in separate terminals), and then go to http://localhost:3000"))
