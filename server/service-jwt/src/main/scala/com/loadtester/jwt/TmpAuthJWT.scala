package com.loadtester.jwt

import com.nimbusds.jwt.JWTClaimsSet
import scala.concurrent.duration.DurationInt
import github.nakamura_t0428.util.helper.UUIDHelper
import com.loadtester.jwt.dto.UserRegisterJWT
import scala.concurrent.duration.Duration

object TmpAuthJWT extends JWTBase {
  def apply(userId:String, email:String, exp: Duration):JWTClaimsSet = {
    claimSetBase
    .subject(userId)
    .email(email)
    .roles(List("nc"))
    .expire(exp)
    .build()
  }
  
  def generate(email:String, exp: Duration = 24 hours):JWTClaimsSet = {
    val uuid = java.util.UUID.randomUUID
    apply(UUIDHelper.uuidToBase64(uuid), email, exp)
  }
  
  def tokenStr(email:String, exp: Duration = 24 hours):String = {
    toTokenStr(generate(email, exp))
  }
  
  def verifyRole:Verifier = { claims =>
    claims.getStringArrayClaim(CRAIM_ROLES).toList match {
      case List("nc") => Some(claims)
      case _ => None
    }
  }
  
  def toUserRegisterJWT(claims:JWTClaimsSet):UserRegisterJWT = {
    val userId = claims.getSubject
    val email = claims.getStringClaim(CRAIM_EMAIL)
    UserRegisterJWT(userId, email)
  }
}