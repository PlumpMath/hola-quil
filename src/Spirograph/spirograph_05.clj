;; Intento de concertir líneas rectas en curvas bezier.
;; no consigo que los puntos de control qiren a la par de las líneas para dar curvas con la misma curvatura

(ns Spirograph.spirograph_05
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))

(def n (atom 1))
(def grad (atom 1))

(defn key-pressed []
 ; (println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "spirograph05-####.png") ;1
   (= 39 (q/key-code)) (swap! n inc) ;RIGHT
   (= 37 (q/key-code)) (swap! n dec) ;LEFT
   (= 38 (q/key-code)) (swap! grad inc) ;UP
   (= 40 (q/key-code)) (swap! grad dec) ;DOWN
   ))

(def spiro-graph
  {:half-w (p/fnk [w] (/ w 2))
   :half-h (p/fnk [h] (/ h 2))
   :outer-r (p/fnk [half-w sep-borde] (- half-w sep-borde))
   :inner-r (p/fnk [outer-r r] (* outer-r r))})

(def spiro-graph-eager (g/compile spiro-graph))




(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb 360 100 100))


(defn draw []
  (q/background 360)

  (def out (let [initial-data {:w (q/width)
                               :h (q/height)
                               :r 0.5
                               :sep-borde 30
                               :str-w 1
                               :sw 1
                               :n (/ @n 10)
                               :grad (/ @grad 10)
                               :colorh1 180
                               :colorh2 100}]
             (merge initial-data (spiro-graph-eager initial-data))))

  (q/stroke 0 0 85)
 ; (q/ellipse (:half-w out) (:half-h out) (* 2 (:inner-r out)) (* 2 (:inner-r out)))
  ;(q/ellipse (:half-w out) (:half-h out) (* 2 (:outer-r out)) (* 2 (:outer-r out)))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.

  (q/fill 0)
  (q/text "spirograph-05" 10 20)
  (q/text "r" 10 40)
  (q/text-num (:r out) 40 40)
  (q/text "str-w" 10 60)
  (q/text-num (:str-w out) 40 60)
  (q/text "n" 10 80)
  (q/text-num (:n out) 40 80)
  (q/text "grad" 10 100)
  (q/text-num (:grad out) 40 100)
  (q/stroke 0)
  (q/stroke-weight (:str-w out))
  (q/with-translation [(:half-w out) (:half-h out)]
    (doseq [[index a] (map vector (iterate inc 0) (range 0 q/TWO-PI (/ q/PI (:n out))))]
      (let [skew1 (* (:grad out) a) ;; --> Meter skew en spirograph???
            skew2 (* skew1 2)
            alfa1 (+ skew1 a)
            alfa2 (+ skew2 a)
            alfa (q/abs-float (- alfa2 alfa1))
            x1 (* (:inner-r out) (q/cos alfa1))
            y1 (* (:inner-r out) (q/sin alfa1))
            x2 (* (:outer-r out) (q/cos alfa2))
            y2 (* (:outer-r out) (q/sin alfa2))
            l (* (:inner-r out) 0.35)
            teta1 (Math/atan (/ (* (:inner-r out)(q/sin alfa)) (- (:outer-r out)(* (:inner-r out)(q/cos alfa)))))
            beta1 (Math/atan (/ (* l (q/sin (- q/QUARTER-PI alfa teta1)))(+ (:inner-r out)(* l (q/cos (- q/QUARTER-PI alfa teta1))))))
            gamma1 (+ beta1 alfa1)
            rc1 (+ (* (+ (:inner-r out)(* l (q/cos (- q/QUARTER-PI alfa teta1))))(q/cos beta1))(* l (q/cos (- q/QUARTER-PI alfa teta1))(q/sin beta1)))
            xc1 (* rc1 (q/cos gamma1))
            yc1 (* rc1 (q/sin gamma1))

            beta2 (Math/atan (/ (* l (q/sin q/QUARTER-PI))(- (:outer-r out)(* l (q/cos q/QUARTER-PI)))))
            gamma2 (+ beta2 alfa2)
            rc2 (/ (* l (q/sin q/QUARTER-PI))(q/sin beta2))
            xc2 (* rc2 (q/cos gamma2))
            yc2 (* rc2 (q/sin gamma2))
            ]



        ;(q/stroke 0)
        (cond
         (odd? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 145 190) 90 75) ;gama de verdes-turquesas
                      ;(q/stroke (:colorh1 out) 100 80)
                      (q/stroke 360 100 100)  ;rojo
         (even? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 70 115) 71 98) ;gama de amarillos-verdosos
                       (q/stroke (:colorh2 out) 70 100)
                      ;(q/stroke 0) ;negro
        )
        (q/no-fill)
        ;(println "index " index xc1 yc1 xc2 yc2)
        (q/bezier x1 y1 xc1 yc1 xc2 yc2 x2 y2)

        (q/stroke-weight 5)
        (q/stroke (q/map-range a 0 q/TWO-PI 0 360) 100 100)
        (q/point xc1 yc1) ;Puntos

        (q/stroke 0)
        (q/text-num index (+ xc1 5) yc1)
        (q/text-num index (+ xc1 5) yc1)

        (q/stroke-weight (:str-w out))
        (q/stroke (q/map-range a 0 q/TWO-PI 0 360) 20 90)
        (q/line x1 y1 xc1 yc1) ;l

        (q/stroke (q/map-range a 0 q/TWO-PI 0 360) 100 100)
        (q/line x1 y1 x2 y2) ;line

        (q/stroke 0 0 80)
        (q/line 0 0 xc1 yc1) ;rc1

        (q/stroke 60 50 80)
        (q/line 0 0 x1 y1) ;(:inner-r out)


        )
    )))


(q/defsketch spirograph
   :host "canvas"
   ;:size [1000 1000]
   :size [700 700]
   :setup setup
   :draw draw
   :key-pressed key-pressed)
