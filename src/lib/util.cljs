(ns lib.util
  (:require [quil.core :as q :include-macros true]))

(defn norm-dist [mean dev]
  (+ (* dev (q/random-gaussian)) mean))

(defn montecarlo []
  (loop []
    (let [r1 (q/random 0 1)
          prob r1
          r2 (q/random 0 1)]
      (if (< r2 prob)
        r1
        (recur)))))

(defn custom-dist [mean dev]
  (+ (* dev (montecarlo)) mean))


(defn create-dot [x y]
  {:loc {:x x
         :y y}
   :vel {:x 0
           :y 0}
   :acc {:x 0
         :y 0}
   :x-off (q/random 0 100000)
   :y-off (q/random 0 100000)
   :radius (norm-dist 25 5)
   :hue (custom-dist 210 20)
   :sat (norm-dist 150 20)
   :bright 200})

(defn inc-noise [val]
  (+ 0.01 val))

(defn update-noise [dot]
  (let [x (:x-off dot)
        y (:y-off dot)]
    (assoc dot
           :x-off (inc-noise x)
           :y-off (inc-noise y))))

(defn update-all-noise [dots]
  (map update-noise dots))
