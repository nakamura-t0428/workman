package com.loadtester.util.converter

import java.util.Calendar
import com.loadtester.util.helper.CommonDateFormatter._

object JavaDateConverter {
  implicit class convert(d:java.util.Date) {
    def sqlDate = new java.sql.Date(d.getTime)
    def sqlTimestamp = new java.sql.Timestamp(d.getTime)
    def format = dateFormatter_sql.format(d)
    def timeToRead = timeFormatterToRead.format(d)
    def dateToRead = dateFormatterToRead.format(d)
    def addYears(years:Int) = {
      val cal = Calendar.getInstance
      cal.add(Calendar.YEAR, years)
      cal.getTime
    }
    def addMonths(months:Int) = {
      val cal = Calendar.getInstance
      cal.setTime(d)
      cal.add(Calendar.MONTH, months)
      cal.getTime
    }
    def addDays(days:Int) = new java.util.Date(d.getTime + {days * 24L*3600*1000})
    def addMinutes(hours:Int) = new java.util.Date(d.getTime + {hours * 60*1000})

    def age:Int = {
      val nowCal = Calendar.getInstance
      nowCal.setTime(new java.util.Date)
      val oldCal = Calendar.getInstance
      oldCal.setTime(d)

      val diffY = nowCal.get(Calendar.YEAR) - oldCal.get(Calendar.YEAR)
      val diffD = nowCal.get(Calendar.DAY_OF_YEAR) - oldCal.get(Calendar.DAY_OF_YEAR)
      if(diffD > 0){diffY}else{diffY-1}
    }
  }
}
