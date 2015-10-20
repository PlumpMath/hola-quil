(ns patterns.2_malla_cuad_pnoise
  (:require [quil.core :refer :all]
            [quil.helpers.drawing :refer [line-join-points]]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq]]
            [quil.helpers.calc :refer [mul-add]]))


(defn setup []
  (background 255)
  (stroke-weight 2)
  (smooth)
  (color-mode :hsb 360 100 100)


  (doseq [y-add (range-incl 0 (height) 20)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.1
          y-mul     40
          xs        (range-incl 0  (width) step)
          ys        (perlin-noise-seq seed seed-incr)
          scaled-ys (mul-add ys y-mul y-add)
          line-args (line-join-points xs scaled-ys)]
      (stroke 180 40 90)
      (dorun (map #(apply line %) line-args))))


   (doseq [x-add (range-incl 0 (height) 20)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.1
          x-mul     40
          xs        (perlin-noise-seq seed seed-incr)
          ys        (range-incl 0 (width) step)
          scaled-xs (mul-add xs x-mul x-add)
          line-args (line-join-points  scaled-xs ys)]
      (stroke 10 100 100)
      (dorun (map #(apply line %) line-args)))))

(defsketch gen-art-7
  :title "2 Malla cuadrada pnoise"
  :setup setup
  :size [700 700])




