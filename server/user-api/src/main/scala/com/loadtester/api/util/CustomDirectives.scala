package com.loadtester.api.util

import shapeless._
import spray.http.{StatusCodes, HttpCookie}
import spray.http.HttpHeaders.RawHeader
import spray.httpx.SprayJsonSupport._
import spray.routing._
import spray.routing.Directives._
import com.loadtester.data.dto.UserReg
import com.loadtester.api.json.JsonSupport._
import com.loadtester.api.json.request.{UserRegister}
import com.loadtester.api.json.response.{SuccessMessage, FailureMessage, AuthorizationError}
import com.loadtester.jwt.{TmpAuthJWT, UserAuthJWT}

object CustomDirectives {
  val TokenHeader = "Auth-Token"
  val COOKIE_AUTHTOKEN = "AUTHTOKEN"
  def extractMap[T](opt:Option[T]):Directive1[T] = {
    opt match {
      case Some(t) => provide(t)
      case _ => complete("")
    }
  }
  
  def userRegisterJson2Dto(json:UserRegister):Directive1[UserReg] = {
    TmpAuthJWT.extractJwtClaimSet(json.token).flatMap(claims => TmpAuthJWT.verifyRole(claims)).map{claims =>
      TmpAuthJWT.toUserRegisterJWT(claims)
    } match {
      case Some(tmpAuth) => {
        val dto = UserReg(tmpAuth.userId, tmpAuth.email, json.passwd, json.name)
        if(dto.name.isEmpty) complete(StatusCodes.BadRequest, FailureMessage("error", "name is empty"))
        else if(dto.passwd.isEmpty) complete(StatusCodes.BadRequest, FailureMessage("error", "passwd is empty"))
        else provide(dto)
      }
      case _ => complete(StatusCodes.Forbidden, FailureMessage("error", "syserr"))
    }
  }
  
  def respondWithNewToken(token:String):Directive0 = {
    respondWithSingletonHeader(RawHeader(TokenHeader, token))
  }
  
  def verifyToken:Directive1[String] = {
    optionalHeaderValueByName("Authorization").flatMap {
      case UserAuthJWT.UserIdOf(userId) => {
        val token = UserAuthJWT.tokenString(userId)
        respondWithNewToken(token).hflatMap{_ =>
          provide(userId)
        }
      }
      case _ => complete(StatusCodes.Forbidden, AuthorizationError())
    }
  }
}