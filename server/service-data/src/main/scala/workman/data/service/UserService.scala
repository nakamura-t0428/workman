package workman.data.service

import workman.data.db.ServiceDb
import workman.util.helper.StringHelper
import workman.data.dto.UserAuth
import workman.data.dto.UserBase
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import workman.data.dto.UserReg
import scala.util.Failure
import com.github.t3hnar.bcrypt._
import scala.util.Success
import workman.data.dto.MyInfo

object AlreadyExistError extends Exception
  
class UserService(val dbm:ServiceDb) {
  import dbm.db
  import dbm.driver.api._
  
  def authenticate(auth:UserAuth) = {
    val q = for {
      u <- dbm.userTbl if
        u.email === auth.email &&
        ! u.disabled
    } yield (u.passHash, (u.userId, u.email, u.name))
    db.run(q.result.headOption).map(_.filter(u=>auth.passwd.isBcrypted(u._1)).map{u =>
      UserBase.tupled(u._2)
    })
  }
  
  def registerUser(user:UserReg) = {
    val q = dbm.userTbl.map(u => (u.userId, u.email, u.passHash, u.name, u.regDate))
    val t = UserReg.unapply(user.copy(passwd = user.passwd.bcrypt)).get
    val chkQ = dbm.userTbl.filter(u => u.userId === user.userId).exists
    val res = db.run(
        chkQ.result.flatMap{ b =>
          if(b) DBIO.failed(AlreadyExistError)
          else {q+=t}
        })
    res.map(_ => user)
  }
  
  def userInfo(userId:String) = {
    val q = dbm.userTbl.filter(u => u.userId === userId && !u.disabled).map(u => (u.userId, u.email, u.name))
    val res = db.run(q.result.headOption.map(_.map(r => MyInfo.tupled(r))))
    res
  }
}