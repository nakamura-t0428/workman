package workman.api.json

import spray.json.DefaultJsonProtocol
import workman.data.dto._
import workman.api.json.response._
import workman.api.json.request._

/**
 * Json変換サポートオブジェクト
 * 
 * 利用方法は以下に詳しい
 * http://spray.io/documentation/1.1.2/spray-httpx/spray-json-support/
 */
object JsonSupport extends DefaultJsonProtocol{
  import spray.json._
  
  implicit val invitationFormatter = jsonFormat1(Invitation.apply)
  implicit val resultMessageFormatter = jsonFormat3(ResultMessage.apply)
  implicit val limitFormatter = jsonFormat2(Limit.apply)
}
