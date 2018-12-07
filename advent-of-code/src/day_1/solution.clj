(ns day-1.solution)

(def input
  (map #(Integer. %) (clojure.string/split-lines (slurp "src/day1/input.txt"))))

(defn solve-part1 [fluctuations] (reduce + fluctuations))

(defn solve-part2 [fluctuations]
  (reduce
    (fn [history x]
      (let [last-frequency (last history)
            resulting-frequency (+ last-frequency x)]

        (if (some #{resulting-frequency} history)
          (reduced resulting-frequency)
          (conj history resulting-frequency))))
    [0]
    (cycle fluctuations)))
