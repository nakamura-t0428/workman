package workman.api.util

import com.typesafe.scalalogging.LazyLogging
import java.util.Properties
import scala.io.Source
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import org.slf4j.MarkerFactory
import javax.mail.Address
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Message.RecipientType
import javax.mail.Transport
import workman.util.helper.PropertyHelper

object MailUtil extends LazyLogging{
  val M_MAIL = MarkerFactory.getMarker("MAIL")
  val M_MAIL_SENT = MarkerFactory.getMarker("MAIL_SENT")

  val PROP_USER_NAME = "mail.user"
  val PROP_PASSWORD  = "mail.password"
  val PROP_FROM = "mail.from"
  var logFormatter:String => String = (s:String) => s
  var authenticator: Option[Authenticator] = None

  lazy val properties: Properties = {
    val prop = new Properties
    prop.load(Source.fromURL(PropertyHelper.loadProperties("/mail")).bufferedReader)
    prop
  }

  def configureMailer = {
    try{
      val auth = new Authenticator{
        val user = properties.getProperty(PROP_USER_NAME)
        val pass = properties.getProperty(PROP_PASSWORD)
        logger.info(s"MailAuth: $user, $pass")
        override def getPasswordAuthentication = {
          new PasswordAuthentication(user, pass)
        }
      }
      this.authenticator = Some(auth)
    }
    catch {
      case e:Throwable => {
        logger.error("Failed to configure MailAuthentication.", e)
      }
    }
  }
  configureMailer
  def from:String = properties.getProperty(PROP_FROM, "")

  def sendMail(from: String, subject: String, to: List[String], text:String, enc:String="iso-2022-jp"):Unit = {
    val session = if(authenticator.isDefined) Session.getDefaultInstance(properties, authenticator.get)
      else Session.getDefaultInstance(properties)

    val msg = new MimeMessage(session)
    msg.setFrom(new InternetAddress(from))
    msg.setRecipients(RecipientType.TO, to.map(new InternetAddress(_)).toArray[Address])
    msg.setText(text, enc)
    msg.setSubject(subject, enc)
    Transport.send(msg)
    logger.info(M_MAIL_SENT, logFormatter(s"FROM: ${from}, SUBJECT: ${subject}, TO: ${to.mkString(", ")}"))
  }
}
