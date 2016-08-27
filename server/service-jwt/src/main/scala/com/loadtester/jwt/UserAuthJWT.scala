package com.loadtester.jwt

import com.nimbusds.jwt.JWTClaimsSet
import scala.concurrent.duration.DurationInt
import com.nimbusds.jose.JWSObject
import scala.concurrent.duration.Duration

object UserAuthJWT extends JWTBase {
  def apply(userId:String, exp:Duration):JWTClaimsSet = {
    claimSetBase
    .subject(userId)
    .roles(List("user"))
    .expire(exp)
    .build()
  }
  
  def tokenString(userId:String, exp:Duration = 10 minutes):String = {
    toTokenStr(apply(userId, exp))
  }
  
  def verifyRole:Verifier = { claims =>
    claims.getStringArrayClaim(CRAIM_ROLES).toList match {
      case xs if xs.contains("user") => Some(claims)
      case _ => None
    }
  }
  
  object UserIdOf {
    def unapply(bearerOp:Option[String]):Option[String] = {
      val prefix = "Bearer "
      val tokenStrOp = for {
        bearer <- bearerOp if bearer.startsWith(prefix)
      } yield {
        bearer.substring(prefix.length, bearer.length)
      }
      val claimsOp = tokenStrOp.flatMap(tokenStr => {
        extractJwtClaimSet(tokenStr)
      })
      claimsOp.flatMap(claims => verifyRole(claims)).map(_.getSubject)
    }
  }
}