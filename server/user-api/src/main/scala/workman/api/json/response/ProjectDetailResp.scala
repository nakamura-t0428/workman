package workman.api.json.response

import workman.data.dto.ProjectInfo
import workman.data.dto.MemberInfo

object ProjectDetailResp {
  def fromModel(p:ProjectInfo, us:Seq[MemberInfo]) = ProjectDetailResp(
      ProjectInfoResp.fromModel(p),
      ProjectPlanResp.fromModel(p),
      MemberInfoResp.fromModel(p.owner),
      CompanyInfoAbsResp.fromModel(p.company),
      us.map(MemberInfoResp.fromModel(_)).toList
      )
}
case class ProjectDetailResp(
    prjInfo:ProjectInfoResp,
    prjPlan:ProjectPlanResp,
    owner:MemberInfoResp,
    company:CompanyInfoAbsResp,
    members:List[MemberInfoResp]
    )
