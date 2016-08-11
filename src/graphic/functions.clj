(ns graphic.functions
  (:require [quil.core :as q]))

(defn setup []
  (q/smooth))

(defn weight [d n] (- 500 (/ (* 500 (* d d))(+ n (* d d)))))
(weight 500 450)

(defn draw-segment [x f n]
  (q/line x (f x n) (+ x 5) (f (+ x 5) n)))


(defn draw []
  (q/background 250)
  (q/stroke 0)
  (q/stroke-weight 1)
  ;; Eje y
  (q/line 50 50 50 (- (q/height) 50))
  ;; Eje x
  (q/line 50 (- (q/height) 50) (- (q/width) 50) (- (q/height) 50))

  ;; gráfica
  (q/push-matrix)
  (q/translate 50 50)
  (q/stroke 0)
  (doseq [d (range 60 500 5)]
    (q/stroke 0)
    (q/stroke-weight (weight d 450))
    (draw-segment d weight 450))
  (doseq [d (range 60 500 5)]
    (q/stroke 250 0 0)
    (q/stroke-weight 1)
    (draw-segment d weight 200)
    (draw-segment d weight 500)
    (draw-segment d weight 600)
    (draw-segment d weight 800))
  (q/pop-matrix)

    ;; línea a 50 (espesor máximo 50?)
  (q/stroke 0 250 0)
  (q/line 50 100 (- (q/width) 50) 100))

(q/defsketch functions
  :size [600 600]
  :setup setup
  :draw draw)
