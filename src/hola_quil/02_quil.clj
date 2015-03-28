(ns hola-quil.quil_02
  (:require [quil.core :as q]
            [quil.middleware :as m]))


; define f
(defn f [t]

  ;; flower
  (let [r (* 200 (q/sin t) (q/cos t))]
    [(* r (q/sin (* t 0.2)))
     (* r (q/cos (* t 0.2)))])

  ;; Espiral
 ; [(* t (q/sin t))
 ;  (* t (q/cos t))]

  )

(defn draw-plot [f from to step]
  (doseq [two-points (->> (range from to step)
                          (map f)
                          (partition 2 1))]
    ; we could use 'point' function to draw a point
    ; but let's rather draw a line which connects 2 points of the plot
    (apply q/line two-points)))

(defn setup []
  (q/frame-rate 60)
  (q/background 255))

#_(defn draw []
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
   (draw-plot f 0 100 0.01)))

(defn draw []
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    ; note that we don't use draw-plot here as we need
    ; to draw only small part of a plot on each iteration
    (let [t (/ (q/frame-count) 10)]
      (q/line (f t)
              (f (+ t 0.1))))))

; run sketch
(q/defsketch trigonometry
  :size [300 300]
  :draw draw)
