package com.loadtester.data

import com.loadtester.data.service._
import com.loadtester.data.db.ServiceDb

class ServiceFactory(runMode:String) {
  val dbm = new ServiceDb(runMode)
  val schemaService = new SchemaService(dbm)
  val userService = new UserService(dbm)
  val projectService = new ProjectService(dbm)
}
