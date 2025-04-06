package slackbot

import cats.effect.unsafe.implicits.global

class MySuite extends munit.FunSuite {
    var token: String = sys.env("SLACK_BOT_TOKEN")
    var channelID: String = sys.env("SLACK_CHANNEL_ID")
    var testSlackBot: SlackClient = SlackClient(token)

    // test for sending and receiving messages correctly
    test("sendMessage") {
        var sentMessage: String = "Test Message"
        testSlackBot.sendMessage(channelID, sentMessage).unsafeRunSync().getOrElse("bad send")
        var receivedMessage: String = testSlackBot.getLastMessage(channelID).unsafeRunSync().getOrElse("bad receive")
        assertEquals(sentMessage, receivedMessage)
    }

    test("parseCommand") {
        var testCmdParser = CommandParser
        var inputCmd: String = "/gcc -S test.c"
        var parsedCmd: (String, List[String]) = testCmdParser.parse(inputCmd).get
        var desiredOutput: (String, List[String]) = ("/gcc", List("-S", "test.c"))
        assertEquals(parsedCmd, desiredOutput)
    }

}
