package slackbot

object CommandParser {
    def parse(input: String): Option[(String, List[String])] = {
        val parts = input.trim.split(" ").toList
        parts match {
            // commands should start with slashes but for this basic test purpose just the first word is the 'command'
            case command :: args => Some((command, args))
            case _ => None
        }
    }
}