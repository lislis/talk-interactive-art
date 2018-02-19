(ns sketches.vector
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [lib.util :as u]))

(defn setup []
  {:dots (vec (for [i (range 10)]
          (u/create-dot (q/random 50 (- (q/width) 50))
                        (q/random 50 200))))
   :wind {:x 0.1
          :y 0.1}})

(defn create
  "creates a vector"
  [x y]
  {:x x :y y})

(defn add
  "adds two vectors together"
  [vec1 vec2]
  (let [x (+ (:x vec1) (:x vec2))
        y (+ (:y vec1) (:y vec2))]
    (create x y)))

(defn apply-force [dot force]
  (let [acc (add (:acc dot) force)
        vel (add (:vel dot) acc)
        loc (add (:loc dot) vel)]
    (assoc dot :loc loc)))

(defn apply-all-forces [state]
  (let [dots (:dots state)
        wind (:wind state)
        updated-dots(map #(apply-force % wind) dots)]
    (assoc state :dots updated-dots)))

(defn update-state [state]
  (let [a (apply-all-forces state)
        dots (:dots a)
        n (u/update-all-noise dots)
        ]
    (assoc state :dots n)))

(defn draw-state [state]
  (q/background 240)
  (dorun
   (for [c (:dots state)]
     (let [loc (:loc c)
           x (q/map-range (q/noise (:x-off c)) 0 1 (- (:x loc) 100) (+ (:x loc) 100))
           y (q/map-range (q/noise (:y-off c)) 0 1 (- (:y loc) 100) (+ (:y loc) 100))
           radius (:radius c)
           hue (:hue c)
           s (:sat c)
           b (:bright c)]
       (q/color-mode :hsb)
       (q/no-stroke)
       (q/fill hue s b)
       (q/ellipse x y radius radius)))))

(q/defsketch sketch
  :host "vector"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:no-start]
  :middleware [m/fun-mode])
