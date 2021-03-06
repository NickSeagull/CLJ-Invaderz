(ns space-invaders.bullet
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [space-invaders.game-object :as game-object]))

(def ^:private bullet-sprite  "bullet.png")
(def ^:private starting-y     90)
(def ^:private sprite-height  16)
(def ^:private sprite-width   16)
(def ^:private size-scale     1.25)
(def ^:private bullet-height  (* sprite-height size-scale))
(def ^:private bullet-width   (* sprite-width size-scale))
(def ^:private speed          30)
(def ^:private vertical-bound 600)

(defn- out-of-vertical-bound [entity]
  (> (:y entity) vertical-bound))

(defn- bullet? [entity]
  (= (:type entity) "Bullet"))

(defn- increment-y [entity]
  (assoc entity :y (+ (:y entity) speed)))

(defn- move-bullet [entity]
  (if (bullet? entity)
    (if (out-of-vertical-bound entity)
      nil
      (increment-y entity))
    entity))

(defn make [x]
  (-> (assoc (texture bullet-sprite)
     	:type   "Bullet"
     	:y      starting-y
     	:x      x
     	:width  bullet-width
     	:height bullet-height)
      (game-object/update-hitbox)))

(defn move-all [entities]
  (->> (map move-bullet entities)
       (map game-object/update-hitbox)))
