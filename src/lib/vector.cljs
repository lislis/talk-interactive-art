(ns lib.vector
  (:require [quil.core :as q :include-macros true]))

(defn create [x y]
  {:x x :y y})

(defn add [vec1 vec2]
  (let [x (+ (:x vec1) (:x vec2))
        y (+ (:y vec1) (:y vec2))]
    (create x y)))

(defn mag [vec]
  (let [x (:x vec)
        y (:y vec)]
    (q/sqrt (+ (* x x) (* y y)))))

(defn mult [vec scalar]
  (let [x (* (:x vec) scalar)
        y (* (:y vec) scalar)]
    (create x y)))

(defn div [vec scalar]
  (let [x (/ (:x vec) scalar)
        y (/ (:y vec) scalar)]
    (create x y)))

(defn normalize [vec]
  (let [m (mag vec)]
    (if (not (= m 0))
      (div vec m)
      vec)))

(defn limit [vec limit]
  (if (< (mag vec) limit)
    vec
    (mult (normalize vec) limit)))
