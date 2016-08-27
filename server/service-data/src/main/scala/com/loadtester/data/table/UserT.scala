package com.loadtester.data.table

import github.nakamura_t0428.db.Driver
import com.loadtester.data.model.User
import com.loadtester.data.model.UserRole
import java.sql.Timestamp

trait UserT extends Driver {
  import driver.api._
  class Users(tag:Tag) extends Table[User](tag, "USERBASE") {
    def userId = column[String]("USER_ID", O.SqlType("CHAR(24)"), O.PrimaryKey)
    def email = column[String]("EMAIL", O.SqlType("VARCHAR(191)"))
    def passHash = column[String]("PASS_HASH", O.SqlType("CHAR(64)"))
    def name = column[String]("NAME", O.SqlType("VARCHAR(64)"))
    def disabled = column[Boolean]("DISABLED", O.Default(false))
    def role = column[Int]("ROLE", O.Default(UserRole.default))
    def regDate = column[Timestamp]("REGDATE", O.SqlType("DATETIME"))
    
    def * = (userId, email, passHash, name, disabled, role, regDate) <> (User.tupled, User.unapply)
  }
  
  val userTbl = TableQuery[Users]
}
