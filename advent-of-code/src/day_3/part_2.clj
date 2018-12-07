(ns day-3.part-2
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

(defn overlapping? [s1 s2]
  (not (empty? (intersection s1 s2))))

(defn overlapped-squares
  "Returns the coordinates of any square inches that
  overlap between the two given claims"
  [claim-1 claim-2]
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

(time (def all-overlaps (find-overlaps claims)))
;; need to run find-overlaps first to get the total overlapped squares, then re-run through the collection until one doesn't overlap with any other.

(defn find-isolated-claim [all-occupied-squares list-of-claims]
  (reduce
    (fn [a-o-s c]
      (if-not (overlapping? a-o-s (area c))
        (reduced c)
        a-o-s))
  all-occupied-squares
  list-of-claims))

(def result (:id (find-isolated-claim
                   (:overlapping all-overlaps)
                   claims)))
