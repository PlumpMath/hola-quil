(ns quil-site.examples.emerald
  (:require [quil.core :as q :include-macros true]

  ))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb 360 100 100)
 ;; (q/no-cursor)
  )

;; (def slider 0)

;;(defn key-pressed []
  ;;(cond
    ;;(= 37 (q/key-code)) (do (println slider) (println "decrece") (dec slider))
    ;;(= 39 (q/key-code)) (do (println slider) (println "crece") (inc slider))
    ;;))



    (defn dibuja []
  (let [w (q/width)
        h (q/height)
        hw (/ w 2)
        hh (/ h 2)
        inner-r (* hw 0.5)
        outer-r (- hh 20)
        sw 1.3
        n 300
        cur 74.4]

    (q/fill 0)
    (q/text "emerald3" 10 20)
    (q/text-num sw 10 40)
    (q/text-num n 10 60)
    (q/text-num cur 10 80)
    (q/stroke-weight sw)
    (q/with-translation [hw hh]
      (doseq [[index a] (map vector (iterate inc 0) (range 0 q/TWO-PI (/ q/PI n)))] ;; el numero de barras es nx2. Siempre es par
        (let [skew1 (* cur a) ;; cuando cur alcanza el doble de n, se completa el ciclo.
              skew2 (* skew1 2.0)]

          ;(q/stroke (q/map-range a 0 q/TWO-PI 50 150) 200 255)
          (q/stroke (q/map-range a 0 q/TWO-PI 145 190) 96 76)
          (if (odd? index)
          (q/line (* inner-r (q/cos (+ skew1 a)))
                  (* inner-r (q/sin (+ skew1 a)))
                  (* outer-r (q/cos (+ skew2 a)))
                  (* outer-r (q/sin (+ skew2 a)))))
          (q/stroke (q/map-range a 0 q/TWO-PI 70 115) 71 98)
          (if (even? index)
          (q/line (* inner-r (q/cos (+ skew1 a)))
                  (* inner-r (q/sin (+ skew1 a)))
                  (* outer-r (q/cos (+ skew2 a)))
                  (* outer-r (q/sin (+ skew2 a)))))
          ))))
  )

(defn draw []
  (q/no-stroke)
  (q/background 360)
  ;; (println  (q/mouse-x))
  (dibuja)
  )

(q/defsketch emerald
   :host "canvas"
   :size [520 520]
   :setup setup
   :draw draw
   ;:renderer :pdf
   ;:output-file "emerald3"
 ;;  :key-pressed key-pressed
  )

