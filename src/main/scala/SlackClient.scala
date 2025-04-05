package slackbot

import sttp.client3._
import sttp.client3.circe._
import io.circe.generic.auto._
import io.circe.Json
import io.circe.syntax._
import cats.effect.IO
import cats.effect.unsafe.implicits.global

class SlackClient(apiToken: String) {
    private val backend = HttpClientSyncBackend()

    def sendMessage(channelID: String, message: String): IO[Either[String, String]] = {
        val request = basicRequest
            .post(uri"https://slack.com/api/chat.postMessage")
            .header("Authorization", s"Bearer $apiToken")
            .body(Map("text"->message, "channel"->channelID).asJson)
            .response(asString)
        IO{
            val response = request.send(backend)
            response.body
        }
    }

    def getLastMessage(channelID: String): IO[Either[String, String]] = {
        val request = basicRequest
            .get(uri"https://slack.com/api/conversations.history?channel=$channelID")
            .header("Authorization", s"Bearer $apiToken")
            .response(asJson[Json])
        IO {
            val response = request.send(backend)
            response.body match {
                case Right(jsonMessages) =>
                    val lastMessage = jsonMessages.hcursor.downField("messages").as[List[Json]].toOption.
                        flatMap(_.headOption).flatMap(_.hcursor.get[String]("text").toOption).getOrElse("No Message Found")
                    Right(lastMessage)
                case Left(error) =>
                    Left(s"Error: $error")
            }
        }
    }
}