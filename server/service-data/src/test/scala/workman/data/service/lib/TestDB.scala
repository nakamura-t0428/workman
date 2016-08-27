package workman.data.service.lib

import workman.data.ServiceFactory
import workman.data.db.base.DbBase

object TestDB extends ServiceFactory(DbBase.RUNMODE_TEST)
