package workman.util.converter

import java.util.Calendar
import workman.util.helper.CommonDateFormatter._

object SqlDateConverter {
  implicit class sqlDateConvert(d:java.sql.Date) {
    def javaDate:java.util.Date = new java.util.Date(d.getTime)
    def calendar:Calendar = {
      val cal = Calendar.getInstance
      cal.setTimeInMillis(d.getTime())
      cal
    }
    def addYears(years:Int):java.sql.Date = {
      val cal = Calendar.getInstance
      cal.setTimeInMillis(d.getTime())
      cal.add(Calendar.YEAR, years)
      new java.sql.Date(cal.getTimeInMillis())
    }
    def addDays(days:Int):java.sql.Date = {
      val cal = Calendar.getInstance
      cal.setTimeInMillis(d.getTime())
      cal.add(Calendar.DATE, days)
      new java.sql.Date(cal.getTimeInMillis())
    }
    def format:String = dateFormatter_sql.format(d.getTime)
  }
}
