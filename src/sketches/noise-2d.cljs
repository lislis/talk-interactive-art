(ns sketches.noise-2d
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  {:x-off 3})

(defn update-state [state]
  state)

(defn draw-state [state]
  )

(q/defsketch sketch
  :host "noise-2d"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
