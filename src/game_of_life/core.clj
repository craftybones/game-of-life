(ns game-of-life.core
  (:require [game-of-life.utils :refer [sum-of-vectors
                                        setify]]))
 
(def neighbor-offsets
  "Assuming a center of [0 0] provides a set of neighboring offsets
  that can be translated easily into neighbors of any given co-ordinate"
  (into (hash-set) 
        (for [i (range -1 2)
              j (range -1 2)
              :when (not= [i j] [0 0])] 
          [i j])))

(defn neighboring
  "Provides the 8 neighboring co-ordinates of co-ord"
  [co-ord]
  (set (map (partial sum-of-vectors co-ord) neighbor-offsets)))

(defn number-of-live-neighbours
  "Given a set of positions of live cells, returns f(x)
  where x is a position of form [r c]. f(x) returns
  the number of live neighbors around x."
  [current-generation]
  (comp count (partial filter current-generation) neighboring))

(defn three-live-neighbours?
  "Given a set of positions of live cells returns f(x)
  where x is a position of form [r c]. f(x) returns
  true when there are exactly three live neighbors around x."
  [current-generation]
  (comp (partial = 3) (number-of-live-neighbours current-generation)))

(defn two-or-three-live-neighbours? [current-generation]
  (comp #(<= 2 % 3) (number-of-live-neighbours current-generation)))

(defn neighbors-that-may-come-alive [current-generation]
  (comp (mapcat neighboring)
        (filter (complement current-generation))
        (filter (three-live-neighbours? current-generation))))

(defn neighbors-that-stay-alive [current-generation]
  (filter (two-or-three-live-neighbours? current-generation)))

(defn next-generation [current-generation]
  (let [new (neighbors-that-may-come-alive current-generation)
        existing (neighbors-that-stay-alive current-generation)]
    (into #{}
          (concat (sequence new current-generation)
                  (sequence existing current-generation)))))
