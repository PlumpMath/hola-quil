(ns hola-quil.spirograph
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
   :inner-r (p/fnk [outer-r] (* outer-r 0.5))
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
                               :sw 1
                               :nx 350
                               :gr 74.5
                               :h1 155
                               :h2 5}))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.

  (q/fill 0)
  (q/text "spirograph" 10 20)
  (q/text-num (:str-w out) 10 40)
  (q/text-num (:n out) 10 60)
  (q/text-num (:grad out) 10 80)
  (q/stroke 0)
  (q/stroke-weight (:str-w out))
  (q/with-translation [(:half-w out) (:half-h out)]
    (doseq [[index a] (map vector (iterate inc 0) (range 0 q/TWO-PI (/ q/PI (:n out))))]
      (let [skew1 (* (:grad out) a)
            skew2 (* skew1 2.0)]
        (cond
         (odd? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 145 190) 90 75) ;gama de verdes-turquesas
                      (q/stroke (:colorh1 out) 90 85)
                      ;(q/stroke 360 100 100)  ;rojo
         (even? index) ;(q/stroke (q/map-range a 0 q/TWO-PI 70 115) 71 98) ;gama de amarillos-verdosos
                       (q/stroke (:colorh2 out) 90 85)
                      ;(q/stroke 0) ;negro
        )
        (q/line (* (:inner-r out) (q/cos (+ skew1 a)))
                  (* (:inner-r out) (q/sin (+ skew1 a)))
                  (* (:outer-r out) (q/cos (+ skew2 a)))
                  (* (:outer-r out) (q/sin (+ skew2 a))))
    ))))


(q/defsketch spirograph
   :host "canvas"
   :size [500 500]
   :setup setup
   :draw draw
   :key-pressed key-pressed)
