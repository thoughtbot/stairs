(ns stairs.core
  (:require [clojure.math.numeric-tower :as math]))

(defn new-rating [{:keys [old-rating score expected-score k-factor]
                   :or {k-factor 30}}]
  "Calculates a player's new rating"
  (let [score-difference (- score expected-score)]
    (-> score-difference
        (* k-factor)
        (+ old-rating)
        int)))

(defn- round-places [decimal-places number]
  "Rounds a number to a given number of decimal places"
  (let [factor (math/expt 10 decimal-places)]
    (double (/ (math/round (* factor number)) factor))))

(defn expected-score [{:keys [player-rating opponent-rating]}]
  (let [rating-difference (- player-rating opponent-rating)]
    (round-places 2 (double (/ 1 (inc (math/expt 10 (- (/ rating-difference 400)))))))))
