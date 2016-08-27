package com.loadtester.api

import scala.concurrent.duration._
import akka.io.IO
import akka.actor.{Actor,ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[UserApiActor], "user-api")
  val proxies = system.actorOf(Props[ProxyActor], "proxy-actor")

  implicit val timeout = Timeout(30.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(proxies, interface = "localhost", port = 8101)
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8090)
}
