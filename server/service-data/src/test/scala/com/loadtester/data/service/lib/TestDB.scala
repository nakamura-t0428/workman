package com.loadtester.data.service.lib

import com.loadtester.data.ServiceFactory
import com.loadtester.data.db.base.DbBase

object TestDB extends ServiceFactory(DbBase.RUNMODE_TEST)
