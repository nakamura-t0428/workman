package com.loadtester.api.service

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import github.nakamura_t0428.mail.MailUtil
import com.loadtester.jwt.TmpAuthJWT
import com.loadtester.api.util.UserAPIProps
import com.loadtester.api.mail.InvitationMail
import com.loadtester.api.db.Redis

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