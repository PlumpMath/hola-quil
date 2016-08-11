(ns patterns.hexagonos_concentricos
  (:require [quil.core :as q :include-macros true]
            [quil.helpers.seqs :as s]))

(defn key-pressed []
  (cond
   (= 49 (q/key-code)) (q/save-frame "001_hexagonos-concentricos-####.png")))

(defn dibuja-hexagono [x y l]
  (let [x1 (+ x (* l (q/cos (/ q/PI 3))))
        y1 (- y (* l (q/sin (/ q/PI 3))))
        x2 (+ x l)
        y2 y
        x3 (+ x (* l (q/cos (/ q/PI 3))))
        y3 (+ y (* l (q/sin (/ q/PI 3))))
        x4 (- x (* l (q/cos (/ q/PI 3))))
        y4 (+ y (* l (q/sin (/ q/PI 3))))
        x5 (- x l)
        y5 y
        x6 (- x (* l (q/cos (/ q/PI 3))))
        y6 (- y (* l (q/sin (/ q/PI 3))))]

    (q/begin-shape)
      (q/vertex x1  y1)
      (q/vertex x2  y2)
      (q/vertex x3  y3)
      (q/vertex x4  y4)
      (q/vertex x5  y5)
      (q/vertex x6  y6)
    (q/end-shape :close)
    ))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth)
  (q/no-fill)

  )


(defn draw []
  (q/background 160 80 100)
  (q/stroke 360)
  (q/stroke-weight 10)


 (doseq [diams (range 0 1000 25)]
    (let [x (/ (q/width) 2)
          y (/ (q/height) 2)]

    (dibuja-hexagono x y diams)))

)



(q/defsketch hexagono
  :size [1000 1000]
  :setup setup
  :draw draw
  :key-pressed key-pressed)
