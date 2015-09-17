(ns hola-quil.spirograph
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [plumbing.core :as p]
            [plumbing.graph :as g]
            [schema.core :as s]
  ))


(defn stats
  "Take a map {:xs xs} and return a map of simple statistics on xs"
  [{:keys [xs] :as m}]
  (assert (contains? m :xs))
  (let [n  (count xs)
        m  (/ (p/sum identity xs) n)
        m2 (/ (p/sum #(* % %) xs) n)
        v  (- m2 (* m m))]
    {:n n   ; count
     :m m   ; mean
     :m2 m2 ; mean-square
     :v v   ; variance
     }))


(stats {:xs [4 6 5 3 7]})

(def stats-graph
  "A graph specifying the same computation as 'stats'"
  {:n  (p/fnk [xs]   (count xs))
   :m  (p/fnk [xs n] (/ (p/sum identity xs) n))
   :m2 (p/fnk [xs n] (/ (p/sum #(* % %) xs) n))
   :v  (p/fnk [m m2] (- m2 (* m m)))})

(def stats-eager (g/compile stats-graph))

(stats-eager {:xs [4 6 5 3 7]})




#_(defn setup []
  (q/frame-rate 60))


#_(defn draw []
   (q/background 255)
  )










#_(q/defsketch spirograph
   :host "canvas"
   :size [500 500]
   :setup setup
   :draw draw)
