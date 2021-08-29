readline
========

Scala Native bindings for the GNU Readline C library.

Here's some skeleton code:

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
