(ns interactive-art.core
  (:require [sketches.random :as random]
            [sketches.normal-dist :as norm]
            [sketches.custom-dist :as custom]))

(defn ^:export run-random [] (random/sketch))

(defn ^:export run-normal-dist [] (norm/sketch))

(defn ^:export run-custom-dist [] (custom/sketch))

; uncomment this line to reset the sketch:
; (run-sketch)
