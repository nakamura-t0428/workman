package workman.data.db

import workman.data.table._
import workman.data.db.base.DbBase

class ServiceDb(runMode:String = DbBase.envRunMode)
extends DbBase("/testportal-db", runMode)
with UserT
with ProjectT
with ProjectUserT
