package workman.api.json

import spray.json.DefaultJsonProtocol
import workman.data.dto._
import workman.api.json.response._
import workman.api.json.request._

object UserJsonSupport extends DefaultJsonProtocol  {
  import spray.json._
  
  implicit val userAuthFormatter = jsonFormat2(UserAuth.apply)
  implicit val userRegisterFormatter = jsonFormat3(UserRegister.apply)
  implicit val userAuthResponceFormatter = jsonFormat4(UserAuthResp.apply)
  implicit val myInfoRespFormatter = jsonFormat4(MyInfoResp.apply)
}