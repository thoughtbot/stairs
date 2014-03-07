(ns stairs.core)

(defn new-rating [{:keys [old-rating score expected-score k-factor]
                   :or {k-factor 30}}]

  "Calculates a player's new rating"
  (let [score-difference (- score expected-score)]
    (-> score-difference
        (* k-factor)
        (+ old-rating)
        int)))
