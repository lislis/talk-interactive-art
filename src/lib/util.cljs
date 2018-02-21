(ns lib.util
  (:require [quil.core :as q :include-macros true]
            [lib.vector :as v]))

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
  {:loc {:x x
         :y y}
   :vel {:x 0
           :y 0}
   :acc {:x 0
         :y 0}
   :x-off (q/random 0 100000)
   :y-off (q/random 0 100000)
   :radius (norm-dist 25 5)
   :hue (custom-dist 210 20)
   :sat (norm-dist 150 20)
   :bright (q/map-range (montecarlo) 0 1 50 255)})

(defn inc-noise [val]
  (+ 0.01 val))

(defn update-noise [dot]
  (let [x (:x-off dot)
        y (:y-off dot)]
    (assoc dot
           :x-off (inc-noise x)
           :y-off (inc-noise y))))

(defn update-all-noise [dots]
  (map update-noise dots))

(defn wrap-edges "takes location vector" [vec width height]
  (let [x (cond (> (:x vec) width) 0
                (< (:x vec) 0) width
                :else (:x vec))
        y (cond (> (:y vec) height) 0
                (< (:y vec) 0) height
                :else (:y vec))]
    (v/create x y)))

(defn apply-force [dot force]
  (let [acc (v/add (:acc dot) force)
        vel (v/add (:vel dot) acc)
        loc (v/add (:loc dot) vel)
        loc-wrapped (wrap-edges loc (q/width) (q/height))]
    (assoc dot :loc loc-wrapped)))

(defn apply-all-forces [dots force]
  (let [updated-dots (map #(apply-force % force) dots)]
    updated-dots))

(defn alter-wind [wind]
  (let [x (q/map-range (q/mouse-x) 0 (q/width) -2 2)
        y (q/map-range (q/mouse-y) 0 (q/height) -2 2)]
    (if (q/mouse-pressed?)
      {:x x :y y}
      wind)))

(defn angle-from-vec [vec]
  (let [delta-x (:x vec)
        delta-y (:y vec)
        rad (q/atan2 delta-y delta-x)]
    rad))

(defn radius-from-vec [vec]
  (let [mag (v/mag vec)]
    (q/map-range mag 0 3 200 400)))

(defn draw-indicator [vec]
  (q/fill 230 230 230 0.5)
  (q/stroke 180)
  (q/ellipse (/ (q/width) 2) (/ (q/height) 2) 130 130)
  (q/with-translation [350 250]
    (q/with-rotation
      [(angle-from-vec vec)]
      (let [radius (radius-from-vec vec)]
        (q/scale 0.1)
        (q/fill 180)
        (q/ellipse (- (q/width) 50) 20 radius radius)
        (q/pop-matrix)))))
