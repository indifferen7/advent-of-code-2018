(ns advent-of-code-2018.day3
  (:require [advent-of-code-2018.utils :as utils]
            [clojure.string :as string]))

(def input (utils/file-rows->seq "day3input"))

(defn to-int [c] (Character/digit c 10))

(defn parse-id [id]
  (->> (drop 1 id)
       (map #(Character/digit % 10))
       (string/join)
       (Integer/parseInt)))

(defn parse-pos [pos]
  (map #(Integer/parseInt %) (string/split pos #"[,:]")))

(defn parse-size [size]
  (map #(Integer/parseInt %) (string/split size #"x")))

(defn parse-row [row]
  (let [[id, _, pos, size] (string/split row #" ")]
    {:id (parse-id id)
     :pos (parse-pos pos)
     :size (parse-size size)}))

(defn covered-inches [row]
  (let [{[offset-x offset-y] :pos [w h] :size} (parse-row row)]
    (for [x (range 0 w)
          y (range 0 h)]
      [(+ offset-x x)
       (+ offset-y y)])))

(defn solve-first []
  (let [all-pos (mapcat covered-inches input)
        freq (frequencies all-pos)]
    (reduce-kv
     (fn [acc k v]
       (if (> v 1)
         (inc acc)
         acc))
     0
     freq)))

(defn solve-second []
  (let [all-pos (mapcat covered-inches input)
        freq (frequencies all-pos)
        non-overlap-inches (set (keys (filter #(= 1 (second %)) freq)))]
    (->> (filter
          (fn [row]
            (let [covered (set (covered-inches row))]
              (if (every? non-overlap-inches covered)
                true
                false)))
          input)
         (map parse-row))))
