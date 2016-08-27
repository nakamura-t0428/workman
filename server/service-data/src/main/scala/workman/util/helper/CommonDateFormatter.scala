package workman.util.helper

import java.text.SimpleDateFormat

object CommonDateFormatter {
  val dateFormatter_sql = new SimpleDateFormat("yyyy-MM-dd")
  val dateFormatter = new SimpleDateFormat("yyyy/MM/dd")
  val dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val timeFormatterToRead = new SimpleDateFormat("yy/MM/dd HH:mm")
  val dateFormatterToRead = new SimpleDateFormat("yy/MM/dd")
  val datetimeLocalFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
}
