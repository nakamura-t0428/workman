package workman.api.json.response

import workman.data.dto.ProjectInfo
import workman.util.converter.SqlDateConverter._

object ProjectPlanResp {
  def fromModel(p:ProjectInfo) = ProjectPlanResp(
      p.project.expectedDays,
      p.project.startDate.format,
      p.project.startDateFixed
      )
}

case class ProjectPlanResp(
    expectedDays:Float,
    startDate:String,
    startDateFixed:Boolean
    )
