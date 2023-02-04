(ns day11
  (:require [clojure.string :as str]))

(def energy-grid (->> (slurp "resources/11.txt")
                      (str/split-lines)
                      (map (comp (partial map #(Integer/parseInt %)) (partial re-seq #"\d")))
                      (map-indexed (fn [y row] (map-indexed (fn [x value] [[x y] value]) row)))
                      (apply concat)
                      (into {})))

(def width (->> (keys energy-grid) (map first) (apply max)))

(defn get-neighbours [x y]
  (->> [[(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]
        [(dec x) y] [(inc x) y]
        [(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]]
       (filter (partial every? #(and (<= 0 %) (>= width %))))))

(defn inc-energy [grid]
  (->> grid
       (map (partial (juxt first (comp inc second))))
       (into {})))