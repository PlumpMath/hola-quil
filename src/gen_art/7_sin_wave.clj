(ns quil-sketches.gen-art.08-sine-save
  (:require [quil.core :refer :all]
            [quil.helpers.drawing :refer [line-join-points]]
            [quil.helpers.seqs :refer [range-incl]]
            [quil.helpers.calc :refer [mul-add]]))

;; Example 8 - Sine Wave
;; Taken from Listing 3.2, p60

;; void setup() {
;;  size(500, 100);
;;  background(255);
;;  strokeWeight(5);
;;  smooth();
;;  stroke(0, 30);
;;  line(20, 50, 480, 50);
;;  stroke(20, 50, 70);

;;  float xstep = 1;
;;  float lastx = -999;
;;  float lasty = -999;
;;  float angle = 0;
;;  float y = 50;
;;  for(int x=20; x<=480; x+=xstep){
;;    float rad = radians(angle);
;;    y = 50 + (sin(rad) * 40);
;;    if(lastx > -999) {
;;      line(x, y, lastx, lasty);
;;    }
;;    lastx = x;
;;    lasty = y;
;;    angle++;
;;  }
;; }


(defn setup []
  (background 255)
  (stroke-weight 5)
  (smooth)
  (stroke 0 30)
  (line 20 50 480 50)
  (stroke 20 50 70)

  (let [xs        (range-incl 20 480 (/ (/ 460 360) 2)) ;; Dividiendo 460 (480-20) entre 360 obligo a que la curva
                                                  ;; haga una onda completa. Si divido por n, entran n curvas sinusoidales
        rads      (map radians (range))
        ys        (map sin rads)
        ;ys        (map #(pow % 3) ys) ;; opcional
        scaled-ys (mul-add ys 40 50)
        line-args (line-join-points xs scaled-ys)]
    (dorun (map #(apply line %) line-args))))

(defsketch gen-art-8
  :title "Sine Wave"
  :setup setup
  :size [500 100])

(defn -main [& args])
