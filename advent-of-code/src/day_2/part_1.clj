(ns day-2.part-1)

(def box-ids (clojure.string/split-lines (slurp "src/day_2/input.txt")))

(def letter-frequencies
  "list of maps containing {character number-of-occurrences}
   for each Box ID."
  (map frequencies box-ids))

(defn has-n-duplicate-items?
  "Returns true if a frequency map has at least 1 set of n duplicates"
  [n]
  (fn [letter-freq-map]
    (let [occurrences (-> (vals letter-freq-map) frequencies)]
      (>= (get occurrences n 0) 1))))

(def contains-a-pair
  (filter
    (has-n-duplicate-items? 2)
    letter-frequencies))

(def contains-a-triple
  (filter
    (has-n-duplicate-items? 3)
    letter-frequencies))

(def answer
  (* (count contains-a-pair) (count contains-a-triple)))

