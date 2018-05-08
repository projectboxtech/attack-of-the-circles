; project.clj
; Copyright © 2018 Jonathan R. Camenzuli (jrcamenzuli@gmail.com)

(defproject ai "0.1.0-SNAPSHOT"
  :description "The goal of this project is to create a Artificial Intelligence within the context of a game."
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [quil "2.7.1"]]
  :aot [ai.core]
  :main ai.core)
