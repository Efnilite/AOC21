(ns day07)

(def positions (->> (slurp "resources/07.txt")
                    (re-seq #"\d+")
                    (map #(Integer/parseInt %))))

; part 1

(->> (range (apply min positions) (inc (apply max positions)))
     (map (fn [new-pos] (map #(Math/abs (- % new-pos)) positions)))
     (map (partial reduce +))
     (apply min))

; part 2

(->> (range (apply min positions) (inc (apply max positions)))
     (map (fn [new-pos]
            (map #(let [distance (Math/abs (- % new-pos))]
                    (/ (* distance (inc distance)) 2))      ; direct formula for sum
                 positions)))
     (map (partial reduce +))
     (apply min))