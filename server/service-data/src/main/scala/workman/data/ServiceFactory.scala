package workman.data

import workman.data.service._
import workman.data.db.ServiceDb

class ServiceFactory(runMode:String) {
  val dbm = new ServiceDb(runMode)
  val schemaService = new SchemaService(dbm)
  val userService = new UserService(dbm)
  val projectService = new ProjectService(dbm)
}
