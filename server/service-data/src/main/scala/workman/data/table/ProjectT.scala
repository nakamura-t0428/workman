package workman.data.table

import workman.data.db.base.Driver
import workman.data.model._
import java.sql.Timestamp
import java.sql.Date

trait ProjectT extends Driver with UserT with CompanyT {
  import driver.api._
  class Projects(tag:Tag) extends Table[Project](tag, "PROJECT") {
    // Basic Info
    def prjId = column[String]("PRJ_ID", O.SqlType("VARCHAR(24)"), O.PrimaryKey)
    def name = column[String]("PRJ_NAME", O.SqlType("VARCHAR(64)"))
    def description = column[String]("PRJ_DESC", O.SqlType("VARCHAR(512)"), O.Default(""))
    def compId = column[String]("COMP_ID", O.SqlType("VARCHAR(24)"))
    def ownerId = column[String]("OWNER_ID", O.SqlType("VARCHAR(24)"))
    def regDate = column[Timestamp]("REGDATE", O.SqlType("DATETIME"))
    def disabled = column[Boolean]("DISABLED", O.Default(false))
    // Planning Schedule
    def expectedDays = column[Float]("EXP_DAYS", O.Default(0f))
    def startDate = column[Date]("ST_DATE")
    def startDateFixed = column[Boolean]("IS_ST_DATE_FIXED", O.Default(false))
    
    def owner = foreignKey("OWNER_FK", ownerId, userTbl)(_.userId, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    def company = foreignKey("COMP_FK", compId, companyTbl)(_.compId, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    
    def * = (
        prjId, name, description, ownerId, compId, regDate, disabled, // Basic Info
        expectedDays, startDate, startDateFixed // Planning Schedule
        ) <> (Project.tupled, Project.unapply)
  }
  
  val projectTbl = TableQuery[Projects]
}
