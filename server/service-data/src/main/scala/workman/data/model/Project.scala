package workman.data.model

import java.sql.Timestamp

case class Project(
    prjId:String,
    name:String,
    description:String,
    ownerId:String,
    compId:String,
    regDate:Timestamp,
    disabled:Boolean = false,
    permManager:Int = ProjectPermission.defaultManager,
    permMember:Int = ProjectPermission.defaultMember,
    permGuest:Int = ProjectPermission.defaultGuest
    )
