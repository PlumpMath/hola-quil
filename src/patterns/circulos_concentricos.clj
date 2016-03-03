(ns patterns.circulos_concentricos
  (:require [quil.core :as q :include-macros true]
            [quil.helpers.seqs :as s]))

(defn key-pressed []
  (cond
   (= 49 (q/key-code)) (q/save-frame "001_circulos-concentricos-####.png")))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth)
  (q/no-fill)

  )


(defn draw []
  (q/background 160 80 100)
  (q/stroke 360)
  (q/stroke-weight 10)


 (doseq [diams (range 0 1000 40)]
    (let [x (/ (q/height) 2)
          y (/ (q/width) 2)]

    (q/ellipse x y diams diams)))

)



(q/defsketch circulos
  :size [1000 1000]
  :setup setup
  :draw draw
  :key-pressed key-pressed)







