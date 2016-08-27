package com.loadtester.data.model

object ProjectPermission {
  val NONE   = 0
  val READ   = Integer.parseInt("00000001", 2)
  val EXEC   = Integer.parseInt("00000010", 2)
  val EDIT   = Integer.parseInt("00000100", 2)
  val MEMBER = Integer.parseInt("00001000", 2)
  
  val defaultManager = READ | EXEC | EDIT |MEMBER
  val defaultMember  = READ | EXEC
  val defaultGuest   = NONE
}
