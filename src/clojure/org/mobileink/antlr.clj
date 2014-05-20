(ns org.mobileink.antlr
  (:import [org.mobileink.antlr ClojureLexer ClojureParser ClojureBaseListener]
           [org.antlr.v4.runtime ANTLRInputStream ANTLRFileStream CommonTokenStream]
           [org.antlr.v4.runtime.tree ParseTree ParseTreeWalker]))

(defn- make-listener [parser]
    (proxy [ClojureBaseListener] []
      ;; (enterEveryRule [ctx]
      ;;   (do
      ;;     (println "entering rule")
      ;;     ;; (println (format "ctx id: %s" (.Identifier ctx)))
      ;;     ;; (println (format "form: %s" (.form ctx)))
      ;;     ))
      ;; (exitEveryRule [ctx] (println "exiting rule"))
      (enterLet [ctx]
        (do (println "entering let")
            ;; (println (format "tok: %s" (.SYMBOL ctx)))
            ))
      (enterLet_form [ctx]
        (do (println "entering let form")
            ;; (println (format "tok: %s" (.SYMBOL ctx)))
            ))
      (exitLet_form [ctx] (println "exiting let form"))
      (enterList [ctx] (println "exiting List"))
      (exitList [ctx] (println "exiting List"))
      (visitTerminal [ctx]
        (do
          (println "visiting terminal node")
          (println "sym: " (.getText (.getSymbol ctx)))
          ))
      ))

(defn parse-str [str]
  "Parse string 'str using the Clojure parser."
  (print (format "parsing %s\n" str))
  (let [input (do ;;(println "getting stream")
                (ANTLRInputStream. str))
        lexer (do ;;(println "getting lexer")
                  (ClojureLexer. input))
        tokens (do
                 ;;(println "getting tokens")
                 (CommonTokenStream. lexer))
        parser (do
                 ;;(println "getting parser")
                 (ClojureParser. tokens))
        tree (do ;;(println "getting tree")
               (.file parser))] ; "file" is the start rule of the grammar
    (do
      (.walk (ParseTreeWalker.) (make-listener parser) tree))))

(defn parse-file [file]
  "Parse file 'file using the Clojure parser."
  (print (format "parsing %s\n" file))
  (let [input (do ;;(println "getting stream")
                ;; use ANTLRInputStream to read from a string buffer
                (ANTLRFileStream. file))
        lexer (do ;;(println "getting lexer")
                  (ClojureLexer. input))
        tokens (do
                 ;;(println "getting tokens")
                 (CommonTokenStream. lexer))
        parser (do
                 ;;(println "getting parser")
                 (ClojureParser. tokens))
        tree (do ;;(println "getting tree")
               (.file parser))] ; "file" is the start rule of the grammar
    (do
      (.walk (ParseTreeWalker.) (make-listener parser) tree))))
