(ns day05
  (:require [clojure.string :as str]))

; part 1

(->> (slurp "resources/05.txt")
     (str/split-lines)
     (map (partial re-seq #"\d+"))
     (map (fn [coords] (map #(Integer/parseInt %) coords)))
     (filter (fn [x] (or (= (nth x 0) (nth x 2))
                         (= (nth x 1) (nth x 3)))))
     (map (fn [coords] (let [dx [(nth coords 0) (nth coords 2)]
                             dy [(nth coords 1) (nth coords 3)]
                             xs (range (apply min dx) (inc (apply max dx)))
                             ys (range (apply min dy) (inc (apply max dy)))
                             biggest (max (count xs) (count ys))]
                         (map vector
                              (take biggest (cycle xs))
                              (take biggest (cycle ys))))))
     (apply concat)
     (frequencies)
     (sort-by val)
     (filter #(< 1 (second %)))
     (count))

; part 2

(->> (slurp "resources/05.txt")
     (str/split-lines)
     (map (partial re-seq #"\d+"))
     (map (partial map #(Integer/parseInt %)))
     (filter (fn [x] (or (= (nth x 0) (nth x 2))
                         (= (nth x 1) (nth x 3))
                         (= (Math/abs (- (nth x 0) (nth x 2)))
                            (Math/abs (- (nth x 1) (nth x 3)))))))
     (map (fn [coords] (let [dx [(nth coords 0) (nth coords 2)]
                             dy [(nth coords 1) (nth coords 3)]
                             x-step (compare (last dx) (first dx))
                             y-step (compare (last dy) (first dy))
                             x-step (if (zero? x-step) 1 x-step)
                             y-step (if (zero? y-step) 1 y-step)
                             xs (range (first dx) (+ x-step (last dx)) x-step)
                             ys (range (first dy) (+ y-step (last dy)) y-step)
                             biggest (max (count xs) (count ys))]
                         (map vector
                              (take biggest (cycle xs))
                              (take biggest (cycle ys))))))
     (apply concat)
     (frequencies)
     (sort-by val)
     (filter #(< 1 (second %)))
     (count))

