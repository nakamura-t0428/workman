package com.loadtester.api.json.response

case class ResultMessage(success:Boolean, msg:String="", errId:String="")

object FailureMessage {
  def apply(msg:String="", errId:String="") = ResultMessage(false, msg, errId)
}

object SysErrMessage {
  def apply() = ResultMessage(false, "error", "syserr")
}

object SuccessMessage {
  def apply(msg:String="") = ResultMessage(true, msg, "")
}
