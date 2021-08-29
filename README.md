readline
========

![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/edadma/readline?include_prereleases) ![GitHub (Pre-)Release Date](https://img.shields.io/github/release-date-pre/edadma/readline) ![GitHub last commit](https://img.shields.io/github/last-commit/edadma/readline) ![GitHub](https://img.shields.io/github/license/edadma/readline)

*readline* provides Scala Native bindings for the [GNU Readline Library](https://tiswww.cwru.edu/php/chet/readline/rltop.html).

Overview
--------

The goal of this project is to provide an easy-to-use Scala Native facade for the portion of the library that of often used to create REPL's.  An example REPL application is provided below that just echos what is typed, however the example also shows how to use *readline*'s history feature including not adding duplicates to the history. 

The more "programmer friendly" part of this library is found in the `io.github.edadma.readline.facade` package.  That's the only package you need to import from, as seen in the examples below.  The other package in the library is `io.github.edadma.readline.extern` which provides for interaction with the Readline C library using Scala Native interoperability elements from the so-call `unsafe` namespace.  There are no public declarations in the `io.github.edadma.readline.facade` package that use `unsafe` types in their parameter or return types, making it a pure Scala facade.  Consequently, you never have to worry about memory allocation or type conversions.

Usage
-----

To use this library, `libreadline-dev` needs to be installed:

```shell
sudo apt install libreadline-dev
```

Include the following in your `build.sbt`:

```scala
resolvers += Resolver.githubPackages("edadma")

libraryDependencies += "io.github.edadma" %%% "readline" % "0.1.2"
```

Use the following `import` in your code:

```scala
import io.github.edadma.readline.facade._
```

Example
-------

This example provides the skeleton for a REPL with history support, including making sure duplicates don't get added to the history.

```scala
import io.github.edadma.readline.facade._

import scala.annotation.tailrec

import Console._

object Main extends App {

  val homeDir       = System.getProperty("user.home")
  val HISTORY_FILE  = s"$homeDir/.my_readline_history"
  var historyExists = read_history(HISTORY_FILE)

  @tailrec
  def repl(): Unit = {
    val prompt = s"${CYAN}type something$RESET> "
    val line   = readline(prompt)

    if (line != null) {
      val s = line.trim

      if (s nonEmpty) {
        println(s"type typed '$s'")

        // do something with 's'

        val prev = history_get(history_base + history_length - 1)

        if (prev == null || prev != s) {
          add_history(s) // only add to history if it's not a duplicate of the previous item

          if (historyExists == 0)
            append_history(1, HISTORY_FILE)
          else {
            historyExists = 0
            write_history(HISTORY_FILE)
          }
        }
      }

      repl()
    }
  }

  repl()

}
```

Readline C library documentation
--------------------------------

The official documentation for the Readline library can be found at [readline](https://tiswww.cwru.edu/php/chet/readline/readline.html#SEC23). The official documentation for the history library can be found at [history](https://tiswww.cwru.edu/php/chet/readline/history.html#SEC6).

License
-------

[LGPL-3.0](https://github.com/edadma/readline/blob/main/LICENSE)
