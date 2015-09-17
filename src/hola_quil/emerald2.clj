(ns quil-site.examples.emerald
  (:require [quil.core :as q :include-macros true]
  ))

(defn setup []
  (q/frame-rate 60)
  ;;(q/no-cursor)
  )

;; (def slider 0)

;;(defn key-pressed []
  ;;(cond
    ;;(= 37 (q/key-code)) (do (println slider) (println "decrece") (dec slider))
    ;;(= 39 (q/key-code)) (do (println slider) (println "crece") (inc slider))
    ;;))

(defn draw []
  (q/no-stroke)
  (q/background 255)
  ;; (println  (q/mouse-x))
  (let [w (q/width)
        h (q/height)
        hw (/ w 2)
        hh (/ h 2)
        inner-r (* hh 0.5)
        outer-r (- hh 20)
        num-barras 720
        ]

    (q/fill 255)
    (q/stroke 0)
    (q/stroke-weight 1)

    (q/with-translation [hw hh]
      (doseq [a (range 0 q/TWO-PI (/ q/TWO-PI num-barras))]
        (let [skew1 (* (/ num-barras 1000) 996   a)
              skew2 (* skew1 2.0)]
          (q/line (* inner-r (q/cos (+ skew1 a)))
                  (* inner-r (q/sin (+ skew1 a)))
                  (* outer-r (q/cos (+ skew2 a)))    ;; Las coordenadas exteriores van al doble de velocidad que las interiores
                  (* outer-r (q/sin (+ skew2 a))))
          )))))

(q/defsketch emerald
   :host "canvas"
   :size [1020 1020]
   :setup setup
   :draw draw
 ;;  :key-pressed key-pressed
  )

