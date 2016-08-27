package com.loadtester.data.model

object ProjectUserRole {
  val MANAGER = "MANAGER"
  val MEMBER  = "MEMBER"
}

case class ProjectUser(
    prjId:String,
    userId:String,
    role:String
    )
