package workman.util.helper

import java.sql.Timestamp

object CsvWriterHelper {
  implicit class convertString(s:String) {
    def col:String = {
      "\"" + s.replaceAll("\"", "\"\"") + "\""
    }
  }
  implicit class convertLong(i:Long) {
    def col:String = {
      i.toString
    }
  }
  implicit class convertInt(i:Int) {
    def col:String = {
      i.toString
    }
  }
  implicit class convertDouble(d:Double) {
    def col:String = {
      "%.5f".format(d)
    }
  }
  implicit class convertFloat(d:Float) {
    def col:String = {
      "%.5f".format(d)
    }
  }
  implicit class convertDate(t:Timestamp) {
    def col:String = {
      CommonDateFormatter.dateTimeFormatter.format(t:java.util.Date)
    }
  }
  
  implicit class convertOption[T <: Any](o:Option[T]) {
    def col:String = {
      o match {
        case Some(i:String) => convertString(i).col
        case Some(i:Long) => convertLong(i).col
        case Some(i:Int) => convertInt(i).col
        case Some(i:Double) => convertDouble(i).col
        case Some(i:Float) => convertFloat(i).col
        case Some(t:Timestamp) => convertDate(t).col
        case _ => ""
      }
    }
  }
}