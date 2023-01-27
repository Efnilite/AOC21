(ns day02
  (:require [clojure.string :as str]))

; part 1

(def commands (->> (slurp "resources/02.txt")
                   (str/split-lines)))

(defn dpos [keyword]
  (->> commands
       (filter #(str/includes? % keyword))
       (map #(last (str/split % #" ")))
       (map #(Integer/parseInt %))
       (apply +)))

(* (dpos "forward")
   (- (dpos "down") (dpos "up")))

; part 2

(defn positions [current next]
  (let [parts (str/split next #" ")
        type (first parts)
        value (Integer/parseInt (last parts))]
    (map +
         current
         (case type "forward" [value (* value (last current)) 0]
                    "down" [0 0 value]
                    "up" [0 0 (- value)]))))

(let [final (reduce positions [0 0 0] commands)]
  (* (first final) (second final)))