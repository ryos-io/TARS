; The MIT License (MIT)
; 
; Copyright (c) 2014 mooito.org - Erhan Bagdemir
; 
; Permission is hereby granted, free of charge, to any person obtaining a copy
; of this software and associated documentation files (the "Software"), to deal
; in the Software without restriction, including without limitation the rights
; to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
; copies of the Software, and to permit persons to whom the Software is
; furnished to do so, subject to the following conditions:
; 
; The above copyright notice and this permission notice shall be included in
; all copies or substantial portions of the Software.
; 
; THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
; IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
; FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
; AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
; LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
; OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
; THE SOFTWARE.

(ns org.mooito.moo.console
  (:gen-class)
  (:use org.mooito.moo.commands)
  (:require clojure.string)
  (:import [org.mooito.moo.commands CommandTemplate]))

;; Macro definition of infinite loop for REPL. 
(defmacro forever [ & body ]
  `(while true ~@body))

;; Prints message of the day on start-up.
(defn print-motd []
  (print "
           (    )
            (oo)
   )\\.-----/(O O)
  # ;       / u
    (  .   |} )
     |/ `.;|/;     Moo version 0.0.1 [ Type 'help' to get help! ]
     \"     \" \"     https://github.com/mooito/moo

")
  (flush))

(defn split-parameters 
  "Split parameters in form of command and parameters"
  [input]
  (if (not (clojure.string/blank? input))
    (clojure.string/split input #"\s" 2)
    ""))

(defn print-prompt 
  "Prints the command prompt."
  []
  (print "moo> ")
  (flush))

;; REPL implementation.
(defn repl
  "Read-Eval-Print-Loop implementation"
  []
  (print-motd)
  (loop []
    (print-prompt)
    (let [ user-input (read-line), input-token (split-parameters user-input)]
      (if (or 
           (and 
            (not 
             (clojure.string/blank? user-input))
            (not= 
             (perform 
              (CommandTemplate.) 
              (first input-token) 
              (get input-token 1)) 
             :TERMINATE))
           (clojure.string/blank? user-input))
        (recur)))))
