;; Esta es mi forma de solucionarlo sin usar estados variables (atoms)

(ns gen_art.2_circulos_concentricos
  (:require [quil.core :as q :include-macros true]))

(defn setup []
  (q/background 250)
  (q/smooth)
  (q/no-fill)
  (q/stroke 0)
  (q/stroke-weight 1)
  (q/frame-rate 24))

(defn draw []
  (doseq [diams (range 0 400 10)]
    (let [x (/ (q/height) 2)
          y (/ (q/width) 2)]

    (q/ellipse x y diams diams))))

(q/defsketch circulos_concentricos
  :title "circulos concentricos"
  :setup setup
  :draw draw
  :size [400 400])


;; Solución usando atoms:

;;(defn setup []
;;  (frame-rate 24)
;;  (smooth)
;;  (background 180)
;;  (stroke 0)
;;  (stroke-weight 1)
;;  (no-fill)
;;  (set-state! :diam (atom 10)
;;              :cent-x (/ (width) 2)
;;              :cent-y (/ (height) 2)))

;;(defn draw []
;;  (let [cent-x (state :cent-x)
;;        cent-y (state :cent-y)
;;        diam   (state :diam)]
;;    (when (<= @diam 400)
;;      (ellipse cent-x cent-y @diam @diam)
;;      (swap! diam + 10))))

;;(defsketch gen-art-3
;;  :title "Concentric Circles"
;;  :setup setup
;;  :draw draw
;;  :size [500 300])
