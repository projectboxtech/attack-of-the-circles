; graphics.clj
; Copyright © 2018 Jonathan R. Camenzuli (jrcamenzuli@gmail.com)

(ns ai.graphics
    (:require [quil.core :as q :include-macros true])
)

(defn draw-particle [{:keys [position velocity accel score size color]}]
  (apply q/fill color)
  (q/ellipse (* (q/width) (:x position)) (* (q/height) (:y position)) (* size (q/width)) (* size (q/height)))
)

(defn draw-state [state]
  (q/background 0)
  (draw-particle (:player state)) ; player
  (doseq [enemy (:enemies state)] ; enemies
    (draw-particle enemy)
  )
  (apply q/fill [255 255 255])
  (q/text-size 20)
  (q/text (str "score = " (format "%.1f" (:score (:player state)))) 0 (q/height))
  ; (q/save-frame "./out/frames/frame####.png") ; ffmpeg -r 60 -i %04d.png -c:v huffyuv test.avi
)