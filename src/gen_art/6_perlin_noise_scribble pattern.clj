(ns gen_art.6_perlin_noise_scribble
  (:require [quil.core :refer :all]
            [quil.helpers.drawing :refer [line-join-points]]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq]]
            [quil.helpers.calc :refer [mul-add]]))

;; Example 7 - Perlin Noise Scribblea
;; Taken from Listing 3.1, p59

;; void setup() {
;;  size(500, 100);
;;  background(255);
;;  strokeWeight(5);
;;  smooth();

;;  stroke(0, 30);
;;  line(20, 50, 480, 50);

;;  stroke(20, 50, 70);
;;  int step = 10;
;;  float lastx = -999;
;;  float lasty = -999;
;;  float ynoise = random(10);
;;  float y;
;;  for (int x = 20 ; x <= 480 ; x += step){
;;    y = 10 + noise(ynoise) * 80;
;;    if(lastx > -999) {
;;      line(x, y, lastx, lasty);
;;    }
;;    lastx = x;
;;    lasty =y;
;;    ynoise += 0.1;
;;  }
;; }

(defn setup []
  (background 255)
  (stroke-weight 2)
  (smooth)
  (color-mode :hsb 360 100 100)

  ;;(stroke 0 30)
  ;; (line 20 50 480 50)


  (doseq [y-add (range-incl 0 (width) 20)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.1
          y-mul     40
          border-x  0
          xs        (range-incl border-x (- (width) border-x) step)
          ys        (perlin-noise-seq seed seed-incr)
          scaled-ys (mul-add ys y-mul y-add)
          line-args (line-join-points xs scaled-ys)]
      (stroke 180 40 90)
      (dorun (map #(apply line %) line-args))))

  (doseq [x-add (range-incl 0 (height) 20)]
    (let [step  10
          seed (rand 10)
          seed-incr 0.1
          x-mul     40
          border-y  0
          xs        (perlin-noise-seq seed seed-incr)
          ys        (range-incl border-y (- (width) border-y) step)
          scaled-xs (mul-add xs x-mul x-add)
          line-args (line-join-points  scaled-xs ys)]
      (stroke 10 100 100)
      (dorun (map #(apply line %) line-args)))))

(defsketch gen-art-7
  :title "Perlin Noise Scribble"
  :setup setup
  :size [700 700])

 (defn -main [& args])
