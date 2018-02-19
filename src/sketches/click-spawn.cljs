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
      (js/console.log (q/mouse-x) (q/mouse-y))
      (conj state {:x (q/mouse-x) :y (q/mouse-y)}))
    state))

(defn draw-mouse []
  (q/color-mode :rgb)
  (q/fill 0)
  (q/ellipse (q/mouse-x) (q/mouse-y) 30 30))

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c state]
     (let [w (norm-dist (:x c) 10)
           h (norm-dist (:y c) 10)
           radius (norm-dist 25 5)
           hue (custom-dist 210 20)
           s (norm-dist 150 20)
           b 200]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill hue s b)
       (q/ellipse w h radius radius))))
  (draw-mouse))

(q/defsketch sketch
  :host "click-dist"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
