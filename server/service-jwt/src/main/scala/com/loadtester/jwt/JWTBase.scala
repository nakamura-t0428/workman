package com.loadtester.jwt

import com.nimbusds.jwt.JWTClaimsSet
import scala.concurrent.duration.Duration
import java.util.Calendar
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.JWSObject
import java.text.ParseException
import java.util.Date
import com.typesafe.scalalogging.LazyLogging
import scala.util.Try

trait JWTBase extends LazyLogging {
  type Verifier = JWTClaimsSet => Option[JWTClaimsSet]
  
  val CRAIM_ROLES = "roles"
  val CRAIM_EMAIL = "email"
  
  protected def claimSetBase = {
    new JWTClaimsSet.Builder()
    .issuer("com.loadtest")
  }
  
  val secret = JWTProps.hs256secret
  val signer = new MACSigner(secret)
  
  def toTokenStr(claims:JWTClaimsSet):String = claims.toTokenStr
  
  implicit class claimBuilderImp(builder:JWTClaimsSet.Builder) {
    def expire(duration:Duration):JWTClaimsSet.Builder = {
      val validUntil = Calendar.getInstance()
      validUntil.add(Calendar.SECOND, duration.toSeconds.toInt)
      builder.expirationTime(validUntil.getTime())
    }
    def roles(roleList:List[String]):JWTClaimsSet.Builder = {
      import scala.collection.JavaConversions
      builder.claim(CRAIM_ROLES, JavaConversions.seqAsJavaList(roleList))
    }
    def email(mail:String):JWTClaimsSet.Builder = {
      builder.claim(CRAIM_EMAIL, mail)
    }
  }
  implicit class claimImp(claims:JWTClaimsSet) {
    def toTokenStr:String = {
      val signed = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims)
      signed.sign(signer)
      signed.serialize()
    }
  }
  
  /** Common verifier for JWS objects. */
  private val verifier = new MACVerifier(secret.getBytes())
  
  def jwtConfirmer(token: JWSObject): Option[JWTClaimsSet] = {
    if (token.verify(verifier)) {
      try {
        Option(JWTClaimsSet.parse(token.getPayload().toJSONObject()))
      } catch {
        case _: ParseException => None
      }
    } else {
      None
    }
  }
  
  def verifyNotExpired: Verifier =
    claims => {
      def isValid(validUntil: Date) = Calendar.getInstance().getTime().compareTo(validUntil) <= 0
      Option(claims.getExpirationTime()) match {
        case Some(validUntil) if isValid(validUntil) => Some(claims)
        case _                                       => {logger.info("Token Expired");None}
      }
    }
  
  def extractJwtClaimSet(tokenStr:String) = {
    Try[Option[JWSObject]](Some(JWSObject.parse(tokenStr))).getOrElse(None).flatMap{ jws =>
      jwtConfirmer(jws).flatMap(verifyNotExpired(_))
    }
  }
}