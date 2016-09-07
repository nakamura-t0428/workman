package workman.api.route

import spray.routing._
import spray.util._
import spray.http.HttpHeaders._
import scala.concurrent.ExecutionContext
import spray.http.MediaTypes

trait TestRoute extends HttpService {
  implicit def exceptionHandler(implicit log:LoggingContext):ExceptionHandler
  implicit val executionContext: ExecutionContext
  
  val respondWithContentDisposition:Directive0 =
    respondWithSingletonHeaders(
        RawHeader("X-Content-Type-Options", """nosniff"""),
        RawHeader("Content-Disposition", """attachment; filename="api.json"""")
      )
    
  val respondAsHTML:Directive0 = 
    respondWithMediaType(MediaTypes.`text/html`) &
    respondWithSingletonHeaders(
        RawHeader("X-Content-Type-Options", """nosniff""")
      )
  
  val testJson = """{ "data" : "
<HTML><HEAD></HEAD><BODY>
TEST
<SCRIPT>
  alert("test");
</SCRIPT>
</BODY></HTML>
"
}"""
  
  val testRoute = pathPrefix("test") {
    (path("json1") & get) {
      respondAsHTML {
        complete(testJson)
      }
    } ~
    (path("json2") & get) {
      respondWithContentDisposition {
        complete(testJson)
      }
    } ~
    (path("json3") &get) {
      (respondAsHTML & respondWithContentDisposition) {
        complete(testJson)
      }
    }
  }
}