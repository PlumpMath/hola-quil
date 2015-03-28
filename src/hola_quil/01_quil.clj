(ns hola-quil.quil_01
  (:require [quil.core :as q]
            [quil.middleware :as m]))

; define function which draws spiral

(defn setup []

  )

(defn draw []
  (q/background 255)
  (q/smooth)
  (q/no-stroke)
  (q/fill 110)
 (doseq []
   (q/ellipse))

  )


; run sketch
(q/defsketch hola-quil
  :size [300 300]
  :draw draw)


#_(q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (doseq [t (range 0 100 0.01)]
      (q/point (* (* 1.5 t) (q/sin t))
               (* t (q/cos t)))))
