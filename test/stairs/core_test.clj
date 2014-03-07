(ns stairs.core-test
  (:require [clojure.test :refer :all]
            [stairs.core :refer :all]))


(deftest new-rating-test
  (testing "calculates a player's new rating"
    (is (= 2115
           (new-rating {:old-rating 2100 :score 1 :expected-score 0.5 :k-factor 30})))
    (is (= 2109
           (new-rating {:old-rating 2100 :score 1 :expected-score 0.69 :k-factor 30})))))
