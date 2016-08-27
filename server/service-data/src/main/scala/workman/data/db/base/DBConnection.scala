package workman.data.db.base

trait DBConnection extends Driver{
import driver.api._
  val db:Database
}