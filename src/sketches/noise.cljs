(ns sketches.noise
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  {})

(defn update-state [state])

(defn draw-state [state]
  (q/background 240)
  )

(q/defsketch sketch
  :host "canvas-noise"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
