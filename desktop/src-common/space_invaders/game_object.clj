(ns space-invaders.game-object
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.math :refer :all]
            [space-invaders.sound-stat :as sound-stat]))

(defn update-hitbox [entity]
  (if-not (nil? entity)
    (if-not (= (:type entity) "UI")
      (assoc entity
        :hitbox (rectangle
                 (:x      entity)
                 (:y      entity)
                 (:width  entity)
                 (:height entity)))
      entity)
    nil))

