package com.loadtester.data.service.lib

import com.loadtester.data.ServiceFactory
import github.nakamura_t0428.db.DbBase

object TestDB extends ServiceFactory(DbBase.RUNMODE_TEST)
