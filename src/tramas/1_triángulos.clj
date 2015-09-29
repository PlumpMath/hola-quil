(ns tramas.triangulos
  (:require [quil.core :as q]
            [quil.helpers.seqs :as s]))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth)

  )

(s/range-incl 0 500 (* 20 (q/sin (q/radians 60))))

(defn draw []
(q/background 360)

(let [w (q/width)
      h (q/height)
      nx 20
      ny (* nx (q/sin (q/radians 60)))
      l (/ (q/mouse-x) 2)
      a (* l (q/sin (q/radians 60)))]
  (doseq [x (s/range-incl 0 w  nx)
          y (s/range-incl 0 h  ny)]


    (q/no-fill)
    (q/stroke x 50 80)

    (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
    (q/with-translation [(- x (/ l 2))(- y (/ a 2))]

    (q/triangle 0 0 (+ 0 (/ l 2)) (+ 0 a) (+ 0 l) 0))


  )))



(q/defsketch triangulos
  :size [700 700]
  :setup setup
  :draw draw)
