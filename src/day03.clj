(ns day03
  (:require [clojure.string :as str]))

; part 1

(def characters (->> (slurp "resources/03.txt")
                     (str/split-lines)
                     (map seq)))

(defn frequencies' [characters]
  (->> characters
       (first)
       (count)
       (range)
       (map (fn [i] (map #(nth % i) characters)))
       (map frequencies)
       (map (partial sort-by val >))))

(defn binary-to-10 [binary-digits]
  (Integer/parseInt (str/join binary-digits) 2))

(def gamma (map ffirst (frequencies' characters)))
(def epsilon (map #(first (second %)) (frequencies' characters)))

(* (binary-to-10 gamma) (binary-to-10 epsilon))

; part 2

(defn frequencies'' [characters preferred-digit]
  (->> characters
       (first)
       (count)
       (range)
       (map (fn [i] (map #(nth % i) characters)))
       (map frequencies)
       (map
         #(sort
            (fn [x y]
              (if (= (last x) (last y)) preferred-digit (compare (last y) (last x))))
            %))))

(defn walk [preferred-digit]
  (loop [rem characters i 0]
    (if (= 1 (count rem))
      (first rem)
      (let [freqs (nth (frequencies'' rem (if (zero? preferred-digit) 1 -1)) i)
            common-bit (first (nth freqs preferred-digit))]
        (recur
          (filter #(= common-bit (nth % i)) rem)
          (inc i)))
      )))

(* (binary-to-10 (walk 0)) (binary-to-10 (walk 1)))