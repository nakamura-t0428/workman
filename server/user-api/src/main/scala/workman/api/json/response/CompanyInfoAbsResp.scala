package workman.api.json.response

import workman.data.model.Company

case class CompanyInfoAbsResp(
    compId:String,
    name:String
    )
object CompanyInfoAbsResp {
  def fromModel(c:Company) = CompanyInfoAbsResp(
      c.compId,
      c.name
      )
}
