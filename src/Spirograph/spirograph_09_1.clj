;; He convertido las circunferencias en elipses



(ns Spirograph.spirograph_09_1
  (:require [quil.core :as q :include-macros true]
            [quil.helpers.seqs :as hs]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))

(def n (atom 1))
(def grad (atom 00))


(defn key-pressed []
  ;(println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "09-1-spirograph-####.png") ;1
   (= 39 (q/key-code)) (swap! n inc) ;RIGHT
   (= 37 (q/key-code)) (swap! n dec) ;LEFT
   (= 38 (q/key-code)) (swap! grad inc) ;UP
   (= 40 (q/key-code)) (swap! grad dec) ;DOWN

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
  (q/color-mode :hsb 360 100 100)
  (let [color1 (hs/cycle-between 30 100 0.01)
        color2 (hs/cycle-between 290 360 0.1)]
    (q/set-state! :color1 (hs/seq->stream color1)
                  :color2 (hs/seq->stream color2))))


(defn draw []
  (q/fill 0 30)
  (q/rect 0 0 (:w out) (:h out))
  (q/stroke 0)

  ;(dorun (map q/point (range 0 700 10)  (range 0 700 10)))

  (def out (let [initial-data {:w (q/width)
                               :h (q/height)
                               :r 0.5
                               :sep-borde 50
                               :str-w 3
                               :sw 1
                               :n  @n
                               :grad (/ @grad 100)
                               :colorh1 100
                               :colorh2 200
                               }]
             (merge initial-data (spiro-graph-ini-eager initial-data))))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.

  (q/fill 250)
  (q/text "spirograph-09-1" 10 20)
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

    ;(q/no-fill)
    ;(q/rotate q/HALF-PI)
    ;(q/arc 0 0 200 50 0 q/PI)

    (doseq [[index a] (map vector (iterate inc 0) (range 0 q/TWO-PI (/ q/PI (:n out))))]
      (let [skew1 (* (:grad out) a) ;; --> Meter skew en spirograph???
            skew2 (* skew1 2)
            alfa1 (+ skew1 a)
            alfa2 (+ skew2 a)
            x1 (* (:inner-r out) (q/cos alfa1)) ;; elipse: x = h + a*cos alfa1
            y1 (* (:inner-r out) (q/sin alfa1)) ;; elipse: y = k + b*cos alfa1
            x2 (* (:outer-r out) (q/cos alfa2))
            y2 (* (:outer-r out) (q/sin alfa2))
            nstep 40
            xstep (/ (- x2 x1) nstep)
            ystep (/ (- y2 y1) nstep)
            xs (take nstep (hs/steps x1 xstep))
            ys (take nstep (hs/steps y1 ystep))
            ]

        ;(q/stroke 0)
        (cond
         (odd? index)   ;(q/stroke (:colorh1 out) 50 100)
                        ;(q/stroke (q/map-range a 0 q/TWO-PI 00 100) 50 (q/map-range a 0 q/TWO-PI 20 100) ) ;gama de verdes-turquesas
                        ;(q/stroke 360 100 100)  ;rojo
                        (q/stroke ((q/state :color1)) 60 ((q/state :color1)))

         (even? index)   ;(q/stroke (:colorh2 out) 50 100)
                         ;(q/stroke (q/map-range a 0 q/TWO-PI 260 360) 50 (q/map-range a 0 q/TWO-PI 60 100) )
                         ;(q/stroke 0) ;negro
                         (q/stroke ((q/state :color2)) 60 ((q/state :color1)))
                         )

        (q/no-fill)
        ;(q/line x1 y1 x2 y2)
        (dorun (map q/point xs ys))


        )
    )))

(q/defsketch spirograph
   :host "canvas"
   ;:size [1000 1000]
   :size [700 700]
   :setup setup
   :draw draw
   :key-pressed key-pressed)
