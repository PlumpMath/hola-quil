(ns gen_art.1_growing_circle
  (:require [quil.core :refer :all]
            [quil.helpers.seqs :refer [seq->stream range-incl]]))

;; Example 2 - Growing Circle
;; Taken from Listing 2.1, p28

;; int diam = 10;
;; float centX, centY;

;; void setup() {
;;   size(500, 300);
;;   frameRate(24);
;;   smooth();
;;   background(180);
;;   centX = width/2;
;;   centY = height/2;
;;   stroke(0);
;;   strokeWeight(5);
;;   fill(255, 50);
;; }

;; void draw() {
;;   if(diam <= 400) {
;;     background(180);
;;     ellipse(centX, centY, diam, diam);
;;     diam += 10;
;;   }
;; }

(defn setup []
  (frame-rate 24)
  (smooth)
  (background 180)
  (stroke 0)
  (stroke-weight 5)
  (fill 255 25)
  (let [diams (range-incl 10 400 10)] ;; define todos los diámetros que va a tener el círculo
    (set-state! :diam (seq->stream diams) ;; set-state! Define un estado específico del sketch. Es llamado una
                                          ;; única vez en setup.
                                          ;; seq->stream convierte una sequence en un stream. Cada vez que el
                                          ;; stream es invocado devuelve el siguiente valor
                                          ;; Esto sirve para crecer. Qué pasa si queremos decrecer?
                :cent-x (/ (width) 2)
                :cent-y (/ (height) 2))))

(defn draw []
  (let [cent-x (state :cent-x) ;; Nos devuelve el estado almacenado en set-state! llamándolo por una clave
        cent-y (state :cent-y)
        diam   ((state :diam))] ;; En este caso, cada vez que llamamos :diam, como es un stream, nos da el siguiente
                                ;; valor de la sequence original
    (when diam                  ;; Si diam es true es porque no hemos llegado al final del stream
      (background 180)
      (ellipse cent-x cent-y diam diam))))

(defsketch growing_circle
  :title "Growing circle 1"
  :setup setup
  :draw draw
  :size [500 300]
  :keep-on-top true ;;Sketch window will always be above other windows
  ;:features [:keep-on-top :present] ;; :present Para pantalla completa
)

 (defn -main [& args])
