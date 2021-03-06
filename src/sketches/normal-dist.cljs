(ns sketches.normal-dist
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn norm-dist [mean dev]
  (+ (* dev (q/random-gaussian)) mean))

(defn setup []
  (q/frame-rate 5))

(defn update-state [state])

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c (take 10 (range))]
     (let [x (norm-dist (/ (q/width) 2) 70)
           y (norm-dist (/ (q/height) 2) 30)
           radius (norm-dist 25 5)
           h (norm-dist 210 12)
           s (norm-dist 150 20)
           b 200]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill h s b)
       (q/ellipse x y radius radius)))))

(q/defsketch sketch
  :host "normal-dist"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
