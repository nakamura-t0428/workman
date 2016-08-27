package workman.data.dto

import java.sql.Timestamp
import workman.util.helper.DateHelper.now

case class UserReg(
    userId:String,
    email:String,
    passwd:String,
    name:String,
    regDate:Timestamp = now
    )
