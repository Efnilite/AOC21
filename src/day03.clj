(ns day03
  (:require [clojure.string :as str]))

; part 1

(def characters (->> (slurp "resources/03.txt")
                     (str/split-lines)
                     (map seq)))

(def frequencies* (->> characters
                       (first)
                       (count)
                       (range)
                       (map (fn [i] (map #(nth % i) characters)))
                       (map frequencies)
                       (map #(sort-by val > %))))

(defn binary-to-10 [binary-digits]
  (Integer/parseInt (str/join binary-digits) 2))

(def gamma (map ffirst frequencies*))
(def epsilon (map #(first (second %)) frequencies*))

(* (binary-to-10 gamma) (binary-to-10 epsilon))

; part 2 (doesn't work yet)

(defn sort-map-by-values [m]
  (into {} (sort-by second (map (fn [[k v]] [k v]) m))))

(defn frequencies' [rarity chars]
  (->> chars
       (first)
       (count)
       (range)
       (map (fn [i] (map #(nth % i) chars)))
       (map frequencies)
       (map sort-map-by-values)
       (map (if (zero? rarity) ffirst #(first (last %))))))

(defn rating [rarity]
  (loop [rem characters bit 0]
    (println (count rem) (frequencies' rarity rem) rem)
    (if (>= 1 (count rem))
      (flatten rem)
      (recur
        (filter #(= (nth (frequencies' rarity rem) bit) (nth % bit)) rem)
        (inc bit)))))

(* (binary-to-10 (rating 0)) (binary-to-10 (rating 1)))