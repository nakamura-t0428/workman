package com.loadtester.data.model

import java.sql.Timestamp

case class Project(
    prjId:String,
    name:String,
    ownerId:String,
    regDate:Timestamp,
    permManager:Int = ProjectPermission.defaultManager,
    permMember:Int = ProjectPermission.defaultMember,
    permGuest:Int = ProjectPermission.defaultGuest
    )
