package workman.data.db.base

import slick.driver.JdbcProfile

trait Driver {
  val driver: JdbcProfile
}