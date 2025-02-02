(ns hospital.aula5
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

;swap tentativas e retentativas quando existe execucoes consecutivas
(defn chega-em! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))


(defn transfere! [hospital de para]
  (swap! hospital h.logic/transfere de para))


(defn simula-dia []
  (let [hospital (atom (h.model/novo-hospital))]
    (chega-em! hospital "joao")
    (chega-em! hospital "luana")
    (chega-em! hospital "iara")
    (chega-em! hospital "taila")
    (transfere! hospital :espera :laboratorio1)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :laboratorio2 :laboratorio3)
    (pprint hospital)
    ))

(simula-dia)


