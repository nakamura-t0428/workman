package workman.api.mail

import workman.api.util.UserAPIProps

object InvitationMail {
  val subject = "loadtesterへのお誘い"
  def body(tokenStr:String) = s"""
${UserAPIProps.siteURLBase}/#/guest/signup/${tokenStr}
""".trim
}