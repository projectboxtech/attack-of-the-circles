; ai.clj
; Copyright © 2018 Jonathan R. Camenzuli (jrcamenzuli@gmail.com)

(ns ai.ai)

(defn ai-keys [in]
	"transfers a vector of size 2 to up down left right movement"
	(let [
		up (> (get in 0) 0.75)
		down (< (get in 0) 0.25)
		left (< (get in 1) 0.25)
		right (> (get in 1) 0.75)
	]{:up up :down down :left left :right right})
)