package slackbot

import cats.effect.unsafe.implicits.global 

// example usage, just shows that parser works, doesn't act on any given command, prints back to slack channel showing command and args
object Main extends App {
    var token: String = sys.env("SLACK_BOT_TOKEN")
    var channelID: String = sys.env("SLACK_CHANNEL_ID")

    val slackBot: SlackClient = SlackClient(token)
    var lastMessage = slackBot.getLastMessage(channelID).unsafeRunSync()
    var cmdParser = CommandParser
    var parsedCmd = cmdParser.parse(lastMessage.getOrElse("Error"))
    println(parsedCmd.get)
    slackBot.sendMessage(channelID, ("Command: " + parsedCmd.get._1)).unsafeRunSync()
    slackBot.sendMessage(channelID, ("Arguments: " + parsedCmd.get._2)).unsafeRunSync()
}