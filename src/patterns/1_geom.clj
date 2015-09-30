(ns patterns.1_geom
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]
            [thi.ng.geom.types :as t]
            [thi.ng.geom.rect :as r]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.line :as l]
            [thi.ng.geom.triangle :as tr]
            [thi.ng.geom.core.utils :as u]
            [thi.ng.geom.core.vector :as v]
            [thi.ng.math.core :as m]
            [plumbing.core :as p]
            [plumbing.graph :as gr]

            ))

(def a2 (v/vec2 0 1))
a2
(:x a2)
(def b2 (assoc a2 :x 1))
b2
(def c2 (v/vec2 1 0))
(def d2 (v/vec2 3 0))

(g/+ a2 b2)

(g/dist c2 d2)



(def l1 (l/line2 0 0 100 100))
(def r1 (r/rect 100 100 90 90))
(def r2 (r/rect 40 50 50 50))
(def r3 (r/union r1 r2))

(g/area r1)
(g/intersect-line r2 l1)

(def r4 (if (g/intersection r1 r2) (g/intersection r1 r2) r1))

(def t1 (tr/triangle2 [0 0][50 100][100 0]))
;; Otras dos formas de dar los argumentos del triángulo:
(tr/triangle2 [[0 0] [50 100] [100 0]])
(tr/triangle2 {:a [0 0] :b [50 100] :c [100 0]})

(def t2 (tr/equilateral2 [[50 50] [100 50]])) ;se le da el lado a partir de dos puntos



;;****************************
(p/defnk rect [p size]
  (let [[x y] p
        [w h] size ]
  (q/rect x y w h)))

(p/defnk line [points]
  (let [[[a b][c d]] points]
    (q/line a b c d)))

(p/defnk triangle [points]
    (let [[[a b][c d][e f]] points]
      (q/triangle a b c d e f)))

;;****************************
(defn setup []

  )


(defn draw []
  (q/background 255)
  (q/stroke 0)
  (line l1)
  (q/no-stroke)
  (q/fill 255 0 0 30)
  (rect r1)
  (q/fill 255 0 0 30)
  (rect r2)
  (q/fill 255 0 0 30)
  (rect r3)
  (q/fill 255 0 255 30)
  (rect r4)
  (triangle t1)
  (triangle t2)

  )

(q/defsketch geom
  :size [200 200]
  :setup setup
  :draw draw)
