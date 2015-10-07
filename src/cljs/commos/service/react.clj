(ns commos.service.react)

(defmacro with-requests
  [bindings & body]
  {:pre [(map? bindings) (every? symbol? (keys bindings))]}
  (let [syms (vec (keys bindings))
        bindings (zipmap (map (fn [s]
                                `(quote ~s))
                              (keys bindings))
                         (vals bindings))]
    `(wrap-services
      ~bindings
      (fn [{:syms ~syms}]
        ~@body))))
