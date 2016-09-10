package workman.util.helper

import java.util.Calendar
import java.sql.Timestamp
import scala.util.Try
import CommonDateFormatter._
import workman.util.converter.JavaDateConverter._
import java.sql.Date

object DateHelper {
  def now:Timestamp = new Timestamp(System.currentTimeMillis)
  def today:Date = todayDate
  
  def lastdate:Calendar = {
    val cal = Calendar.getInstance
    cal.add(Calendar.DATE, -1)
    cal
  }
  def todayDate:java.sql.Date = {
    java.sql.Date.valueOf(dateFormatter_sql.format(new java.util.Date))
  }

  def parseDate(str:String):java.util.Date = {
    if(str.contains('-')) {
      dateFormatter_sql.parse(str)
    } else {
      dateFormatter.parse(str)
    }
  }
  def parseSqlDate(str:String) = new java.sql.Date(parseDate(str).getTime)
  def parseTimestamp(str:String) = new java.sql.Timestamp(dateTimeFormatter.parse(str).getTime)

  def listupDateTuple(since:java.util.Date, until:java.util.Date):List[(Int, Int, Int)] = {
    val days = {{until.getTime - since.getTime}/{24*3600*1000}}.toInt
    val cal = Calendar.getInstance
    cal.setTime(since)
    val res = for(i <- 1 to days) yield {
      cal.add(Calendar.DATE, 1)
      (cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE))
    }
    res.toList
  }

  def listupDate(since:java.util.Date, until:java.util.Date):List[java.util.Date] = {
    val days = {{until.getTime - since.getTime}/{24*3600*1000}}.toInt
    val cal = Calendar.getInstance
    cal.setTime(since)
    val res = for(i <- 1 to days) yield {
      cal.add(Calendar.DATE, 1)
      cal.getTime
    }
    res.toList
  }

  def listupSqlDate(since:java.sql.Date, until:java.sql.Date):List[java.sql.Date] = {
    val days = {{until.getTime - since.getTime}/{24*3600*1000}}.toInt
    val cal = Calendar.getInstance
    cal.setTime(since)
    val res = for(i <- 1 to days) yield {
      cal.add(Calendar.DATE, 1)
      new java.sql.Date(cal.getTimeInMillis)
    }
    res.toList
  }

  def parseTimestampOption(str:String):Option[Timestamp] = {
    str match {
      case "" => None
      case s => Try(parseDate(s).sqlTimestamp).toOption
    }
  }
  def parseEndTimestampOption(str:String):Option[Timestamp] = {
    str match {
      case "" => None
      case s => Try(parseDate(s).addDays(1).sqlTimestamp).toOption
    }
  }
}
