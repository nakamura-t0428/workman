package com.loadtester.data.model

object UserRole {
  val NONE   = 0
  val USER   = Integer.parseInt("00000001", 2)
  val ADMIN  = Integer.parseInt("10000000", 2)
  
  val default = USER
}
