(ns day-3.part-1
  (:require [clojure.string :as s]
            [clojure.math.combinatorics :refer [combinations]]
            [clojure.set :refer [intersection union]]))

(def matcher #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)")

(defn claim [s]
  (let [[_ id x-offset y-offset width height] (re-find matcher s)]
    {:id (Integer. id)
     :offset [(Integer. x-offset) (Integer. y-offset)]
     :size [(Integer. width) (Integer. height)]}))

(def claims
  (map claim (s/split-lines (slurp "src/day_3/input.txt"))))

(defn area [{[width height] :size [offset-x offset-y] :offset}]
  (into #{}
        (for [x (range width)
              y (range height)]
          [(+ offset-x x) (+ offset-y y)])))

(defn overlapped-squares
  "Returns the coordinates of any square inches that
  overlap between the two given claims"
  [s1 s2]
  (intersection (area claim-1) (area claim-2)))



(defn find-overlaps
  [[first-claim second-claim & other-claims]]
  (reduce
    (fn [{:keys [overlapping claims-examined] :as m} next-claim]
      (let [new-overlaps (keep
                           #(not-empty (overlapped-squares next-claim %))
                           claims-examined)]

        {:claims-examined (conj claims-examined next-claim)
         :overlapping (apply union overlapping new-overlaps)}))

    {:claims-examined [first-claim second-claim]
     :overlapping (overlapped-squares
                    first-claim
                    second-claim)}
    other-claims))

(def result (find-overlaps claims)) ;; just a cool 6 minute runtime
(def answer (count (:overlapping result)))

