package com.loadtester.api

import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure
import akka.io.IO
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import spray.can.server.Stats
import spray.util._
import spray.http._
import spray.routing._
import com.loadtester.api.util.SSLConfig
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ProxyActor extends HttpServiceActor with SSLConfig with ActorLogging{
  import context.dispatcher // ExecutionContext for the futures and scheduler
  import system.dispatcher // implicit execution context
  
  implicit val system: ActorSystem = ActorSystem()
  implicit val timeout: Timeout = Timeout(30 seconds) // for the actor 'asks'
  
  def receive = runRoute(route)
  def route:Route = (ctx:RequestContext) => ctx complete(proxy(ctx))
  
  def proxy(ctx:RequestContext): Future[HttpResponse] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    logRequest(ctx.request)
    
    //SSLではここにコネクターの設定が必要？
    IO(Http).ask(ctx.request).mapTo[HttpResponse] map { resp =>
      logResponse(ctx.request, resp)
      resp
    }
  }
  
  def logRequest(req:HttpRequest):Unit = {
    log.debug(s"ProxyIn: ${req.method} ${req.uri}")
  }
  def logResponse(req:HttpRequest, resp:HttpResponse):Unit = {
    log.info(s"ProxyOut: ${resp.status} for ${req.method} ${req.uri}")
  }
}