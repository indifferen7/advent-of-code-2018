(ns advent-of-code-2018.day5
  (:require [advent-of-code-2018.utils :as utils]
            [clojure.string :as string]))

(def input (seq (utils/slurp-file "day5input")))

(defn destructable [a b]
  (and
   (not= a b)
   (or
    (= a (Character/toUpperCase b))
    (= a (Character/toLowerCase b)))))

(defn destroy-some [s]
  (loop [string s
         result []]
    (let [[first second] string]
      (cond
        (nil? first) result
        (nil? second) (recur (rest string) (conj result first))
        :else (if (destructable first second)
                (recur (drop 2 string) result)
                (recur (drop 1 string) (conj result first)))))))

(defn solve-first []
  (loop [string input]
    (let [result (destroy-some string)]
      (if (= string result)
        (count result)
        (recur result)))))