package com.loadtester.api.db

import github.nakamura_t0428.db.DbBase
import com.loadtester.data.ServiceFactory

object DbServices extends ServiceFactory(DbBase.RUNMODE_PROD)