package com.loadtester.api

import akka.actor.Actor
import route._
import spray.routing._
import spray.http._
import spray.http.HttpHeaders.RawHeader
import com.loadtester.api.util.CustomDirectives.{TokenHeader}
import com.loadtester.api.util.UserAPIProps._
import spray.util.LoggingContext

class UserApiActor extends Actor with AuthRoute with ProjectRoute{
  implicit override def exceptionHandler(implicit log:LoggingContext) = ExceptionHandler {
    case e: Throwable =>
      requestUri { uri =>
        log.warning("Request to {} could not be handled normally: {} : {}", uri, e.getMessage)
        e.printStackTrace
        complete(StatusCodes.InternalServerError, "System Error")
      }
  }
  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context
  
  // supplies the default dispatcher as the implicit execution context
  override implicit lazy val executionContext = context.dispatcher
  
  def handleTimeouts: Receive = {
    case Timedout(x: HttpRequest) =>
      sender ! HttpResponse(StatusCodes.InternalServerError, "Too late")
  }
  
  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(
      respondWithHeaders(
          RawHeader("Access-Control-Allow-Origin", siteOrigin),
          RawHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"),
          RawHeader("Access-Control-Allow-Headers", s"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"),
          RawHeader("Access-Control-Expose-Headers", s"${TokenHeader}")
          ) {
        options {
          complete {StatusCodes.OK}
        } ~
        authRoute ~ projectRoute
      }
    )
}
