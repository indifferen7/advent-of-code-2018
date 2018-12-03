(ns advent-of-code-2018.utils
	(:require [clojure.java.io :as io]
			  [clojure.string :as string]))

(defn slurp-file [name]
	(-> (io/resource name)
		(slurp)))

(defn file-rows->seq [name]
	(-> (slurp-file name)
		(string/split-lines)))
