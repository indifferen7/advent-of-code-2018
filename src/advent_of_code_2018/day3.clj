(ns advent-of-code-2018.day3
  (:require [advent-of-code-2018.utils :as utils]
            [clojure.string :as string]))

(def input (utils/file-rows->seq "day3input"))

(defn parse-pos [pos] (map #(Integer/parseInt %) (string/split pos #"[,:]")))
(defn parse-size [size] (map #(Integer/parseInt %) (string/split size #"x")))

(defn parse-id [id]
  (->> (drop 1 id)
       (map #(Character/digit % 10))
       (string/join)
       (Integer/parseInt)))

(defn parse-row [row]
  (let [[id, _, pos, size] (string/split row #" ")]
    {:id (parse-id id)
     :pos (parse-pos pos)
     :size (parse-size size)}))

(defn covered-inches [row]
  (let [{[offset-x offset-y] :pos [w h] :size} (parse-row row)]
    (for [x (range 0 w)
          y (range 0 h)]
      [(+ offset-x x) (+ offset-y y)])))

(defn non-overlap-inches [input]
  (->> (mapcat covered-inches input)
       (frequencies)
       (filter #(= 1 (second %)))
       (keys)
       (set)))

;solution first part
(defn solve-first [input]
  (->> (mapcat covered-inches input)
       (frequencies)
       (reduce-kv
        (fn [acc k v]
		  (if (> v 1) (inc acc) acc)) 0)))

;solution second part
(defn solve-second [input]
  (let [not-overlapping (non-overlap-inches input)]
    (->> (filter #(every? not-overlapping (covered-inches %)) input)
         (map parse-row))))
