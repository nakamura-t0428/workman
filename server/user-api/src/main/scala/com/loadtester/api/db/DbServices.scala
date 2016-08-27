package com.loadtester.api.db

import com.loadtester.data.ServiceFactory
import com.loadtester.data.db.base.DbBase

object DbServices extends ServiceFactory(DbBase.RUNMODE_PROD)