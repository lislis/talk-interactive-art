(ns sketches.noise
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  {:x-off 0})

(defn update-noise [val]
  (+ 0.01 val))

(defn update-state [state]
  (let [x-off (update-noise (:x-off state))]
    (assoc state :x-off x-off)))

(defn draw-state [state]
  (q/background 240)
  (let [x-noise (q/noise (:x-off state))
        x (q/map-range x-noise 0 1 0 (q/width))]
    (q/fill 0)
    (q/ellipse x (/ (q/height) 2) 20 20)))

(q/defsketch sketch
  :host "canvas-noise"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
