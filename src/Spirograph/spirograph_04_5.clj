;; Las lineas se pueden convertir en arcos de elipse


(ns Spirograph.spirograph_04_5
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))

(def n (atom 300))
(def grad (atom 0))
(def twist (atom 0))


(defn key-pressed []
  (println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "spirograph04-5-####.png") ;1
   (= 39 (q/key-code)) (swap! n inc) ;RIGHT
   (= 37 (q/key-code)) (swap! n dec) ;LEFT
   (= 38 (q/key-code)) (swap! grad inc) ;UP
   (= 40 (q/key-code)) (swap! grad dec) ;DOWN
   (= 104 (q/key-code)) (swap! twist inc) ;8
   (= 98 (q/key-code)) (swap! twist (fn [x] (if (> x 0) (dec x) 0))) ;2
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
  (q/stroke 0)

  (def out (let [initial-data {:w (q/width)
                               :h (q/height)
                               :r 0.5
                               :sep-borde 30
                               :str-w 1
                               :sw 1
                               :n @n
                               :grad (/ @grad 100)
                               :colorh1 100
                               :colorh2 220
                               :tw @twist}]
             (merge initial-data (spiro-graph-eager initial-data))))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.

  (q/fill 0)
  (q/text "spirograph-04-5" 10 20)
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
            alfa1 (+ skew1  a)
            alfa2 (+ skew2  a)
            x1 (* (:inner-r out) (Math/pow (q/cos alfa1) 7))
            y1 (* (:inner-r out) (Math/pow (q/sin alfa1) 7))
            x2 (* (:outer-r out) (q/cos alfa2))
            y2 (* (:outer-r out) (q/sin alfa2))
            xc (+ (/ (- x2 x1) 2) x1)
            yc (+ (/ (- y2 y1) 2) y1)
            rx (Math/hypot (- x2 x1) (- y2 y1))
            ry (:tw out)
            ]

        ;(q/stroke 0)
        (cond
         (odd? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 145 190) 90 75) ;gama de verdes-turquesas
                      (q/stroke (:colorh1 out) 50 80)
                      ;(q/stroke 360 100 100)  ;rojo
         (even? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 70 115) 71 98) ;gama de amarillos-verdosos
                       (q/stroke (:colorh2 out) 50 80)
                      ;(q/stroke 0) ;negro
        )

        (q/no-fill)
        (q/with-translation [xc yc]
          (if
           (= (/ (- x2 x1) rx) 1)
            (q/rotate 0)
            (if (> y2 y1)
              (q/rotate  (Math/acos  (/ (- x2 x1) rx)))
              (q/rotate  (- 0 (Math/acos  (/ (- x2 x1) rx))))))
        (q/arc 0 0 rx ry 0 q/PI))

        )
    )))


(q/defsketch spirograph
   :host "canvas"
   ;:size [1000 1000]
   :size [700 700]
   :setup setup
   :draw draw
   :key-pressed key-pressed)
