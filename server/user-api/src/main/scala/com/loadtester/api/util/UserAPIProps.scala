package com.loadtester.api.util

import scala.util.Try
import scala.concurrent.duration.{Duration, DurationInt}
import github.nakamura_t0428.util.helper.PropertyHelper
import com.typesafe.scalalogging.LazyLogging

object UserAPIProps extends LazyLogging{
  val prop = PropertyHelper.getProperties("/user-api")
  val siteOrigin = prop.getProperty("user.site.origin", "https://localhost")
  val siteURLBase = prop.getProperty("user.site.url.base", "https://localhost")
  val mailSysFrom = prop.getProperty("user.mail.sys.from", "debug@test.test")
  val invitaionExp:Duration = prop.getProperty("user.invitaion.exp.hour", "24").toInt.hours
  val redisHost = prop.getProperty("user.redis.host","localhost")
  val redisPort:Int = Try(prop.getProperty("user.redis.port", "6379").toInt).getOrElse(6379)
}