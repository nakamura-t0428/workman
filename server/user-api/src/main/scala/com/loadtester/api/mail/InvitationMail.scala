package com.loadtester.api.mail

import com.loadtester.api.util.UserAPIProps

object InvitationMail {
  val subject = "loadtesterへのお誘い"
  def body(tokenStr:String) = s"""
${UserAPIProps.siteURLBase}/#/signup/${tokenStr}
""".trim
}