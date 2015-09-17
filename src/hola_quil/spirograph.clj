(ns hola-quil.spirograph
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))



(def spiro-graph
  {:w (p/fnk [wx] wx)
   :h (p/fnk [hx] hx)
   :sep-borde (p/fnk [] 30)
   :half-w (p/fnk [w] (/ w 2))
   :half-h (p/fnk [h] (/ h 2))
   :outer-r (p/fnk [half-w sep-borde] (- half-w sep-borde))
   :inner-r (p/fnk [outer-r] (* outer-r 0.5))
   :str-w (p/fnk [sw] sw)

   }
  )

(def spiro-graph-eager (g/compile spiro-graph))






(defn setup []
  (q/frame-rate 60))


(defn draw []
  (q/background 255)


  (def out (spiro-graph-eager {:wx (q/width)
                               :hx (q/height)

                               :sw 1}))

  ;; q/width y q/height no tienen valor hasta que size es llamado.
  ;; Si no los pongo dentro del draw no funcionan.


  (q/stroke 0)
  (q/stroke-weight (:str-w out))




  (q/ellipse (:half-w out) (:half-h out) (* 2 (:outer-r out)) (* 2 (:outer-r out)))

  )



*ns*






(q/defsketch spirograph
   :host "canvas"
   :size [500 500]
   :setup setup
   :draw draw)
