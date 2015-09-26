;; Mi version

(ns gen_art.3_lineas_horizontales
  (:require [quil.core :as q]))

(defn draw-line-h
  "Dibuja líneas horizontales a una altura h"
  [h]
  (q/stroke 250 (q/map-range h 0 (q/height) 0 250))
  (q/line 20 h (- (q/width) 20) h)
  (q/stroke 0 (q/map-range h 0 (q/height) 250 0))
  (q/line 20 (+ h 4) (- (q/width) 20) (+ h 4))


  )

(defn setup []
  (q/background 180)
  (q/stroke-weight 4)
  (q/stroke-cap :square)
  (doseq [a (range 10 (q/height) 10)]
    (draw-line-h a)
    )
  )




(q/defsketch lineas_horizontales
  :title "lineas horizontales"
  :setup setup
  :size [500 300])


;; (defn draw-line
;;   "Draws a horizontal line on the canvas at height h"
;;   [h]
;;   (stroke 0 (- 255 h))
;;   (line 10 h (- (width) 20) h)
;;   (stroke 255 h)
;;   (line 10 (+ h 4) (- (width) 20) (+ h 4)))

;; (defn setup []
;;  (background 180)
;;  (stroke-weight 4)
;;  (stroke-cap :square)
;;  (let [line-heights (range 10 (- (height) 15) 10)]
;;    (dorun (map draw-line line-heights))))

;;(defsketch example-4
;;  :title "Fading Horizontal Lines"
;;  :setup setup
;;  :size [500 300])

;;(defn -main [& args])
