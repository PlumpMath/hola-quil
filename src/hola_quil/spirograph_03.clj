;; Cuando lo lanzo me abre dos ventanas. ¿Por qué?

(ns hola-quil.spirograph-03
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))

(defn key-pressed []
  (cond
   (= 37 (q/key-code)) (q/save-frame "spirograph-####.png")))

(def spiro-graph
  {:w (p/fnk [wx] wx)
   :h (p/fnk [hx] hx)
   :sep-borde (p/fnk [] 30)
   :half-w (p/fnk [w] (/ w 2))
   :half-h (p/fnk [h] (/ h 2))
   :outer-r (p/fnk [half-w sep-borde] (- half-w sep-borde))
   :r (p/fnk [rx] rx)
   :inner-r (p/fnk [outer-r r] (* outer-r r))
   :str-w (p/fnk [sw] sw)
   :n (p/fnk [nx] nx)
   :grad (p/fnk [gr] gr)
   :colorh1 (p/fnk [h1] h1)
   :colorh2 (p/fnk [h2] h2)
   }
  )

(def spiro-graph-eager (g/compile spiro-graph))


(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb 360 100 100))


(defn draw []
  (q/background 360)


  (def out (spiro-graph-eager {:wx (q/width)
                               :hx (q/height)
                               :rx 0.5
                               :sw 1
                               :nx 300
                               :gr 207.1923
                               :h1 180
                               :h2 40}))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.

  (q/fill 0)
  (q/text "spirograph-03" 10 20)
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
      (let [skew1 (* (:grad out) a)
            skew2 (* skew1 2)]
        ;(q/stroke 0)
        (cond
         (odd? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 145 190) 90 75) ;gama de verdes-turquesas
                      (q/stroke (:colorh1 out) 100 80)
                      ;(q/stroke 360 100 100)  ;rojo
         (even? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 70 115) 71 98) ;gama de amarillos-verdosos
                       (q/stroke (:colorh2 out) 70 100)
                      ;(q/stroke 0) ;negro
        )
      (q/line   (* (:inner-r out) (q/cos (+ skew1 a)))
                  (* (:inner-r out) (q/sin (+ skew1 a)))
                  (* (:outer-r out) (q/cos (+ skew2 a)))
                  (* (:outer-r out) (q/sin (+ skew2 a))))
                )

    )))


(q/defsketch spirograph
   :host "canvas"
   :size [1000 1000]
  ; :size [700 700]
   :setup setup
   :draw draw
   :key-pressed key-pressed)
