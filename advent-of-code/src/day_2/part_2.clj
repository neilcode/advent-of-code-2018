(ns day-2.part-2
  (:require [clojure.set :refer [intersection union difference]]))

(def box-ids (clojure.string/split-lines (slurp "src/day_2/input.txt")))

(def letter-frequencies (map frequencies box-ids))

;; combine all unique letter frequencies between the 2 strings
;; find all the matching frequencies between the two strings
;; subtract the matches from the total uniques.
;; if you only have 2 uniques, each string is only off by one letter
;; if you have 0, the strings are matching
;; if you have more than 2, each string differs by more than 1 letter.
(defn off-by-one-letter
  [freq1 freq2]
  (let [all-letters (union
                      (into #{} freq1)
                      (into #{} freq2))
        shared-letters (intersection
                         (into #{} freq1)
                         (into #{} freq2))]
    (when (= 2
           (count
             (difference
               all-letters
               shared-letters)))
      (map first shared-letters))))
