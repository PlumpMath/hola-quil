(ns circles.circles-1
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]))

(def r "radio" 100)
(def a360 (->> (s/range-incl 0 360 0.1) (map q/radians)))

(defn circunf [cx cy r]
  (let [xs (map #(+ cx (* r (q/cos %))) a360)
        ys (map #(+ cy (* r (q/sin %))) a360)
        line-args (d/line-join-points xs ys)]
    (dorun (map #(apply q/line %) line-args))
    (q/ellipse (+ cx r) (+ cy 0) 5 5)

    ))



(defn setup []

  (q/smooth)
  (q/stroke-weight 1)

  )


(defn draw []
  (q/background 250)

  (q/with-translation [(/(q/width) 2) (/(q/height) 2)]
    (let [xs (map #(* r (q/cos %)) a360)
          ys (map #(* r (q/sin %)) a360)]
    (doseq [coords (partition 2 (interleave xs ys))]
        (circunf (first coords) (second coords) r)
     ))
    )
  )



(q/defsketch circles
  :title "circles-1"
  :size [600 600]
  :setup setup
  :draw draw)
