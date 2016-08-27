package com.loadtester.util.converter

import java.util.Calendar

object CalendarConverter {
  implicit class convert(cal:Calendar) {
    def sqlDate = {
      val y = cal.get(Calendar.YEAR)
      val m = cal.get(Calendar.MONTH)+1
      val d = cal.get(Calendar.DATE)
      java.sql.Date.valueOf(s"${y}-${m}-${d}")
    }
    def tupleDate = (cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE))
  }
}