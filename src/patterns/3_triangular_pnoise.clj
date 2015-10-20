(ns patterns.3_triangular_pnoise
  (:require [quil.core :refer :all]
            [quil.helpers.drawing :refer [line-join-points]]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq]]
            [quil.helpers.calc :refer [mul-add]]))



(defn setup []
  (background 255)
  (stroke-weight 2)
  (smooth)
  (color-mode :hsb 360 100 100)

  (doseq [y-add (range-incl 0 (height) 15)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.03
          y-mul     30
          xs        (range-incl 0  (width) step)
          ys        (perlin-noise-seq seed seed-incr)
          scaled-ys (mul-add ys y-mul y-add)
          line-args (line-join-points xs scaled-ys)]

      (stroke  (map-range y-add 0 (height) 120 170) 50 100)
      (dorun (map #(apply line %) line-args))
      ;;(stroke 0 0 0)
      ;;(dorun (map #(point % %2) xs scaled-ys))
      ))


   (doseq [x-add (range-incl (- (width)) (width) 15)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.03
          x-mul     30
          xs        (perlin-noise-seq seed seed-incr)
          scaled-xs (mul-add xs x-mul x-add)
          ang-xs     (map-indexed (fn [idx itm] (+ itm (* idx 10 (tan (/ PI 6))))) scaled-xs)
          ys        (range-incl 0 (height) step)
          line-args (line-join-points  ang-xs ys)]
      (stroke (map-range x-add 0 (width) 170 220) 50 100)
      (dorun (map #(apply line %) line-args))
      ;;(stroke 0 0 0)
      ;;(dorun (map #(point % %2) ang-xs ys))
      ))

  (doseq [x-add (range-incl 0 (* 2 (width)) 15)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.03
          x-mul     30
          xs        (perlin-noise-seq seed seed-incr)
          scaled-xs (mul-add xs x-mul x-add)
          ang-xs     (map-indexed (fn [idx itm] (+ itm (* idx 10 (- (tan (/ PI 6)))))) scaled-xs)
          ys        (range-incl 0 (height) step)
          line-args (line-join-points  ang-xs ys)]
      (stroke (map-range x-add 0 (width) 220 270) 50 100)
      (dorun (map #(apply line %) line-args))
      ;;(stroke 0 0 0)
      ;;(dorun (map #(point % %2) ang-xs ys))
      )))

(defsketch Triangular-pnoise
  :title "Triangular pnoise"
  :setup setup
  :size [350 550])

