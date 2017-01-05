(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest neighborhood
  (testing "neighbor offsets provide offsets assuming center at 0 0"
    (is (= #{[-1 -1] [-1 0] [-1 1]
             [0 -1]         [0 1]
             [1 -1]  [1 0]  [1 1]}
           neighbor-offsets)))
  (testing "neighboring co-ordinates are returned for a given co-ordinate"
    (is (= #{[0 0] [0 1] [0 2]
             [1 0]       [1 2]
             [2 0] [2 1] [2 2]}
           (neighboring [1 1])))
    (is (= #{[-3 -3] [-3 -2] [-3 -1]
            [-2 -3]         [-2 -1]
            [-1 -3] [-1 -2] [-1 -1]}
           (neighboring [-2 -2])))))
