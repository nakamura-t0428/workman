package com.loadtester.api.json

import spray.json.DefaultJsonProtocol
import com.loadtester.data.dto._
import com.loadtester.api.json.response._
import com.loadtester.api.json.request._

object UserJsonSupport extends DefaultJsonProtocol  {
  import spray.json._
  
  implicit val userAuthFormatter = jsonFormat2(UserAuth)
  implicit val userRegisterFormatter = jsonFormat3(UserRegister)
  implicit val userAuthResponceFormatter = jsonFormat4(UserAuthResponse)
  implicit val myInfoRespFormatter = jsonFormat4(MyInfoResp)
}