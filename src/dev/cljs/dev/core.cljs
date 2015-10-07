(ns dev.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljsjs.react]
            [cljs.core.async :refer [chan put! take! <! >! close!]]
            [commos.delta.local-store :as ls]
            [commos.service :as service]
            [commos.delta :as delta]
            [commos.service.react :refer-macros [with-requests]]
            [minreact.core :as m :refer-macros [defreact]]))

(defn adder
  [{:keys [counter] :as state} amount]
  [:is [:counter] (+ counter amount)])

(defn div [& content]
  (apply js/React.createElement "div" nil
         content))

(defn button [title on-click]
  (apply js/React.createElement "button" #js{:onClick on-click}
         title))

(defreact app [{:keys [store]}]
  (fn render []
    (with-requests {counter [store :count {:xf delta/sums}]}
      (div (pr-str counter)
        "    "
        (button "go"
          (fn [_]
            (service/request store [:op :add 10] (chan))))))))

(enable-console-print!)

(defn dbg-service [s]
  (let [ident (gensym "service")
        ctr (atom 0)
        cid (memoize (fn [ch]
                       (swap! ctr inc)))]
    (reify
      service/IService
      (request [_ spec ch]
        (println ident "requesting" spec (cid ch))
        (service/request s spec ch))
      (cancel [_ ch]
        (println ident "cancelling" (cid ch))
        (service/cancel s ch)))))

(defonce store (dbg-service
                (ls/local-store :k->op {:add adder}
                                :alias->spec {:count [:read [:counter]]})))

(comment
  (service/request store [:op :add 200] (chan)))

(defn main []
  (js/React.render (app {:store store})
                   (js/document.getElementById "app")))

(main)
