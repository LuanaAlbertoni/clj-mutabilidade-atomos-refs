(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

;simbolo que qualquer thread que acessar esse namespace vai ter acesso a ele como "luana"
;bandeado de uma forma global
(def nome "luana")

;simbolo redefinido (refeito o binding)
(def nome "redefinido")


(let [nome "luana"]
  ;acao 1
  ;acao 2
  ;(println nome)
  ;nao eh refeito o binding do simbolo local
  ;criando um novo simbolo local ao bloco e escondendo o anterior: SHADOWING
  (let [nome "iara"]
    ;acao 3
    ;acao 4
    ;(println nome)
    )
  ;(println nome)
  )

(defn testa-atomo []
  (let [hospital-albertoni (atom { :espera h.model/fila_vazia})]
    (println hospital-albertoni)
    (pprint hospital-albertoni)
    ;(pprint (deref hospital-albertoni))
    ; @ eh um atalho para deref
    (pprint @hospital-albertoni)

    ;assoc nao altera o conteudo dentro de um atomo!
    (println "assoc:")
    (pprint (assoc @hospital-albertoni :laboratorio1 h.model/fila_vazia))
    (println "\nhospital apenas fazendo assoc")
    (pprint @hospital-albertoni)

    ;uma das maneiras de alterar conteudo dentro do atomo
    (swap! hospital-albertoni assoc :laboratorio1 h.model/fila_vazia)
    (println "\nhospital com swap")
    (pprint @hospital-albertoni)

    (swap! hospital-albertoni assoc :laboratorio2 h.model/fila_vazia)
    (println "\nhospital com swap adicionando lab2")
    (pprint @hospital-albertoni)

    ;update tradicional imutavel, com dereferencia que nao trara efeito
    (update @hospital-albertoni :laboratiorio1 conj "111")

    (swap! hospital-albertoni update :laboratorio1 conj "111")
    (println "\nhospital com swap update lab1")
    (pprint @hospital-albertoni)))
;(testa-atomo)


(defn chega-em-malvado! [hospital pessoa]
  ;swap tentativas e retentativas quando existe execucoes consecutivas
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo)


(defn chega-sem-malvado! [hospital pessoa]
  ;swap tentativas e retentativas quando existe execucoes consecutivas
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo)



















