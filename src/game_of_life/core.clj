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

(defn two-or-three-live-neighbours?
  "Given a set of positions of live cells returns f(x)
  where x is a position of form [r c]. f(x) returns
  true when there are either two or three live neighbors
  around x."
  [current-generation]
  (comp #(<= 2 % 3) (number-of-live-neighbours current-generation)))

(defn neighbors-that-may-come-alive
  "Given a set of positions of live cells returns a transducer.
  The transducer will filter co-ordinates with dead neighbours
  that have exactly three living neighbours around them.

  Effectively this forms the set of dead neighbours that will
  come alive in the next generation."
  [current-generation]
  (comp (mapcat neighboring)
        (filter (complement current-generation))
        (filter (three-live-neighbours? current-generation))))

(defn cells-that-stay-alive
  "Given a set of positions of live cells returns a transducer.
  The transducer will filter co-ordinates of cells that
  have either two or three neighbouring cells that are alive.

  Effectively this forms the set of cells that continue to
  live in the next generation."
  [current-generation]
  (filter (two-or-three-live-neighbours? current-generation)))

(defn next-generation
  "Given a set of positions of live cells returns the next
  generation of live cells as a set of positions."
  [current-generation]
  (let [new (neighbors-that-may-come-alive current-generation)
        existing (cells-that-stay-alive current-generation)]
    (into #{}
          (concat (sequence new current-generation)
                  (sequence existing current-generation)))))
