(ns game-of-life.utils)

(def sum-of-vectors
  "Sums up n vectors in parallel.

  \t=> (sum-of-vectors [1 2] [3 4])
  \t   [4 6]\n
  \t=> (sum-of-vectors [1 2] [3 4] [5 6])
  \t   [9 12]"
  (partial mapv +))

(def not-nil? (complement nil?))

(def until-end-of (comp range count))
