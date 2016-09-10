package workman.api.json

import spray.json.DefaultJsonProtocol
import workman.data.dto._
import workman.api.json.response._
import workman.api.json.request._

object ProjectJsonSupport extends DefaultJsonProtocol {
  import spray.json._
  
  implicit val companyRegisterFomatter = jsonFormat2(CompanyRegister.apply)
  implicit val companyRegisterRespFormatter = jsonFormat3(CompanyInfoResp.apply)
  implicit val companyInfoAbsRespFormatter = jsonFormat2(CompanyInfoAbsResp.apply)
  implicit val companySearchFormatter = jsonFormat3(CompanySearch.apply)
  implicit object CompanyListFormatter extends RootJsonFormat[CompanyList] {
    def read(value:JsValue) = CompanyList(value.convertTo[List[CompanyInfoResp]])
    def write(obj:CompanyList) = obj.datas.toJson
  }
  
  implicit val projectRegisterFormatter = jsonFormat3(ProjectRegister.apply)
  implicit val projectRegisterRespFormatter = jsonFormat2(ProjectInfoResp.apply)
  implicit val projectPlanRespFormatter = jsonFormat3(ProjectPlanResp.apply)
  implicit val memberResponseFormatter = jsonFormat2(MemberInfoResp.apply)
  implicit val projectDetailFormatter = jsonFormat5(ProjectDetailResp.apply)
  implicit object ProjectListFormatter extends RootJsonFormat[ProjectList] {
    def read(value: JsValue) = ProjectList(value.convertTo[List[ProjectInfoResp]])
    def write(obj: ProjectList) = obj.datas.toJson
  }
}
