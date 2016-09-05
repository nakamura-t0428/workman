package workman.api.json

import spray.json.DefaultJsonProtocol
import workman.data.dto._
import workman.api.json.response._
import workman.api.json.request._

object ProjectJsonSupport extends DefaultJsonProtocol {
  import spray.json._
  
  implicit val companyRegisterFomatter = jsonFormat2(CompanyRegister)
  implicit val companyRegisterRespFormatter = jsonFormat3(CompanyInfoResp)
  implicit val companySearchFormatter = jsonFormat1(CompanySearch)
  implicit object CompanyListFormatter extends RootJsonFormat[CompanyList] {
    def read(value:JsValue) = CompanyList(value.convertTo[List[CompanyInfoResp]])
    def write(obj:CompanyList) = obj.datas.toJson
  }
  
  implicit val projectRegisterFormatter = jsonFormat3(ProjectRegister)
  implicit val projectRegisterRespFormatter = jsonFormat2(ProjectInfoResponse)
  implicit val memberResponseFormatter = jsonFormat2(MemberInfoResp)
  implicit val projectDetailFormatter = jsonFormat3(ProjectDetailResp)
  implicit object ProjectListFormatter extends RootJsonFormat[ProjectList] {
    def read(value: JsValue) = ProjectList(value.convertTo[List[ProjectInfoResponse]])
    def write(obj: ProjectList) = obj.datas.toJson
  }
}
