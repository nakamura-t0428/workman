package com.loadtester.api.util

import org.scalatest.FunSpec
import spray.testkit.ScalatestRouteTest

import scala.util.{Success,Failure}
import scala.concurrent.{
  ExecutionContext,
  Future
}
import spray.util.LoggingContext
import spray.http.HttpCookie
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing._
import spray.routing.authentication.{
  BasicAuth,
  UserPass
}
import com.typesafe.scalalogging.LazyLogging
import com.nimbusds.jose.{JWSObject, JWSAlgorithm}
import com.nimbusds.jwt.JWTClaimsSet
import com.loadtester.jwt.{UserAuthJWT, TmpAuthJWT}
import com.loadtester.api.db.{DbServices => db}
import com.loadtester.api.json.JsonSupport._
import com.loadtester.api.json.UserJsonSupport._
import com.loadtester.api.json.request.{Invitation, UserRegister}
import com.loadtester.api.json.response.{FailureMessage, SuccessMessage, SysErrMessage, UserAuthResponse, AuthorizationError}
import com.loadtester.api.service.TmpAuthLogic
import com.loadtester.api.service.TmpAuthLogic.InvitaionExistsException
import com.loadtester.data.dto.{UserReg, UserAuth}
import com.loadtester.data.service.AlreadyExistError
import scala.concurrent.duration.DurationInt
import spray.http.HttpHeaders._

class CustomDirectivesSpec extends FunSpec with ScalatestRouteTest with HttpService with LazyLogging {
  import CustomDirectives._
  def actorRefFactory = system // connect the DSL to the test ActorSystem
  
  describe("userRegisterJson2Dto") {
    val userId = "dummy"
    val email = "test@test.test"
    val passwd = "password1"
    val name = "Yamada Taro"
    
    val smallRoute = post { entity(as[UserRegister]){js => userRegisterJson2Dto(js) {dto =>
      complete((dto.userId, dto.email, dto.passwd, dto.name).toString)
    }}}
    it("有効期限内のトークンからDB用DTOを抽出できること") {
      val validClaims = TmpAuthJWT(userId, email, 24 hours)
      val validToken = TmpAuthJWT.toTokenStr(validClaims)
      val json = UserRegister(token=validToken, passwd = passwd, name=name)
      Post("/register", json) ~> smallRoute ~> check {
        assertResult((userId, email, passwd, name).toString)(responseAs[String])
      }
    }
    it("有効期限切れのトークンではDB用DTOを抽出できないこと") {
      val expiredClaims = TmpAuthJWT(userId, email, -5 minutes)
      val expiredToken = TmpAuthJWT.toTokenStr(expiredClaims)
      val json = UserRegister(token=expiredToken, passwd = passwd, name=name)
      
      Post("/register", json) ~> smallRoute ~> check {
        assertResult(StatusCodes.Forbidden)(status)
      }
    }
  }
  
  describe("verifyToken") {
    val userId = "testtest"
    val validToken = UserAuthJWT.tokenString(userId)
    val expiredToken = UserAuthJWT.tokenString(userId, -5 minutes)
    val smallRoute = get { verifyToken {userId => complete(userId)} }
    it("有効期限内のトークンでアクセスできること") {
      Get("/") ~> addHeader("Authorization", s"Bearer ${validToken}") ~> smallRoute ~> check {
        assertResult(userId)(responseAs[String])
      }
    }
    it("有効期限切れのトークンでアクセスできないこと") {
      Get("/") ~> addHeader("Authorization", s"Bearer ${expiredToken}") ~> smallRoute ~> check {
        assertResult(StatusCodes.Forbidden)(status)
      }
    }
    it("アクセス成功時にCookieに更新用トークンが格納されること") {
      Get("/") ~> addHeader("Authorization", s"Bearer ${validToken}") ~> smallRoute ~> check {
        logger.info(headers.toString())
        assertResult(1)(headers.filter(_.is(TokenHeader.toLowerCase)).size)
      }
    }
    it("更新用トークンでアクセスできること") {
      Get("/") ~> addHeader("Authorization", s"Bearer ${validToken}") ~> smallRoute ~> check {
        logger.info(headers.toString())
        header(TokenHeader.toLowerCase) match {
          case Some(RawHeader(_, token)) => {
            Get("/") ~> addHeader("Authorization", s"Bearer ${token}") ~> smallRoute ~> check {
              assertResult(userId)(responseAs[String])
              assertResult(1)(headers.filter(_.is(TokenHeader.toLowerCase)).size)
            }
          }
          case _ => {fail("Token Not Found!")}
        }
      }
    }
  }
}