package com.loadtester.data.db.base

import java.util.Properties
import scala.io.Source
import com.loadtester.util.helper.PropertyHelper
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver
import slick.driver.H2Driver
import org.apache.commons.dbcp2.BasicDataSource

object DbBase {
  val RUNMODE_KEY = "runMode"
  val RUNMODE_TEST = "test"
  val RUNMODE_DEV  = "dev"
  val RUNMODE_PROD = "prod"
  def envRunMode = scala.util.Properties.envOrElse(DbBase.RUNMODE_KEY, DbBase.RUNMODE_PROD)
}

class DbBase(
    propBase:String,
    runMode:String
    ) extends DBConnection {
  import DbBase._
  val DRIVER_MYSQL = "com.mysql.jdbc.Driver"
  val DRIVER_H2 = "org.h2.Driver"
  val prop = new Properties
  val propName = runMode match {
    case RUNMODE_TEST => propBase + ".test"
    case RUNMODE_DEV => propBase
    case RUNMODE_PROD => propBase
  }
  prop.load(Source.fromURL(PropertyHelper.loadProperties(propName)).bufferedReader)
  
  val driver:JdbcProfile = prop.getProperty("db.driver", DRIVER_MYSQL) match {
    case DRIVER_MYSQL => MySQLDriver
    case DRIVER_H2 => H2Driver
    case _ => MySQLDriver
  }
  
  val db = driver.api.Database.forDataSource({
    val ds = new BasicDataSource
    ds.setDriverClassName(prop.getProperty("db.driver"))

    ds.setUrl(prop.getProperty("db.url"))
    ds.setUsername(prop.getProperty("db.username"))
    ds.setPassword(prop.getProperty("db.password"))

    ds.setMaxTotal(prop.getProperty("db.max.total").toInt)
    ds.setMaxIdle(prop.getProperty("db.max.idle").toInt)
    ds.setInitialSize(prop.getProperty("db.initial.size").toInt)

    ds
  })
}