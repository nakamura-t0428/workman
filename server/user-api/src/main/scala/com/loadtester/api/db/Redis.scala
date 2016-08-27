package com.loadtester.api.db

import scala.concurrent.duration.Duration
import com.redis._
import com.loadtester.api.util.{UserAPIProps}

object Redis {
  val clients = new RedisClientPool(UserAPIProps.redisHost, UserAPIProps.redisPort)
  val INVITATION_KEY = "invitation.emails"
  
  def setInvitation(email:String, exp:Duration = UserAPIProps.invitaionExp) = {
    clients.withClient { client => {
      client.set((INVITATION_KEY, email), System.currentTimeMillis, false, Seconds(exp.toSeconds))
    }}
  }
  
  def hasInvitation(email:String):Boolean = {
    clients.withClient { client => {
      client.get((INVITATION_KEY, email)).isDefined
    }}
  }
}
