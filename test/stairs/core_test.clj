(ns stairs.core-test
  (:require [clojure.test :refer :all]
            [stairs.core :refer :all]))


(deftest new-rating-test
  (testing "calculates a player's new rating"
    (are [expected, old-rating score expected-score]
         (= expected (new-rating {:old-rating old-rating,
                                  :score score,
                                  :expected-score expected-score,
                                  :k-factor k-factor}))
         2115 2100 1 0.5
         2109 2100 1 0.69)))

(deftest expected-score-test
  (testing "calculates the expected score"
    (are [expected player-rating opponent-rating]
         (= expected (expected-score {:player-rating player-rating,
                                      :opponent-rating opponent-rating,
                                      :k-factor k-factor}))
         0.5  1400 1400
         0.53 1420 1400
         0.47 1400 1420
         0.56 1440 1400)))
