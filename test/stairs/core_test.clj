(ns stairs.core-test
  (:require [clojure.test :refer :all]
            [stairs.core :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as ct :refer (defspec)]))

(def k-factor 30)
(def min-rating 800)
(def max-rating 2600)

(deftest new-rating-test
  (testing "calculates a player's new rating"
    (are [expected, old-rating score expected-score]
         (= expected (new-rating {:old-rating old-rating,
                                  :score score,
                                  :expected-score expected-score,
                                  :k-factor k-factor}))
         2115 2100 1 0.5
         2109 2100 1 0.69)))

(defspec rating-should-not-change-on-expected-outcomes 100
  (prop/for-all [rating (gen/choose min-rating max-rating)]
                (letfn [(rating-unchanged [score]
                          (= rating (new-rating {:old-rating rating,
                                                 :score score,
                                                 :expected-score score,
                                                 :k-factor k-factor})))]
                  (every? rating-unchanged [0 0.5 1]))))

(defspec rating-should-change-by-same-amount 100
  (prop/for-all [rating (gen/choose min-rating max-rating)]
                (let [reward (- (new-rating {:old-rating rating,
                                             :score 1,
                                             :expected-score 0,
                                             :k-factor k-factor}) rating)
                      penalty (- (new-rating {:old-rating rating,
                                              :score 0,
                                              :expected-score 1,
                                              :k-factor k-factor}) rating)]
                        (= reward (- penalty)))))

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

(defspec expected-scores-should-always-sum-to-one 100
  (prop/for-all [t (gen/tuple (gen/choose min-rating max-rating)
                              (gen/choose min-rating max-rating))]
                (let [rating-a (nth t 0)
                      rating-b (nth t 1)
                      score-a (expected-score {:player-rating rating-a,
                                               :opponent-rating rating-b,
                                               :k-factor k-factor})
                      score-b (expected-score {:player-rating rating-b,
                                               :opponent-rating rating-a,
                                               :k-factor k-factor})]

                  (= 1.0 (+ score-a score-b)))))
