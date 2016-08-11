(ns patterns.triangulos_concentricos
  (:require [quil.core :as q :include-macros true]
            [quil.helpers.seqs :as s]))

(defn key-pressed []
  (cond
   (= 49 (q/key-code)) (q/save-frame "001_triangulos-concentricos-####.png")))

(defn dibuja-triangulo-equilatero [x y l]
  (let [h (* l (q/sin (/ q/PI 3)))
        x1 (- x (/ l 2))
        y1 (- y (/ h 3))
        x2 x
        y2 (+ y (/ (* 2 h) 3))
        x3 (+ x (/ l  2))
        y3 (- y (/ h 3))]

    (q/triangle x1 y1 x2 y2 x3 y3)))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth)
  (q/no-fill)

  )


(defn draw []
  (q/background 160 80 100)
  (q/stroke 360)
  (q/stroke-weight 10)


 (doseq [diams (range 0 1000 70)]
    (let [x (/ (q/width) 2)
          y (* (q/height) (/ 1 3))]

    (dibuja-triangulo-equilatero x y diams)))

)



(q/defsketch triangulos
  :size [1000 1000]
  :setup setup
  :draw draw
  :key-pressed key-pressed)
