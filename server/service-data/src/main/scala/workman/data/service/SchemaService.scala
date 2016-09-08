package workman.data.service

import workman.data.db.ServiceDb
//import scala.concurrent.impl.Future
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._

class SchemaService(val dbm:ServiceDb) {
  import dbm.db
  import dbm.driver.api._
  
  val tbls = List(dbm.userTbl, dbm.projectTbl, dbm.prjUserTbl, dbm.companyTbl)
  val schema = tbls.map(_.schema).reduce(_ ++ _)
  
  def initTables = {
    Await.ready(dropTables, DurationInt(30).second)
    createTables
  }
  
  def dropTables = {
    Future.sequence(
      tbls.map{tbl =>
        db.run(tbl.schema.drop)
      }
    )
  }
  
  def createTables = {
    db.run(schema.create)
  }
}