(ns interactive-art.core
  (:require [sketches.random :as random]
            [sketches.normal-dist :as norm]
            [sketches.custom-dist :as custom]
            [sketches.click-spawn :as click]
            [sketches.noise :as noise]
            [sketches.noise-2 :as n2]
            [sketches.vector :as vec]
            ))

(defn ^:export run-random [] (random/sketch))

(defn ^:export run-normal-dist [] (norm/sketch))

(defn ^:export run-custom-dist [] (custom/sketch))

(defn ^:export run-click-spawn [] (click/sketch))

(defn ^:export run-noise [] (noise/sketch))

(defn ^:export run-n2 [] (n2/sketch))

(defn ^:export run-vec [] (vec/sketch))

; uncomment this line to reset the sketch:
; (run-sketch)
