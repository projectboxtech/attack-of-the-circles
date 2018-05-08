; core.clj
; Copyright © 2018 Jonathan R. Camenzuli (jrcamenzuli@gmail.com)

(ns ai.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [ai.utils :as utils]
            [ai.physics :as physics]
            [ai.ai :as ai]
            [ai.graphics :as graphics]
            )
  (:import 
    java.awt.event.KeyEvent
    (ai.utils Vector2D)
    (ai.physics Particle)
    )
  (:gen-class)
)

(def FPS 60)
(def DT (/ 1.0 FPS))
(def N-ENEMIES 10)
(def field {:x1 0.0 :x2 1.0 :y1 0.0 :y2 1.0 :window-scale 500})
(def safe-color [0 128 255])
(def unsafe-color [255 0 0])

; initial state
(def state
  {
    :enemies
    (vec
      (for [i (range N-ENEMIES)]
        (let [
            angle (* i (/ (* Math/PI 2.0) N-ENEMIES))
            speed 0.5
            x-center (/ (- (:x2 field) (:x1 field)) 2.0)
            x-center (/ (- (:y2 field) (:y1 field)) 2.0)
            starting-distance (+ 0.025 0.025)
            velocity (Vector2D. (* speed (Math/sin angle)) (* speed (Math/cos angle)))
            position (Vector2D. (+ x-center (* starting-distance (Math/sin angle))) (+ x-center (* starting-distance (Math/cos angle))))
          ]
          (Particle. 
            position
            velocity
            (Vector2D. 0.0 0.0) ; acceleration
            0.0 ; score
            0.05 ; size
            [0 255 0] ; color
          )
        )
      )
    )
    :player
    (Particle. 
      (Vector2D. 0.5 0.5) ; position
      (Vector2D. 0.0 0.0) ; velocity
      (Vector2D. 0.0 0.0) ; acceleration
      0.0 ; score
      0.05 ; size
      [0 128 255] ; color
    )
    :recovery-rate 1.0
    :attack-rate 20.0
  }
)

(defn setup []
  (q/frame-rate FPS)
  (q/color-mode :rgb)
  (q/no-stroke)
  state
)

(defn update-collisions-player [state]
  (update-in 
    state
    [:player]
    physics/collide
  )
)

(defn update-collisions-enemies [state]
  (assoc
    state
    :enemies
    (vec
      (map 
        physics/collide
        (:enemies state)
      )
    )
  )
)

(defn update-collisions [state]
  (update-collisions-enemies
    (update-collisions-player state)
  )
)

(defn attacking?
  [enemies player]
  (if (empty? enemies) false
    (if 
      (let [
        enemy (first enemies)
        pos1 (:position player)
        pos2 (:position enemy)
        size1 (:size player)
        size2 (:size enemy)
        result (< (utils/mag (utils/sub pos2 pos1)) (+ (/ size1 2.0) (/ size2 2.0)))
        ]
        result
      )
      true
      (attacking? (rest enemies) player)
    )
  )
)

(defn update-physics-player [state]
  (update-in
    state
    [:player]
    (fn [b] (physics/move b DT))
  )
)

(defn update-physics-enemies [state]
  (assoc
  state
  :enemies
  (vec
    (map 
      (fn [b] (physics/move b DT))
      (:enemies state)
      )
    )
  )
)

(defn update-physics [state]
  (update-physics-enemies
    (update-physics-player state)
  )
)

(defn update-score [state]
  (if
    (attacking? (:enemies state) (:player state))
    (assoc-in (assoc-in state [:player :color] unsafe-color) [:player :score] (- (:score (:player state)) (* (:attack-rate state) DT)))
    (assoc-in (assoc-in state [:player :color] safe-color) [:player :score] (+ (:score (:player state)) (* (:recovery-rate state) DT)))
  )
)

(defn update-state [state]
  (update-score
    (update-collisions
      (update-physics state)
    )
  )
)

(defn key-pressed [state key]
  (case (:key key)
  :left (assoc-in state [:player :accel :x] -1.0)
  :right (assoc-in state [:player :accel :x] 1.0)
  :down (assoc-in state [:player :accel :y] 1.0)
  :up (assoc-in state [:player :accel :y] -1.0)
  state
  )
)

(defn key-released [_state key]
  (case (:key key)
  :left (assoc-in _state [:player :accel :x] 0.0)
  :right (assoc-in _state [:player :accel :x] 0.0)
  :down (assoc-in _state [:player :accel :y] 0.0)
  :up (assoc-in _state [:player :accel :y] 0.0)
  :r state
  _state
  )
)

(defn -main [& args]
  (q/sketch
  :title "ai"
  :host "host"
  :size [(* (:window-scale field) (- (:x2 field) (:x1 field))) (* (:window-scale field) (- (:y2 field) (:y1 field)))]
  :setup setup
  :update update-state
  :draw graphics/draw-state
  :middleware [m/fun-mode]
  :features [:exit-on-close]
  :key-pressed key-pressed
  :key-released key-released
  )
)