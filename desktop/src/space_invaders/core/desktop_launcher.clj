(ns space-invaders.core.desktop-launcher
  (:require [space-invaders.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. space-invaders "CLJ Invaderz" 800 600)
  (Keyboard/enableRepeatEvents true))
