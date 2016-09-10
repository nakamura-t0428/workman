package workman.data.dto

import java.sql.Date
import workman.data.model._

case class ProjectInfo(
    project:Project,
    company:Company,
    owner:User
    )
