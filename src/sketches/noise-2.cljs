(ns sketches.noise-2
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

(defn create-dot [x y]
  {:x x
   :y y
   :x-off (q/random 0 100000)
   :y-off (q/random 0 100000)
   :radius (norm-dist 25 5)
   :hue (custom-dist 210 20)
   :sat (norm-dist 150 20)
   :bright (q/map-range (montecarlo) 0 1 50 200)})

(defn setup []
  [(create-dot (/ (q/width) 2) (/ (q/height) 2))])

(defn add-dot [state]
  (conj state (create-dot (q/mouse-x) (q/mouse-y))))

(defn inc-noise [val]
  (+ 0.01 val))

(defn update-noise [dot]
  (let [x (:x-off dot)
        y (:y-off dot)]
    (assoc dot
           :x-off (inc-noise x)
           :y-off (inc-noise y))))

(defn update-all-noise [state]
  (map update-noise state))

(defn update-state [state]
  (let [s (update-all-noise state)]
    (if (and (q/mouse-pressed?) (= (q/mouse-button) :left))
      (add-dot s)
      s)
    ))

(defn draw-mouse []
  (q/color-mode :rgb)
  (q/stroke 100)
  (q/fill 100 0.5)
  (q/ellipse (q/mouse-x) (q/mouse-y) 30 30))

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c state]
     (let [x (q/map-range (q/noise (:x-off c)) 0 1 (- (:x c) 100) (+ (:x c) 100))
           y (q/map-range (q/noise (:y-off c)) 0 1 (- (:y c) 100) (+ (:y c) 100))
           radius (:radius c)
           hue (:hue c)
           s (:sat c)
           b (:bright c)]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill hue s b)
       (q/ellipse x y radius radius))))
  (draw-mouse))

(q/defsketch sketch
  :host "noise-2"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
