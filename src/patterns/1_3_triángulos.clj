(ns patterns.1_3_triangulos
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]
            [plumbing.core :as p]
            [plumbing.graph :as g]))



(defn key-pressed []
  ;(println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "01_3_triangulos-####.png"))) ;1

(def draw-triang-sin
  {:h (p/fnk [l] (* l (q/sin (q/radians 60))))
   :x1 (p/fnk [x l] (- x (/ l 2)))
   :y1 (p/fnk [y h] (- y (/ h 3)))
   :x2 (p/fnk [x] x)
   :y2 (p/fnk [y h] (+ y (/ (* 2 h) 3)))
   :x3 (p/fnk [x l] (+ x (/ l  2)))
   :y3 (p/fnk [y h] (- y (/ h 3)))
   :step (p/fnk [l] (/ l 36))
   :n (p/fnk [l step] (+ 1 (/ l step)))
   :xs (p/fnk [l step] (s/range-incl 0 l step))
   :rads (p/fnk [n] (map #(* 10 (q/radians %)) (range n)))
   :ys (p/fnk [rads] (map q/sin rads))
   :scaled-ys (p/fnk [ys] (map #(* 10 %) ys))
   :line-args (p/fnk [xs scaled-ys] (d/line-join-points xs scaled-ys))
   })

(def draw-triang-sin-eager (g/compile draw-triang-sin))

(def out )

(defn dibuja-triangulo-equilatero [x y l]

    (q/with-translation [x1 y1]
      (q/rotate (q/radians 60))
      (dorun (map #(apply q/line %) line-args)))
    (q/with-translation [x2 y2]
      (q/rotate (q/radians 300))
      (dorun (map #(apply q/line %) line-args)))
    (q/with-translation [x3 y3]
      (q/rotate (q/radians 180))
      (dorun (map #(apply q/line %) line-args))))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth))

(defn draw []
  (q/background 160 80 100)
  (q/stroke 360)
  (q/stroke-weight 2)
  (q/no-fill)

  (let [w (q/width)
        h (q/height)
        nx 100
        ny (* nx (q/sin (/ q/PI 3)))
        l (if (= 0 (q/mouse-x)) 0.01 (q/map-range (q/mouse-x) 0 w 0 300))
        a (* l (q/sin (q/radians 60)))]
    (doseq [x (s/range-incl 0 w nx)
            [index y] (s/indexed-range-incl 0 h ny)]
        (cond
         (odd? index)  (do
                         (q/with-translation [x y]
                           (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
                           (dibuja-triangulo-equilatero 0 0 l)))
         (even? index)  (do
                          (q/with-translation [(+ x (/ nx 2)) y]
                           (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
                           (dibuja-triangulo-equilatero 0 0 l))))))
  (q/fill 0)
  (q/text "1.2.triángulos" 10 20)
  (q/text "mouse-x" 10 40)
  (q/text-num (q/mouse-x) 80 40)
  (q/text "mouse-y" 10 60)
  (q/text-num (q/mouse-y) 80 60))




(q/defsketch triangulos
  :size [700 700]
  :setup setup
  :draw draw
  :key-pressed key-pressed)
