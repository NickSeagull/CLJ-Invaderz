(ns space-invaders.main-level
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.ui :refer :all]
            [space-invaders.player :as player]
            [space-invaders.bullet :as bullet]
            [space-invaders.enemy :as enemy]
            [space-invaders.game-object :as game-object]
            [space-invaders.background :as background]
            [space-invaders.sound-stat :as sound-stat]))

(defn- make-version-label
  [version]
  (-> (assoc (label (str "invaders.clj - " version " version.") (color :white))
        :type "Label"
        :hitbox nil)))

(defn- check-events
  [screen entities]
  (cond
   (key-pressed? :dpad-left)
   (player/move screen entities :left)
   (key-pressed? :dpad-right)
   (player/move screen entities :right)
   :else
   entities))


(defn spawn-enemy [screen entities]
  (conj entities (enemy/make screen entities (+ 3 (* (/ 20 8000) (+ 1 (:score (player/get-player entities))))))))

(defn make-bullet [screen entities]
  (let [player (player/get-player entities)
        x      (+ (:x player) (/ (:width player) 3))]
    (conj entities (bullet/make x))))

(defn update-lifes [entities]
  (let [lbl (first (filter #(= (:subtype %) "Lifes") entities))
        p (player/get-player entities)]
    (label! lbl :set-text (str "Lifes: " (:lifes p)))))

(defn update-score [entities]
  (let [lbl (first (filter #(= (:subtype %) "Score") entities))
        p (player/get-player entities)]
    (label! lbl :set-text (str "Score: " (:score p)))))

(defn start
  [screen entities]
  (add-timer! screen :spawn-enemy 4 0.20)
  (sound "countdown.wav" :play)
  (let [player     (player/make screen)
        bg1        (background/make screen 0)
        bg2        (background/make screen (height screen))
        score      (assoc
                       (label
                        (str "Score: " (:score player))
                        (color :white))
                     :type "UI"
                     :subtype "Score"
                     :x 50
                     :y (- (height screen) 70))
        lifes      (assoc 
                       (label 
                        (str "Lifes: " (:lifes player)) 
                        (color :white))
                     :type "UI"
                     :subtype "Lifes"
                     :x 50
                     :y (- (height screen) 50))]
    (-> [bg1 bg2 player lifes score])))

(defn render
  [screen entities]
  (update-lifes entities)
  (update-score entities)
  (-> (check-events screen entities)
      (bullet/move-all)
      (enemy/move-all)
      (player/maybe-die)
      (background/move-all screen)))
