//import xyz.hyperreal.readline.{Readline => rl}
//
//import scala.annotation.tailrec
//import scala.scalanative.unsafe._
//import scala.scalanative.libc.stdlib._
//
//import Console._
//
//object Main extends App {
//  val homeDir = System.getProperty("user.home")
//
//  Zone { implicit z =>
//    val HISTORY_FILE = toCString(s"$homeDir/.my_readline_history")
//
//    var historyExists = rl.read_history(HISTORY_FILE)
//
//    @tailrec
//    def repl(): Unit = {
//      val prompt = s"${CYAN}type something$RESET> "
//      val line   = rl.readline(toCString(prompt))
//
//      if (line != null) {
//        val s = fromCString(line).trim
//
//        free(line)
//
//        if (s nonEmpty) {
//          println(s"type typed '$s'")
//
//          // do something with 's'
//
//          val prev = rl.history_get(rl.history_base + rl.history_length - 1)
//
//          if (prev == null || fromCString(!prev) != s) {
//            rl.add_history(toCString(s))
//
//            if (historyExists == 0)
//              rl.append_history(1, HISTORY_FILE)
//            else {
//              historyExists = 0
//              rl.write_history(HISTORY_FILE)
//            }
//          }
//        }
//
//        repl()
//      }
//    }
//
//    repl()
//  }
//}
