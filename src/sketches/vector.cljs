(ns sketches.vector
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [lib.util :as u]))

(defn setup []
  {:dots (vec (for [i (range 100)]
          (u/create-dot (q/random 50 (- (q/width) 50))
                        (q/random 50 500))))
   :wind {:x 0.1
          :y 0.1}})


(defn update-state [state]
  (let [dots (:dots state)
        wind (u/alter-wind (:wind state))
        a (u/apply-all-forces dots wind)
        n (u/update-all-noise a)]
    (assoc state :dots n :wind wind)))

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c (:dots state)]
     (let [loc (:loc c)
           x-noise (q/noise (:x-off c))
           y-noise (q/noise (:y-off c))
           x (q/map-range x-noise 0 1 (- (:x loc) 100) (+ (:x loc) 100))
           y (q/map-range y-noise 0 1 (- (:y loc) 100) (+ (:y loc) 100))
           radius (:radius c)
           hue (:hue c)
           s (:sat c)
           b (:bright c)]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill hue s b)
       (q/ellipse x y radius radius))))

  (if (q/mouse-pressed?)
    (u/draw-indicator (:wind state))))

(q/defsketch sketch
  :host "vector"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
