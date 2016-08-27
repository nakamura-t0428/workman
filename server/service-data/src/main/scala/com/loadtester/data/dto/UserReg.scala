package com.loadtester.data.dto

import java.sql.Timestamp
import github.nakamura_t0428.util.helper.DateHelper.now

case class UserReg(
    userId:String,
    email:String,
    passwd:String,
    name:String,
    regDate:Timestamp = now
    )
