(ns advent-of-code-2018.day2
  (:require [advent-of-code-2018.utils :as utils]))

(def input (utils/file-rows->seq "day2input"))

(defn map-has-value [map val]
  (-> (vals map)
      (set)
      (contains? val)))

(defn twos-fn [freqs]
  (if (map-has-value freqs 2) inc identity))

(defn threes-fn [freqs]
  (if (map-has-value freqs 3) inc identity))

(defn reducer-for-first [acc row]
  (let [freqs (frequencies row)]
    {:twos ((twos-fn freqs) (:twos acc))
     :threes ((threes-fn freqs) (:threes acc))}))

(defn solve-first []
  (let [{twos :twos
         threes :threes} (reduce
                          reducer-for-first
                          {:twos 0
                           :threes 0}
                          input)]
    (* twos threes)))

(defn drop-nth [nth coll]
  (concat (take nth coll) (drop (inc nth) coll)))

(defn solve-second []
  (loop [i 0
         all (map #(drop-nth i %) input)]
    (let [code (get (clojure.set/map-invert (frequencies all)) 2)]
      (if (not (nil? code))
        (clojure.string/join code)
        (recur (inc i) (map #(drop-nth i %) input))))))