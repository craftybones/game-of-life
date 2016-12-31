(ns game-of-life.core
  (:require [game-of-life.utils :refer [sum-of-vectors not-nil? until-end-of]]))

;; (def sum-of-vectors (partial map +))

(def neighbor-offsets 
  (for [i (range -1 2) 
        j (range -1 2) 
        :when (or (not= 0 i) (not= 0 j))] 
    [i j]))

(defn neighbors-of [c] 
  (map (partial sum-of-vectors c) neighbor-offsets))

;; (def not-nil? (complement nil?))

(defn neighbors-of [grid co-ord] 
  (let [cell-at (partial get-in grid)]
    (filter not-nil? 
            (map cell-at (neighbors-of co-ord)))))

(def number-of-live (comp count (partial filter #{:live})))

(defn alive-or-dead [cell neighbors]
  (let [alive (number-of-live neighbors)]
    (cond
      (= 3 alive) :live
      (and (= 2 alive) (= cell :live)) :live
      :else :dead)))

;; (def until-end-of (comp range count))

(defn tick [grid]
  (vec (for [r (until-end-of grid)]
         (vec (for [c (until-end-of (first grid))
                    :let [cell (get-in grid [r c])]]
                (alive-or-dead cell (neighbors-of grid [r c])))))))
