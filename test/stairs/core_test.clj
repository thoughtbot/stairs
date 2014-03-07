(ns stairs.core-test
  (:require [clojure.test :refer :all]
            [stairs.core :refer :all]))


(deftest new-rating-test
  (testing "calculates a player's new rating"
    (is (= 2115
           (new-rating {:old-rating 2100, :score 1, :expected-score 0.5, :k-factor 30})))
    (is (= 2109
           (new-rating {:old-rating 2100, :score 1, :expected-score 0.69, :k-factor 30})))))

(deftest expected-score-test
  (testing "calculates the expected score"
    (are [expected player-rating opponent-rating] 
         (= expected (expected-score {:player-rating player-rating
                                      :opponent-rating opponent-rating}))
         0.5  1400 1400
         0.53 1420 1400
         0.47 1400 1420
         0.56 1440 1400)))
