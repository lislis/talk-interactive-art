(ns sketches.click-spawn
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
  [{:x (/ (q/width) 2)
    :y (/ (q/height) 2)}])

(defn update-state [state]
  (if (q/mouse-pressed?)
    (do
      (conj state {:x (q/mouse-x) :y (q/mouse-y)}))
    state))

(defn draw-mouse []
  (q/color-mode :rgb)
  (q/stroke 100)
  (q/fill 100 0.5)
  (q/ellipse (q/mouse-x) (q/mouse-y) 30 30))

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c state]
     (let [x (norm-dist (:x c) 10)
           y (norm-dist (:y c) 10)
           radius (norm-dist 25 5)
           h (custom-dist 210 20)
           s (norm-dist 150 20)
           b 200]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill h s b)
       (q/ellipse x y radius radius))))
  (draw-mouse))

(q/defsketch sketch
  :host "click-dist"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
