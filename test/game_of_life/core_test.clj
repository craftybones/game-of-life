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
           (neighboring [-2 -2]))))
  (testing "number of live neighbors based on current generation"
    (let [live-cells #{[1 1] [1 2] [1 3]}
          num-of-live-around (number-of-live-neighbours live-cells)]
      (are [x y] (= x y)
        1 (num-of-live-around [0 0])
        2 (num-of-live-around [0 1])
        3 (num-of-live-around [0 2])
        1 (num-of-live-around [1 0])
        1 (num-of-live-around [1 1])
        2 (num-of-live-around [1 2])
        1 (num-of-live-around [1 3])
        1 (num-of-live-around [1 4])
        1 (num-of-live-around [2 0])
        2 (num-of-live-around [2 1])
        3 (num-of-live-around [2 2])
        0 (num-of-live-around [0 5]))))
  (testing "exactly three live neighbors on a single line"
    (let [live-cells #{[1 1] [1 2] [1 3]}
          three-live-around? (three-live-neighbours? live-cells)]
      (are [x] (true? x)
        (three-live-around? [0 2])
        (three-live-around? [2 2]))
      (are [x] (false? x)
        (three-live-around? [0 0])
        (three-live-around? [0 1])
        (three-live-around? [0 3])
        (three-live-around? [0 4])
        (three-live-around? [1 0])
        (three-live-around? [1 1])
        (three-live-around? [1 2])
        (three-live-around? [1 3])))))
