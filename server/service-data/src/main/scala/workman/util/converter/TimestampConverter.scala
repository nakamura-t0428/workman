package workman.util.converter

import java.sql.Timestamp
import java.util.Date

object TimestampConverter {
  implicit class timestampConverter(t:Timestamp) {
    def javaDate:Date = new java.util.Date(t.getTime)
    def addDays(d:Int):Timestamp = new Timestamp(t.getTime + {d * 24L*3600*1000})
    def addHours(h:Int):Timestamp = new Timestamp(t.getTime + {h * 60L*60*1000})
    def addMinutes(m:Int):Timestamp = new Timestamp(t.getTime + {m * 60L*1000})
    def - (a:Timestamp):Long = {
      t.getTime - a.getTime
    }
  }
}