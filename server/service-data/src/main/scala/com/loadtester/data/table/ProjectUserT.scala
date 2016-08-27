package com.loadtester.data.table

import github.nakamura_t0428.db.Driver
import com.loadtester.data.model._

trait ProjectUserT extends Driver with UserT with ProjectT{
  import driver.api._
  class ProjectUsers(tag:Tag) extends Table[ProjectUser](tag, "PROJECT_USER") {
    def prjId = column[String]("PRJ_ID", O.SqlType("CHAR(24)"))
    def userId = column[String]("USER_ID", O.SqlType("CHAR(24)"))
    def role = column[String]("ROLE", O.SqlType("VARCHAR(20)"), O.Default(ProjectUserRole.MEMBER))
    
    def prj = foreignKey("PROJECT_FK", prjId, projectTbl)(_.prjId, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    def user = foreignKey("USER_FK", userId, userTbl)(_.userId, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    def pk = primaryKey("pk_prj_user", (prjId, userId))
    
    def * = (prjId, userId, role) <> (ProjectUser.tupled, ProjectUser.unapply)
  }
  val PrjUserTbl = TableQuery[ProjectUsers]
}