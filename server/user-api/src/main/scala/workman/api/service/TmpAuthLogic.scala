package workman.api.service

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import workman.api.util.MailUtil
import workman.api.jwt.TmpAuthJWT
import workman.api.util.UserAPIProps
import workman.api.mail.InvitationMail
import workman.api.db.Redis

object TmpAuthLogic {
  object InvitaionExistsException extends Exception
  
  def sendInvitation(email:String):Future[String] = Future{
    if(Redis.hasInvitation(email)) throw InvitaionExistsException
    val claims = TmpAuthJWT.generate(email)
    val tokenStr = TmpAuthJWT.toTokenStr(claims)
    MailUtil.sendMail(UserAPIProps.mailSysFrom, InvitationMail.subject, email::Nil, InvitationMail.body(tokenStr))
    Redis.setInvitation(email)
    tokenStr
  }
}