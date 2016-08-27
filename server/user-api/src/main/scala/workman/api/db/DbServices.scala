package workman.api.db

import workman.data.ServiceFactory
import workman.data.db.base.DbBase

object DbServices extends ServiceFactory(DbBase.RUNMODE_PROD)