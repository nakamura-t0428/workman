package workman.api.jwt

import workman.util.helper.PropertyHelper
import scala.concurrent.duration.DurationInt

object JWTProps {
  val prop = PropertyHelper.getProperties("/jwt")
  val hs256secret = prop.getProperty("jwt.hs256.secret", "PREASE_CHANGE_ME")
  val tokenLifeInvitation = prop.getProperty("jwt.life.minute.invitation", "1440").toInt.minutes
  val tokenLifeUser = prop.getProperty("jwt.life.minute.user", "10").toInt.minutes
}
