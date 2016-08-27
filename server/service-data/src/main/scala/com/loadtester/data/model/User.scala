package com.loadtester.data.model

import java.sql.Timestamp

case class User(
    userId:String,
    email:String,
    passHash:String,
    name:String,
    disabled:Boolean,
    role:Int,
    regDate:Timestamp
    )
