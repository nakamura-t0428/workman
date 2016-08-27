package com.loadtester.data.service.lib

import org.scalatest._
import org.scalatest.BeforeAndAfter
import com.loadtester.data.service.lib.{TestDB => db}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success,Failure}
import scala.concurrent.Await
import scala.concurrent.duration._

trait DBTestBase extends FunSpec with BeforeAndAfter {
  def initDb = {
    val res = db.schemaService.createTables
    Await.ready(res, DurationInt(30).second)
    
    res.value.get
  }
  
  before {
    initDb
  }
}
