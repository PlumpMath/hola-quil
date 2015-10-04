;; Varios triangulos dentro de cada triangulo

(ns patterns.1_4_3_triangulos
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]))

(s/range-incl 0 40 5)
(s/range-incl 20 50 5)
(reverse (s/range-incl -40 0 5))

(defn key-pressed []
  ;(println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "01_4_3_triangulos-####.png"))) ;1

#_(defn coord-int [i d n]
  (cond
   (>= d i) (rest (s/range-incl i d (int (/ (- d i) n))))
   (< d i) (rest (reverse (s/range-incl d i (int (/ (- i d) n)))))))

(defn coord-int [i d n]
  (cond
   (>= d i) (rest (take (- n 1)  (s/steps i (/ (- d i) n))))
   (< d i) (reverse (take (- n 1)  (s/steps d (/ (+ i d) n))))
   ))

;; 10 y 20
 (take 4 (s/steps 10 (/ (- 20 10) 5)))

 (take 4 (s/steps -20 (/ (+ -10 20) 5)))


(defn dibuja-triangulo-equilatero [x y l]
  (let [h (* l (q/sin (q/radians 60)))
        st 5
        x1 (- x (/ l 2))
        x1s (coord-int x x1 st)
        y1 (- y (/ h 3))
        y1s (coord-int y y1 st)
        x2 x
        x2s (repeat (count x1s) x)
        y2 (+ y (/ (* 2 h) 3))
        y2s (coord-int y y2 st)
        x3 (+ x (/ l  2))
        x3s (coord-int x x3 st)
        y3 (- y (/ h 3))
        y3s (coord-int y y3 st)
        coords1 (partition 4 (interleave x1s y1s x2s y2s))
        coords2 (partition 4 (interleave x2s y2s x3s y3s))
        coords3 (partition 4 (interleave x3s y3s x1s y1s))

        ]

    (q/line x1 y1 x2 y2)
    (q/line x2 y2 x3 y3)
    (q/line x3 y3 x1 y1)

    (dorun (map #(apply q/line %) coords1))
    (dorun (map #(apply q/line %) coords2))
    (dorun (map #(apply q/line %) coords3))

    ;(apply #(q/line %) (first coords1))
    ;(apply q/line (first coords2))
    ;(apply q/line (first coords3))



    ))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth))

(defn draw []
  (q/background 360)
  (q/stroke 150)
  (q/stroke-weight 1)
  (q/no-fill)

  (let [w (q/width)
        h (q/height)
        nx 100
        ny (* nx (q/sin (/ q/PI 3)))
        l (if (= 0 (q/mouse-x)) 0.01 (q/map-range (q/mouse-x) 0 w 0 300))
        a (* l (q/sin (q/radians 60)))]
    (doseq [x (s/range-incl 0 w  nx)
            [index y] (s/indexed-range-incl 0 h ny)]
      (let [xr (cond (odd? index) x
                     (even? index) (+ x (/ nx 2)))
            ]
      (q/with-translation [xr y]
        ;(q/rotate (q/map-range  (q/mouse-y) 0 h 0 q/TWO-PI))
        (dibuja-triangulo-equilatero 0 0 l)
        )
        )))

  (q/fill 100)
  (q/text "1.4.3.triángulos" 10 20)
  (q/text "mouse-x" 10 40)
  (q/text-num (q/mouse-x) 80 40)
  (q/text "mouse-y" 10 60)
  (q/text-num (q/mouse-y) 80 60))




(q/defsketch triangulos
  :size [700 700]
  :setup setup
  :draw draw
  :key-pressed key-pressed)
