(ns advent-of-code-2018.day4
  (:require [advent-of-code-2018.utils :as utils]
            [clojure.string :as string]))

(def input (utils/file-rows->seq "day4input"))

(defn comparator [a b]
  (compare (subs a 1 17) (subs b 1 17)))

(defn guard-id [row]
  (string/replace (get row 3) #"#" ""))

(defn minute [row]
  (-> (get row 1)
      (string/split #":")
      (second)
      (Integer/parseInt)))

(defn parse-row [row]
  (-> (string/replace row #"[\[\]]" "")
      (string/split #" ")))

(defn add-minutes [sleep-minute wakeup-minute minutes]
  (concat minutes (range sleep-minute wakeup-minute)))

(defn minutes-by-sleeping-guard []
  (loop [guard nil
         sleep-minute 0
         rows (sort comparator input)
         result {}]
    (if (empty? rows)
      result
      (let [head (parse-row (first rows))
            rest (rest rows)]
        (cond
          (= "Guard" (get head 2)) (recur (guard-id head) sleep-minute rest result)
          (= "falls" (get head 2)) (recur guard (minute head) rest result)
          (= "wakes" (get head 2)) (recur guard sleep-minute rest
                                          (update result guard (partial add-minutes sleep-minute (minute head))))))))) ;amagerd this code sucks :D

(defn top-sleeper [minutes-by-guard]
  (reduce-kv
   (fn [m k v]
     (if (< (count (:mins m)) (count v))
       (assoc m :guard k :mins v)
       m))
   {:guard nil :mins []}
   minutes-by-guard))

(defn solve-first []
  (let [winner (top-sleeper (minutes-by-sleeping-guard))
        freqs (frequencies (:mins winner))
        [minute] (first (sort-by val > freqs))]
    (* (Integer/parseInt (:guard winner)) minute)))

(defn minute-sleeper [minutes-by-guard]
  (reduce-kv
   (fn [m k v]
     (let [freqs (frequencies v)
           [min times] (apply max-key val freqs)]
       (if (< (:times m) times)
         (assoc m :guard k :min min :times times)
         m)))
   {:guard nil :min 0 :times 0}
   minutes-by-guard))

(defn solve-second []
  (let [winner (minute-sleeper (minutes-by-sleeping-guard))]
    (* (Integer/parseInt (:guard winner)) (:min winner))))