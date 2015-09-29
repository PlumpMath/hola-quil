(ns patterns.triangulos
  (:require [quil.core :as q]
            [quil.helpers.seqs :as s]))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth)

  )

(defn dibuja-triangulo-equilatero [x y l]
  (let [h (* l (q/sin (/ q/PI 3)))
        x1 (- x (/ l 2))
        y1 (- y (/ h 3))
        x2 x
        y2 (+ y (/ (* 2 h) 3))
        x3 (+ x (/ l  2))
        y3 (- y (/ h 3))]

    (q/triangle x1 y1 x2 y2 x3 y3)))


(defn draw []
  (q/background 160 80 100)
  (let [w (q/width)
        h (q/height)
        nx 50
        ny (* nx (q/sin (/ q/PI 3)))
        l (q/map-range (q/mouse-x) 0 w 0 300)
        a (* l (q/sin (q/radians 60)))]
    (doseq [x (s/range-incl 0 w  nx)
            [index y] (s/indexed-range-incl 0 h ny)]

      (q/stroke 360)
      (q/stroke-weight 1.5)
      (q/no-fill)


        (cond
         (odd? index)  (do
                         (q/push-matrix)
                         (q/with-translation [x y]
                           (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
                           ;(q/fill (q/map-range (+ x y) 0 (+ w h) 0 100) 70 100 50)
                           (dibuja-triangulo-equilatero 0 0 l))
                         (q/pop-matrix))

         (even? index)  (do
                          (q/push-matrix)
                          (q/with-translation [(+ x (/ nx 2)) y]
                           (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
                           ;(q/fill (q/map-range (+ y x) 0 (+ w h) 260 360) 70 100 50)
                           (dibuja-triangulo-equilatero 0 0 l))
                          (q/pop-matrix))))))



(q/defsketch triangulos
  :size [700 700]
  :setup setup
  :draw draw)
