(ns day-2.part-2
  (:require [clojure.set :refer [intersection union difference]]
            [clojure.math.combinatorics :refer [combinations]]))

(def box-ids (clojure.string/split-lines (slurp "src/day_2/input.txt")))

(defn off-by-one?
  [[s1 s2]]

  (let [letter-comparisons (filter
                             char?
                             (map #(when (= %1 %2) %1) s1 s2))]

    (when (= 1 (- (count s1)
                  (count letter-comparisons)))
      (reduce str letter-comparisons))))

(def answer (keep
              off-by-one?
              (combinations box-ids 2)))
