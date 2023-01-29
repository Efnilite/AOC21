(ns day04
  (:require
    [clojure.string :as str]))

; part 1

(def lines (->> (slurp "resources/04.txt")
                (str/split-lines)
                (remove (fn [line] (= "" line)))))

(def nums (as-> lines l
                (first l)
                (str/split l #",")
                (map (fn [num] (Integer/parseInt num)) l)))

(def boards (->> lines
                 (rest)
                 (partition 5)
                 (map (fn [board]
                        (map
                          (fn [row]
                            (->> row
                                 (re-seq #"\d+")
                                 (map str/trim)
                                 (map #(Integer/parseInt %))))
                          board)))))

(def boards-data (map
                   (fn [board]
                     {:rows board
                      :cols (map-indexed
                              (fn [index row]
                                (map
                                  #(nth (nth board %) index)
                                  (range (count row)))) board)
                      })
                   boards))

(defn finished-boards [data]
  (->> data
       (filter
         (fn [board-data]
           (some #{0}
                 (->> board-data
                      (vals)
                      (map
                        (fn [board]
                          (map
                            (fn [row]
                              (count row))
                            board)))
                      (flatten)))))))

(defn remove-same-values [m k v]
  (map
    (fn [row]
      (remove (partial = v) row))
    (get m k)))

(defn filter-same-value [v data]
  (zipmap
    [:rows :cols]
    (map
      #(remove-same-values data % v)
      (keys data)))
  )

(loop [rem nums data boards-data previous-num (first nums)]
  (let [finished-boards (finished-boards data)]

    (if (not (empty? finished-boards))
      (*
        previous-num
        (->> finished-boards
             (first)
             (vals)
             (first)
             (flatten)
             (apply +)))

      (recur
        (rest rem)
        (map
          (partial filter-same-value (first rem))
          data)
        (first rem)))))

; part 2

(loop [rem nums data boards-data]
  (if (= 1 (count data))
    (*
      (first rem)
      (->> data
           (map (partial filter-same-value (first rem)))
           (first)
           (vals)
           (first)
           (flatten)
           (apply +)))

    (recur
      (rest rem)
      (->> data
           (map (partial filter-same-value (first rem)))
           (remove
             (fn [board]
               (->> board
                    (vals)
                    (map (partial some empty?))
                    (some true?))))))))