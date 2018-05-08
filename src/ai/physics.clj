; physics.clj
; Copyright © 2018 Jonathan R. Camenzuli (jrcamenzuli@gmail.com)

(ns ai.physics
	(:require 
		[ai.utils :as utils]
	)
	(:import 
		java.awt.event.KeyEvent
		(ai.utils Vector2D)
	)
)

(defrecord Particle [#^Vector2D position #^Vector2D velocity #^Vector2D accel #^double score #^double size #^ints color])

(defprotocol Kinematics
	"Movements of bodies"
	(move [b dt] "Moves body one time step")
	(collide [b] "Handles body collisions")
)

(extend-protocol Kinematics
	Particle
	(move [b dt]
		(let [
			vel (utils/add (:velocity b) (utils/mul (:accel b) dt))
			pos (utils/add (:position b) (utils/mul (:velocity b) dt))
			]
			(assoc b :velocity vel :position pos)
		)
	)
	(collide [b]
		(let 
			[
			isVertWall (or (< (:x (:position b)) 0) (> (:x (:position b)) 1))
			isHorizWall (or (< (:y (:position b)) 0) (> (:y (:position b)) 1))
			vert (if isVertWall (assoc-in b [:velocity :x] (* -1.0 (:x (:velocity b)))) b)
			hori (if isHorizWall (assoc-in vert [:velocity :y] (* -1.0 (:y (:velocity vert)))) vert)
			] 
			hori
		)
	)
)