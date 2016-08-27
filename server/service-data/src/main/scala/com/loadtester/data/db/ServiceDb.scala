package com.loadtester.data.db

import com.loadtester.data.table._
import com.loadtester.data.db.base.DbBase

class ServiceDb(runMode:String = DbBase.envRunMode)
extends DbBase("/testportal-db", runMode)
with UserT
with ProjectT
with ProjectUserT
