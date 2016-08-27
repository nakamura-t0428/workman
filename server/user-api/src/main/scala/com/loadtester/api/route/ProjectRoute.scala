package com.loadtester.api.route

import scala.util.{Success,Failure}
import scala.concurrent.{
  ExecutionContext,
  Future
}
import spray.routing._
import com.typesafe.scalalogging.LazyLogging
import com.loadtester.data.dto.ProjectReg
import com.loadtester.api.json.request.ProjectRegister
import com.loadtester.api.db.{DbServices => db}
import com.loadtester.api.util.CustomDirectives
import spray.httpx.SprayJsonSupport._
import com.loadtester.api.json.JsonSupport._
import com.loadtester.api.json.ProjectJsonSupport._
import com.loadtester.api.json.request._
import com.loadtester.api.json.response._
import spray.http.StatusCodes
import spray.util.LoggingContext
import com.loadtester.data.dto.Limit

trait ProjectRoute extends HttpService with LazyLogging {
  implicit def exceptionHandler(implicit log:LoggingContext):ExceptionHandler
  implicit val executionContext: ExecutionContext
  
  val projectRoute = CustomDirectives.verifyToken { userId =>
    pathPrefix("project") {
      pathEnd {
        post{
          prjCreate(userId)
        } ~
        get{
          prjList(userId)
        }
      } ~
      path(Rest) { prjId =>
        get {
          prjDetail(userId, prjId)
        }
      } ~
      path(Rest) { prjId =>
        delete {
          prjDelete(userId, prjId)
        }
      }
    }
  }
  
  def prjCreate(userId:String) =
    entity(as[ProjectRegister]) {prj => 
      onComplete(db.projectService.createProject(ProjectReg(prj.name, userId))) {
        case Success(p) => complete{
          ProjectInfoResponse(prjId = p.prjId, name = p.name)
        }
        case Failure(e) => complete {
          logger.error("SystemError", e)
          SysErrMessage()
        }
      }
    }
  def prjList(userId:String) =
    parameters("limit".as[Int], "page".as[Int]) {(limit, page) => 
      val dbRes = db.projectService.myProjects(userId, Limit(limit min 50 , page))
      onComplete(dbRes) {
        case Success(prjs) => complete{
          ProjectList(prjs.map(p => ProjectInfoResponse(p.prjId, p.name)).toList)
        }
        case Failure(e) => complete{
          logger.error("SystemError", e)
          SysErrMessage()
        }
      }
    }
  
  def prjDetail(userId:String, prjId:String) = {
    val dbRes = db.projectService.myProject(userId, prjId)
    onComplete(dbRes) {
      case Success((prj,members)) => complete{
        ProjectDetailResp(
            ProjectInfoResponse(prj.prjId, prj.name),
            MemberInfoResp(prj.owner.userId, prj.owner.name),
            members.map(mem => MemberInfoResp(mem.userId, mem.name)).toList
            )
      }
      case Failure(e) => complete{
        logger.error("SystemError", e)
        SysErrMessage()
      }
    }
  }
  
  def prjDelete(userId:String, prjId:String) = {
    val dbRes = db.projectService.deleteProject(userId, prjId)
    onComplete(dbRes) {
      case Success(true) => complete{
        SuccessMessage()
      }
      case Success(_) => complete{
        logger.error(s"Project to delete not found: (${userId}, ${prjId})")
        SysErrMessage()
      }
      case Failure(e) => complete{
        logger.error("SystemError", e)
        SysErrMessage()
      }
    }
  }
}
