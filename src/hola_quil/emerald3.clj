(ns quil-site.examples.emerald
  (:require [quil.core :as q :include-macros true]
  ))

(defn setup []
  (q/frame-rate 60)
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
        sw 1.2
        n 300
        cur 600]

    (q/stroke 0)
    (q/fill 0)
    (q/text "emerald3" 10 20)
    (q/text-num sw 10 40)
    (q/text-num n 10 60)
    (q/text-num cur 10 80)
    (q/stroke-weight sw)
    (q/with-translation [hw hh]
      (doseq [a (range 0 q/TWO-PI (/ q/PI n))] ;; el numero de barras es nx2. Siempre es par
        (let [skew1 (* cur a)
              skew2 (* skew1 2.0)]
          (q/line (* inner-r (q/cos (+ skew1 a)))
                  (* inner-r (q/sin (+ skew1 a)))
                  (* outer-r (q/cos (+ skew2 a)))
                  (* outer-r (q/sin (+ skew2 a))))))))
  )

(defn draw []
  (q/no-stroke)
  (q/background 255)
  ;; (println  (q/mouse-x))
  (dibuja)
  )

(q/defsketch emerald
   :host "canvas"
   :size [1020 1020]
   :setup setup
   :draw draw
 ;;  :key-pressed key-pressed
  )

