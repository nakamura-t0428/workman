package workman.data.model

import java.sql.Timestamp

case class Project(
    prjId:String,
    name:String,
    ownerId:String,
    regDate:Timestamp,
    disabled:Boolean,
    permManager:Int = ProjectPermission.defaultManager,
    permMember:Int = ProjectPermission.defaultMember,
    permGuest:Int = ProjectPermission.defaultGuest
    )
