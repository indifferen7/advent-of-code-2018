(ns advent-of-code-2018.day2
  (:require [advent-of-code-2018.utils :as utils]
            [clojure.set :as set]
            [clojure.string :as string]))

(def input (utils/file-rows->seq "day2input"))

(defn map-has-value [map val]
  (-> (vals map)
      (set)
      (contains? val)))

(defn inc-if-eq [val freqs]
  (if (map-has-value freqs val) inc identity))

(defn reducer-fn [acc row]
  (let [freqs (frequencies row)]
    {:twos ((inc-if-eq 2 freqs) (:twos acc))
     :threes ((inc-if-eq 3 freqs) (:threes acc))}))

(defn solve-first [input]
  (let [{twos :twos
         threes :threes} (reduce reducer-fn {:twos 0 :threes 0} input)]
    (* twos threes)))

(defn drop-nth [nth coll]
  (concat (take nth coll) (drop (inc nth) coll)))

(defn solve-second [input]
  (loop [i 0
         all (map #(drop-nth i %) input)]
    (let [code (get (set/map-invert (frequencies all)) 2)]
      (if (not (nil? code))
        (string/join code)
        (recur (inc i) (map #(drop-nth i %) input))))))