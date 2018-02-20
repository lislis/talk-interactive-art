(ns sketches.random
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 5)
  {})

(defn update-state [state])

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c (take 10 (range))]
     (let [w (q/random 0 (q/width))
           h (q/random 0 (q/height))
           radius (q/random 10 50)
           r (q/random 0 255)
           g (q/random 0 255)
           b (q/random 0 255)]
       (q/no-stroke)
       (q/fill r g b)
       (q/ellipse w h radius radius)))))

(q/defsketch sketch
  :host "random"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
