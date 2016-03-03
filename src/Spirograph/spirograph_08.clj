;; Astroide exterior, circunferencia interior más grande


(ns Spirograph.spirograph_08
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))

(def n (atom 200))
(def grad (atom 100000))
(def twist (atom 0))


(defn key-pressed []
  ;(println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "spirograph08-####.png") ;1
   (= 39 (q/key-code)) (swap! n inc) ;RIGHT
   (= 37 (q/key-code)) (swap! n dec) ;LEFT
   (= 38 (q/key-code)) (swap! grad inc) ;UP
   (= 40 (q/key-code)) (swap! grad dec) ;DOWN
   (= 104 (q/key-code)) (swap! twist inc) ;8
   (= 98 (q/key-code)) (swap! twist (fn [x] (if (> x 0) (dec x) 0))) ;2
   ))

(def spiro-graph-ini
  {:half-w (p/fnk [w] (/ w 2))
   :half-h (p/fnk [h] (/ h 2))
   :outer-r (p/fnk [half-w sep-borde] (- half-w sep-borde))
   :inner-r (p/fnk [outer-r r] (* outer-r r))
   })

(def spiro-graph-ini-eager (g/compile spiro-graph-ini))



(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb 360 100 100))


(defn draw []
  (q/background 0)
  (q/stroke 0)

  (def out (let [initial-data {:w (q/width)
                               :h (q/height)
                               :r 0.5
                               :sep-borde 30
                               :str-w 1
                               :sw 1
                               :n  @n
                               :grad (/ @grad 1000)
                               :colorh1 160
                               :colorh2 50
                               :tw @twist}]
             (merge initial-data (spiro-graph-ini-eager initial-data))))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.

  (q/fill 360)
  (q/text "spirograph-08" 10 20)
  (q/text "r" 10 40)
  (q/text-num (:r out) 40 40)
  (q/text "str-w" 10 60)
  (q/text-num (:str-w out) 40 60)
  (q/text "n" 10 80)
  (q/text-num (:n out) 40 80)
  (q/text "grad" 10 100)
  (q/text-num (:grad out) 40 100)
  (q/text "twist" 10 120)
  (q/text-num (:tw out) 40 120)
  (q/stroke 0)
  (q/stroke-weight (:str-w out))
  (q/with-translation [(:half-w out) (:half-h out)]

    ;(q/no-fill)
    ;(q/rotate q/HALF-PI)
    ;(q/arc 0 0 200 50 0 q/PI)

    (doseq [[index a] (map vector (iterate inc 0) (range 0 q/TWO-PI (/ q/PI (:n out))))]
      (let [skew1 (* (:grad out) a) ;; --> Meter skew en spirograph???
            skew2 (* skew1 2)
            alfa1 (+ skew1 a)
            alfa2 (+ skew2 a)
            x1 (* 1.5 (:inner-r out)  (q/cos alfa1)) ;; Circunferencia interior más grande
            y1 (* 1.5 (:inner-r out)  (q/sin alfa1)) ;; Circunferencia interior más grande
            x2 (* (:outer-r out) (Math/pow (q/cos alfa2) 3)) ;; Astroide
            y2 (* (:outer-r out) (Math/pow (q/sin alfa2) 3)) ;;Astroide
            xc (+ (/ (- x2 x1) 2) x1)
            yc (+ (/ (- y2 y1) 2) y1)
            rx (Math/hypot (- x2 x1) (- y2 y1))
            ry (:tw out)
            angi (atom 0)
            angf (atom q/TWO-PI)
            ]

        ;(q/stroke 0)
        (cond
         (odd? index) (do
                        ;(q/stroke 360)
                        ;(q/stroke (:colorh1 out) 45 100)
                        (q/stroke 360 0 (q/map-range a 0 q/TWO-PI 80 100))
                        ;(q/stroke 360 100 100)  ;rojo
                        )
         (even? index)
                       (do
                         ;(q/stroke 360)
                         ;(q/stroke (:colorh1 out) 45 100)
                         ;(q/stroke (:colorh2 out) 45 100)
                         (q/stroke 360 0 (q/map-range a 0 q/TWO-PI 40 60))
                         ;(q/stroke 0) ;negro
                         ))

        (q/no-fill)
        (q/with-translation [xc yc]
          (if
           (= (/ (- x2 x1) rx) 1)
            (q/rotate 0)
            (if (> y2 y1)
              (q/rotate  (Math/acos  (/ (- x2 x1) rx)))
              (q/rotate  (- 0 (Math/acos  (/ (- x2 x1) rx))))))
        (q/arc 0 0 rx ry @angi @angf))

        )
    )))


(q/defsketch spirograph
   :host "canvas"
   ;:size [1000 1000]
   :size [700 700]
   :setup setup
   :draw draw
   :key-pressed key-pressed)
