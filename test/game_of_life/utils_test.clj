(ns game-of-life.utils-test
  (:require [clojure.test :refer :all]
            [game-of-life.utils :refer :all]))

(deftest summing-vectors
  (testing "Should sum up vectors in parallel"
    (is (= [1 1] (sum-of-vectors [0 0] [1 1])))
    (is (= [2 3 4] (sum-of-vectors [0 1 2] [2 2 2])))))

;; (deftest)

