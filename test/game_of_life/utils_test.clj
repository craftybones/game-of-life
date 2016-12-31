(ns game-of-life.utils-test
  (:require [clojure.test :refer :all]
            [game-of-life.utils :refer :all]))

(deftest summing-vectors
  (testing "Should sum up vectors in parallel"
    (is (= [1 1] (sum-of-vectors [0 0] [1 1])))
    (is (= [2 3 4] (sum-of-vectors [0 1 2] [2 2 2])))
    (is (= [9 12] (sum-of-vectors [1 2] [3 4] [5 6])))
    (is (= [] (sum-of-vectors [])))))

(deftest aint-no-nil
  (testing "Anything that isn't nil should return true and vice versa"
    (is (not-nil? 0))
    (is (not-nil? "a"))
    (is (not-nil? []))
    (is (= false (not-nil? nil)))
    (is (= false (not-nil? (first '()))))))

(deftest range-across-a-collection
  (testing "Provides a range that ranges from 0 to the length of a collection"
    (is (= '(0 1 2) (until-end-of [1 2 3])))
    (is (= '(0) (until-end-of [0])))
    (is (= '() (until-end-of [])))))

