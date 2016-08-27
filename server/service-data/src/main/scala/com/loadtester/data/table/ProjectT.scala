package com.loadtester.data.table

import github.nakamura_t0428.db.Driver
import com.loadtester.data.model._
import java.sql.Timestamp

trait ProjectT extends Driver with UserT {
  import driver.api._
  class Projects(tag:Tag) extends Table[Project](tag, "PROJECT") {
    def prjId = column[String]("PRJ_ID", O.SqlType("CHAR(24)"), O.PrimaryKey)
    def prjName = column[String]("PRJ_NAME", O.SqlType("VARCHAR(64)"))
    def ownerId = column[String]("OWNER_ID", O.SqlType("CHAR(24)"))
    def regDate = column[Timestamp]("REGDATE", O.SqlType("DATETIME"))
    // 権限
    def permManager = column[Int]("PERM_MANAGER")
    def permMember = column[Int]("PERM_MEMBER")
    def permGuest = column[Int]("PERM_GUEST")
    
    def owner = foreignKey("OWNER_FK", ownerId, userTbl)(_.userId, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    
    def * = (prjId, prjName, ownerId, regDate, permManager, permMember, permGuest) <> (Project.tupled, Project.unapply)
  }
  
  val projectTbl = TableQuery[Projects]
}
