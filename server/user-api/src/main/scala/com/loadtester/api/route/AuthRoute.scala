package com.loadtester.api.route

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
import com.loadtester.api.util.{CustomDirectives, UserAPIProps}
import com.loadtester.api.service.TmpAuthLogic
import com.loadtester.api.service.TmpAuthLogic.InvitaionExistsException
import com.loadtester.data.dto.{UserReg, UserAuth}
import com.loadtester.data.service.AlreadyExistError
import com.loadtester.api.json.response.MyInfoResp
import com.loadtester.api.json.response.ProjectInfoResponse
import com.loadtester.api.json.ProjectJsonSupport._
import com.loadtester.api.json.response.MyInfoResp


/**
 * 認証API定義
 */
trait AuthRoute extends HttpService with LazyLogging {
  implicit def exceptionHandler(implicit log:LoggingContext):ExceptionHandler
  
  // you can use Actor's dispatcher as the execution context
  implicit val executionContext: ExecutionContext
  
  // a user authentication function
  def userPassAuthenticator(userPass: Option[UserPass]): Future[Option[String]] = {
    val res = for {
      up <- userPass
    } yield {
      db.userService.authenticate(UserAuth(up.user, up.pass)).map(_.map(_.email))
    }
    res.get
  }

  val authRoute =
    path("invite") {
      post {
        entity(as[Invitation]) {inv => {
          onComplete(TmpAuthLogic.sendInvitation(inv.email)) {
            case Success(_) => complete(SuccessMessage())
            case Failure(InvitaionExistsException) => complete(FailureMessage("already exists", "exists"))
            case Failure(e) => complete {
              logger.error("SystemError", e)
              SysErrMessage()
            }
          }
        }}
      }
    } ~
    path("signup") {
      post {
        entity(as[UserRegister]){ json => CustomDirectives.userRegisterJson2Dto(json) { dto =>
          val dbRes = db.userService.registerUser(dto)
          onComplete(dbRes) {
            case Success(user) => {
              val token = UserAuthJWT.tokenString(user.userId)
              CustomDirectives.respondWithNewToken(token) {
                complete(SuccessMessage())
              }
            }
            case Failure(AlreadyExistError) => {
              complete(FailureMessage("Already Exists", "exists"))
            }
            case Failure(e) => {
              logger.error(s"Failed to Register User for ${dto.toString}", e)
              complete(SysErrMessage())
            }
          }
        }}
      }
    } ~
    path("signin") {
      post { entity(as[UserAuth]) { userAuth =>
        val dbRes = db.userService.authenticate(userAuth)
        onComplete(dbRes) {
          case Success(Some(user)) => {
            val token = UserAuthJWT.tokenString(user.userId)
            CustomDirectives.respondWithNewToken(token) {
              complete(UserAuthResponse(true, token, user.email, user.name))
            }
          }
          case Success(_) => {
            complete(FailureMessage("Invalid Account.","auth_failed"))
          }
          case Failure(e) => {
            logger.error("Failed to Authenticate", e)
            complete(SysErrMessage())
          }
        }
      }}
    } ~
    CustomDirectives.verifyToken { userId =>
      path("myinfo") {
        get {
          onComplete(db.userService.userInfo(userId)) {
            case Success(Some(u)) => {
              complete(MyInfoResp(true, u.userId, u.email, u.name))
            }
            case Success(_) => {
              complete(FailureMessage("User not found","not_found"))
            }
            case Failure(e) => {
              logger.error(s"Failed to get UserInfo for ${userId}", e)
              complete(SysErrMessage())
            }
          }
        }
      } ~
      path("test") {
        get {
          complete {
            ProjectInfoResponse("1", "テストプロジェクト1")
          }
        }
        
      }
    }
}