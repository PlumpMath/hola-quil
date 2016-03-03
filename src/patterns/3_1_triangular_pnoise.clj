(ns patterns.3_1_triangular_pnoise
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]))

(def m
  "Datos iniciales"
  {:step 10
   :seedi 0.03
   :mul 30
   :espaciado 5}
  )

(defn dibuja-horizontales []
(doseq [y-add (s/range-incl 0 (q/height) (m :espaciado))]
    (let [seed (rand 10)
          xs        (s/range-incl 0  (* 2 (q/width)) (:step m))
          ys        (s/perlin-noise-seq seed (:seedi m))
          scaled-ys (c/mul-add ys (m :mul) y-add)

          line-args (d/line-join-points xs scaled-ys)]

      (q/stroke  (q/map-range y-add 0 (q/height) 220 120) 50 100)
      (q/stroke-weight  (q/map-range y-add 0 (q/height) 1 2))
      (dorun (map #(apply q/line %) line-args)))))

(defn dibuja-verticales [incl]
   (doseq [x-add (s/range-incl (- (q/width)) (* 2 (q/width)) (m :espaciado))]
    (let [seed (rand 10)

          xs        (s/perlin-noise-seq seed (:seedi m))
          scaled-xs (c/mul-add xs (m :mul) x-add)
          ang-xs     (map-indexed (fn [idx itm] (+ itm (* idx 10 (* incl (q/tan (/ q/PI 6)))))) scaled-xs)
          ys        (s/range-incl 0 (q/height) (:step m))

          line-args (d/line-join-points  ang-xs ys)]

      (q/stroke (q/map-range x-add 0 (*  incl (q/width)) 190 230) 50 100)
      (q/stroke-weight  (q/map-range x-add (- (q/width)) (* 2 (q/width)) 0.5 2.5))
      (dorun (map #(apply q/line %) line-args)))))

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

  (q/stroke 200)
  (q/no-fill)
  (q/rect 100 100 400 720)
  )

(defn setup []
  (q/stroke-weight 1)
  (q/smooth)
  (q/color-mode :hsb 360 100 100)
  (q/background 360)

  (dibuja-horizontales)
  (dibuja-verticales -1)
  (dibuja-verticales 1)


  ;(dibuja-marco)

  )

(q/defsketch triangular-pnoise
  :title "Triangular pnoise"
  :size [960 355]
  :setup setup
  )

