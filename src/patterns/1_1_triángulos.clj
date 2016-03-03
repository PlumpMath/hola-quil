(ns patterns.1_1_triangulos
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]
            ))


(def m
  "Datos iniciales"
  {:step 10
   :seedi 0.03
   :mul 30
   :espaciado 5}
  )

(defn dibuja-horizontales []

  (q/stroke-weight 1)
(doseq [y-add (s/range-incl 0 (q/height) (m :espaciado))
        ]
    (let [seed 5.5
          xs        (s/range-incl 0  (* 2 (q/width)) (:step m))
          ys        (s/perlin-noise-seq seed (:seedi m))
          scaled-ys (c/mul-add ys (m :mul) y-add)

          line-args (d/line-join-points xs scaled-ys)]
       (q/stroke  (q/map-range y-add 0 (q/height) 100 160) 50 100) ;amarillos-verdosos
     ; (q/stroke  (q/map-range y-add 0 (q/height) 300 360) 50 100);rojos-anaranjados
      (dorun (map #(apply q/line %) line-args)))))

(defn dibuja-verticales [incl]
   (q/stroke-weight 1)
   (doseq [x-add (s/range-incl (- (q/width)) (* 2 (q/width)) (m :espaciado))]
    (let [seed 5.5

          xs        (s/perlin-noise-seq seed (:seedi m))
          scaled-xs (c/mul-add xs (m :mul) x-add)
          ang-xs     (map-indexed (fn [idx itm] (+ itm (* idx 10 (* incl (q/tan (/ q/PI 6)))))) scaled-xs)
          ys        (s/range-incl 0 (q/height) (:step m))

          line-args (d/line-join-points  ang-xs ys)]
      (q/stroke (q/map-range x-add 0 (q/width) 50 100) 50 100) ;amarillos-verdosos
      ;(q/stroke (q/map-range x-add 0 (q/width) 0 50) 50 100) ;rojos-anaranjados
      (dorun (map #(apply q/line %) line-args)))))

(defn dibuja-triangulo-equilatero [x y l]
  (let [h (* l (q/sin (/ q/PI 3)))
        x1 (- x (/ l 2))
        y1 (- y (/ h 3))
        x2 x
        y2 (+ y (/ (* 2 h) 3))
        x3 (+ x (/ l  2))
        y3 (- y (/ h 3))]

    (q/triangle x1 y1 x2 y2 x3 y3)))

(defn dibuja-marco []
  (q/begin-shape)
  (q/no-stroke)
  (q/fill 360)
  (q/vertex 0 0)
  (q/vertex 600 0)
  (q/vertex 600 920)
  (q/vertex 0 920)
  (q/vertex 0 0)

  (q/vertex 100 100)
  (q/vertex 100 820)
  (q/vertex 500 820)
  (q/vertex 500 100)
  (q/vertex 100 100)
  (q/end-shape)

  (q/stroke 300)
  (q/stroke-weight 1)
  (q/no-fill)
  (q/rect 100 100 400 720)
  )

(defn key-pressed []
  (cond
   (= 49 (q/key-code)) (q/save-frame "1_1_triangulos-####.png") ;1
   ))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth)

  )



(defn draw []
  (q/background 360)
  (dibuja-horizontales)
  (dibuja-verticales -1)
  (dibuja-verticales 1)



  (let [w (q/width)
        h (q/height)
        nx 50
        ny (* nx (q/sin (/ q/PI 3)))
        l (q/map-range (q/mouse-x) 0 w 0 300)
        a (* l (q/sin (q/radians 60)))]
    (doseq [x (s/range-incl 0 w  nx)
            [index y] (s/indexed-range-incl 0 h ny)]

      (q/stroke 360)
      (q/stroke-weight 3)
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
                          (q/pop-matrix)))))
  (dibuja-marco)

  )



(q/defsketch triangulos
  :title "triangulos"
  :size [600 920]
  :setup setup
  :draw draw
  :key-pressed key-pressed)

(println "Cleanliness is next to godliness")
