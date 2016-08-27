package com.loadtester.api.json

import spray.json.DefaultJsonProtocol
import com.loadtester.data.dto._
import com.loadtester.api.json.response._
import com.loadtester.api.json.request._

object ProjectJsonSupport extends DefaultJsonProtocol {
  import spray.json._
  
  implicit val projectRegisterFormatter = jsonFormat1(ProjectRegister)
  implicit val projectRegisterResponseFormatter = jsonFormat2(ProjectInfoResponse)
  implicit val memberResponseFormatter = jsonFormat2(MemberInfoResp)
  implicit val projectDetailFormatter = jsonFormat3(ProjectDetailResp)
  implicit object ProjectListFormatter extends RootJsonFormat[ProjectList] {
    def read(value: JsValue) = ProjectList(value.convertTo[List[ProjectInfoResponse]])
    def write(obj: ProjectList) = obj.datas.toJson
  }
}
