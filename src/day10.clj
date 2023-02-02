(ns day10
  (:require [clojure.string :as str]))

; part 1

(def points {\) 3 \] 57 \} 1197 \> 25137})

(defn tread [string]
  (let [seq (re-seq #"[\[\(\{\<][\>\}\)\]]" string)
        first-chunk (first seq)]
    [(if (empty? seq) nil (str/replace-first string first-chunk "")) first-chunk]))

(->> (slurp "resources/10.txt")
     (str/split-lines)
     (map (fn [line]
            (take-while (fn [[updated chunk]]
                          (and (some? updated)
                               (= (last chunk) (get {\( \) \[ \] \{ \} \< \>} (first chunk)))))
                        (iterate (comp tread first) [line "()"]))))
     (map (comp tread first last))
     (filter (partial every? some?))
     (map (comp (partial get points) last last))
     (apply +))