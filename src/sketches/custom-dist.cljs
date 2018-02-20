(ns sketches.custom-dist
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

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

(defn setup []
  (q/frame-rate 5)
  {})

(defn update-state [state])

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c (take 10 (range))]
     (let [w (norm-dist (/ (q/width) 2) 70)
           h (norm-dist (/ (q/height) 2) 30)
           radius (norm-dist 25 5)
           hue (custom-dist 210 20)
           s (norm-dist 150 20)
           b 200]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill hue s b)
       (q/ellipse w h radius radius)))))

(q/defsketch sketch
  :host "custom-dist"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
