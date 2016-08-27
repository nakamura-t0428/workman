package workman.api.json

import spray.json.DefaultJsonProtocol
import workman.data.dto._
import workman.api.json.response._
import workman.api.json.request._

object UserJsonSupport extends DefaultJsonProtocol  {
  import spray.json._
  
  implicit val userAuthFormatter = jsonFormat2(UserAuth)
  implicit val userRegisterFormatter = jsonFormat3(UserRegister)
  implicit val userAuthResponceFormatter = jsonFormat4(UserAuthResponse)
  implicit val myInfoRespFormatter = jsonFormat4(MyInfoResp)
}