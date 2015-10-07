(defproject org.commos/service.react "0.1.0"
  :description "Use commos services in React components"
  :url "http://github.com/commos/service.react"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 
                 [org.commos/service "0.2.0"]
                 [minreact "0.1.0"]]
  :source-paths ["src/cljs"]
  :profiles {:dev {:dependencies [[org.clojure/clojurescript "1.7.48"]
                                  [org.commos/delta.local-store
                                   "0.1.0-SNAPSHOT"]]
                   :plugins [[lein-cljsbuild "1.0.6"]
                             [lein-figwheel "0.3.5"]]
                   
                   :figwheel {:nrepl-port 7888}
                   
                   :cljsbuild
                   {:builds [{:id "dev"
                              :source-paths ["src/cljs"
                                             "src/dev/cljs"]
                              :figwheel true
                              :compiler {:main dev.core}}]}}})
