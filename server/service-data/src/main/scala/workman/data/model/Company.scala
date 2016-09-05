package workman.data.model

import java.sql.Timestamp

case class Company(
    compId:String,
    name:String,
    description:String,
    regDate:Timestamp,
    disabled:Boolean
    )
