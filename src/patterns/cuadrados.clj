(ns patterns.cuadrados
  (:require [quil.core :as q]
            [quil.helpers.seqs :as s]))

(defn setup []
  (q/color-mode :hsb 360 100 100)

  )


(defn draw []


(let [step (q/mouse-x)]
  (doseq [x (s/range-incl 0 500 step)
          y (s/range-incl 0 500 step)]
    (q/fill (q/map-range (if (q/mouse-pressed?) (q/random  (+ x y)) (+ x y)) 0 (+ x y) 0 360) 50 90)
    ;(q/no-stroke)
    (q/begin-shape)
    (dorun (map #(apply q/vertex %)  [[x y][x (+ y step)][(+ x step) (+ y step)][(+ x step) y][x y]]))
    (q/end-shape)


  )))



(q/defsketch cuadrados
  :size [500 500]
  :setup setup
  :draw draw)
