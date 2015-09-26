(ns gen_art.2_random_scribble
  (:require [quil.core :as q :include-macros true]
            [quil.helpers.seqs :as s]
            [quil.helpers.drawing :as d]))

(repeat 5 1)

(defn setup []
  (q/background 250)
  (q/stroke-weight 4)
  (q/stroke 0 30)
  (q/line 10 (/ (q/height) 2) (- (q/width) 10) (/ (q/height) 2))

  (q/stroke 0)
  (q/with-translation [0 (/ (q/height) 2)]
  (let [step 10
        border-x 10
        amp 30
        xs (s/range-incl border-x (- (q/width) border-x) step)
        ys (repeatedly (count xs) #(q/random (- amp) amp))
        line-args (d/line-join-points xs ys)]

  (dorun (map #(apply q/line %) line-args)))))

(q/defsketch random_scribble
  :setup setup
  :size [500 100])


