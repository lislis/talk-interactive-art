(ns sketches.noise
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 5)
  {})

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 240)
  (q/ellipse 100 100 50 50))

(q/defsketch sketch
  :host "noise"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
