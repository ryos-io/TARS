![TARS](http://www.moo.io/img/tars2.jpg)
===

<img src="https://travis-ci.org/mooito/tars.svg" /> [![API Documentation](http://b.repl.ca/v1/doc-API-blue.png)](http://www.moo.io/tars/doc/) 

TARS is a framework, which provides a command-line interface to interact with users of your applications like  CLI clients e.g mongo, mysql, etc. TARS provides a baseline functionality of a CLI.  It even understands a few commands like "help" and "quit". You only need to extend it to make TARS understand your custom commands.

+ [API Doc](http://www.moo.io/tars/doc/)
+ Twitter: [@ebagdemir](https://twitter.com/ebagdemir)
+ [GitHub Issues](https://github.com/mooito/tars/issues)

[![Clojars Project](http://clojars.org/io.moo/tars/latest-version.svg)](http://clojars.org/io.moo/tars)

How to use
---

To add the CLI into your application just add the dependency and the define the main function.

```
(defproject your-app "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [io.moo/tars "0.1.0"]]
  :main io.moo.tars.container)
```

After you run your application by calling:
```
lein run
```
the CLI will be available for user interaction with a default MOTD and prompt. You can override this settings and customize them in your applications.

```
        .
       _|_
/\/\  (. .)
`||'   |#|
 ||__.-"-"-.___
 `---| . . |--.\     TARS version 0.1.0 [ Type 'help' to get help! ]
     | : : |  |_|    https://github.com/mooito/tars
     `..-..' ( I )
      || ||   | |
      || ||   |_|
     |__|__|  (.)
tars>
```
Out of the box, TARS provide two commands, that are "help" and "quit". You can now extend the TARS to understand your commands.


How to customize
---

You can override the MOTD by creating a new branding file under "~/.tars/branding" and also the prompt by adding a configuration file in "~/.tars/config.clj". The configuration file will be loaded while the CLI starts. To override the prompt settings, just add a new definition for the prompt:

```
(def config {:prompt "tars"})
```
How to extend
---
To add your own commands just define new methods using defmethod:

```
(ns test-prj.core
  (:gen-class)
  (:require [io.moo.tars.container :as c])
  (:require [io.moo.tars.commands :as co])
  (:require [io.moo.tars.rendering :as r]))

(defmethod co/on-start "test" [ params ])
(defmethod co/on-error "test" [ params ]
  (r/prints "test failed!" println))
(defmethod co/exec "test executing" [ commands params ])
(defmethod co/on-complete "test" [ params ]
  (r/prints "\ntest completed!\n" print)
  (:console-action (get co/command-map "test")))

(defn -main [ & args ]
    (c/start-repl))
```
