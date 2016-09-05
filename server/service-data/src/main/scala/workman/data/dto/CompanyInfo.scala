package workman.data.dto

import java.sql.Timestamp

case class CompanyInfo(
    compId:String,
    name:String,
    desc:String,
    regDate:Timestamp
    )