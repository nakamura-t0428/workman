package com.loadtester.jwt

import github.nakamura_t0428.util.helper.PropertyHelper

object JWTProps {
  val prop = PropertyHelper.getProperties("/jwt")
  val hs256secret = prop.getProperty("jwt.hs256.secret", "PREASE_CHANGE_ME")
}
