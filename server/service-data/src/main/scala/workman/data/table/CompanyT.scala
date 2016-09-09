package workman.data.table

import workman.data.db.base.Driver
import workman.data.model._
import java.sql.Timestamp

trait CompanyT extends Driver {
  import driver.api._
  
  class Companies(tag:Tag) extends Table[Company](tag, "COMPANY") {
    def compId = column[String]("COMP_ID", O.SqlType("VARCHAR(24)"), O.PrimaryKey)
    def name = column[String]("COMP_NAME", O.SqlType("VARCHAR(64)"))
    def description = column[String]("COMP_DESC", O.SqlType("VARCHAR(512)"))
    def regDate = column[Timestamp]("REGDATE", O.SqlType("DATETIME"))
    def disabled = column[Boolean]("DISABLED", O.Default(false))
    
    def * = (compId, name, description, regDate, disabled) <> (Company.tupled, Company.unapply)
  }
  
  val companyTbl = TableQuery[Companies]
}