package com.loadtester.api.json.response

object AuthorizationError {
  def apply() = FailureMessage("invalid authorization","authorization_error")
}
object TokenExpiredError {
  def apply() = FailureMessage("authentication token expired","token_expired_error")
}
