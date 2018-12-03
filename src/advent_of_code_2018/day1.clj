(ns advent-of-code-2018.day1
  (:require [advent-of-code-2018.utils :as utils]
            [clojure.string :as string]))

(defn get-op [c] (resolve (symbol (str c))))

(defn tupelize [s]
  [(get-op (first s))
   (-> (drop 1 s)
       (string/join)
       (Integer/parseInt))])

(defn next-freq [freq [op num]] (op freq num))

(defn solve-first []
  (->> (utils/file-rows->seq "day1input")
       (map tupelize)
       (reduce next-freq 0)))

(defn solve-second []
  (let [input (cycle (utils/file-rows->seq "day1input"))]
    (loop [seen #{}
           counter 0
           freq 0]
      (let [next (next-freq freq (tupelize (first (drop counter input))))]
        (if (contains? seen next)
          next
          (recur (conj seen next) (inc counter) next))))))