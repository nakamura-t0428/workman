package com.loadtester.tool.funcs

import com.loadtester.data.db.ServiceDb
import com.loadtester.data.service.SchemaService
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object InitDb extends LazyLogging {
  val dbm = new ServiceDb
  val dao = new SchemaService(dbm)
  
  def initDb(args:List[String]):Unit = {
    val res = dao.createTables
    Await.ready(res, DurationInt(30).second)
    
    res.value.get match {
      case Success(a) => {
        logger.info("Database Initialized.")
      }
      case Failure(e) => {
        logger.error("Failed to DbInit", e)
      }
    }
  }
}