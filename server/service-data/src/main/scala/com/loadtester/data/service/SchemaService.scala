package com.loadtester.data.service

import com.loadtester.data.db.ServiceDb

class SchemaService(val dbm:ServiceDb) {
  import dbm.db
  import dbm.driver.api._
  
  val tbls = List(dbm.userTbl, dbm.projectTbl, dbm.PrjUserTbl)
  val schema = tbls.map(_.schema).reduce(_ ++ _)
  
  def createTables = {
//    db.run(DBIO.sequence(tbls.map(_.schema.drop.asTry)).asTry.andFinally( schema.create ))
    db.run(schema.drop.asTry.andFinally( schema.create ))
  }
}