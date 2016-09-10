package workman.api.json.response

import workman.data.dto.ProjectInfo
import workman.data.model.Project

object ProjectInfoResp {
  def fromModel(p:ProjectInfo) = ProjectInfoResp(
      p.project.prjId,
      p.project.name
      )
  def fromModel(p:Project) = ProjectInfoResp(
      p.prjId,
      p.name
      )
}

case class ProjectInfoResp(
    prjId:String,
    name:String
    )
