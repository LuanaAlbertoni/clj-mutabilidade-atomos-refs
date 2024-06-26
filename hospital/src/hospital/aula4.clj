(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo-com-mapv
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]]

    ;mapv forca a execucao pois gera um vetor de uma funcao lazy
    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
;(simula-um-dia-em-paralelo-com-mapv)


(defn simula-um-dia-em-paralelo-com-mapv-refatorada
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        start-thread #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]

    (mapv start-thread pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-refatorada)



(defn start-thread
  ([hospital]
   (fn [pessoa] (start-thread hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))



(defn simula-um-dia-em-paralelo-com-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        starta (start-thread hospital)]

    (mapv starta pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-extraida)



(defn start-thread
  [hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))


(defn simula-um-dia-em-paralelo-com-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        starta (partial start-thread hospital)]

    (mapv starta pessoas)
    (.start (Thread. (fn [] (Thread/sleep 5000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-partial)


(defn start-thread
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

;Doseq : Para executar uma determinada sequencia especificada
(defn simula-um-dia-em-paralelo-com-doseq
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]]

    (doseq [pessoa pessoas]
      (start-thread hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 5000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-doseq)

;Dotimes : Para executar um numero N de vezes
(defn simula-um-dia-em-paralelo-com-dotimes
  []
  (let [hospital (atom (h.model/novo-hospital))]

    (dotimes [pessoa 6]
      (start-thread hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))


(simula-um-dia-em-paralelo-com-dotimes)











