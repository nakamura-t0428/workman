package com.loadtester.data.dto

import java.sql.Timestamp
import com.loadtester.util.helper.DateHelper.now

case class UserReg(
    userId:String,
    email:String,
    passwd:String,
    name:String,
    regDate:Timestamp = now
    )
