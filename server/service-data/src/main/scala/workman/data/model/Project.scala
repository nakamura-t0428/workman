package workman.data.model

import java.sql.Timestamp
import java.sql.Date

case class Project(
    // Basic Info
    prjId:String,
    name:String,
    description:String,
    ownerId:String,
    compId:String,
    regDate:Timestamp,
    disabled:Boolean = false,
    // Planning Schedule
    expectedDays:Float,
    startDate:Date,
    startDateFixed:Boolean = false
    )
