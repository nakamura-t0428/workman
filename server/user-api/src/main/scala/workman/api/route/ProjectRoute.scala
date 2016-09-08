package workman.api.route

import scala.util.{Success,Failure}
import scala.concurrent.{
  ExecutionContext,
  Future
}
import spray.routing._
import com.typesafe.scalalogging.LazyLogging
import workman.data.dto.ProjectReg
import workman.api.json.request.ProjectRegister
import workman.api.db.{DbServices => db}
import workman.api.util.CustomDirectives
import spray.httpx.SprayJsonSupport._
import workman.api.json.JsonSupport._
import workman.api.json.ProjectJsonSupport._
import workman.api.json.request._
import workman.api.json.response._
import spray.http.StatusCodes
import spray.util.LoggingContext
import workman.data.dto.Limit
import workman.data.dto.CompanyReg

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
    pathPrefix("company") {
      pathEnd {
        post{
          companyCreate()
        } ~
        get{
          companySearch()
        }
      } ~
      {
        path(Rest) { compId =>
          get {
            companyDetail(compId)
          }
        }
      }
    }
  }
  
  def companyCreate() =
    entity(as[CompanyRegister]) {comp =>
      onComplete(db.projectService.createCompany(CompanyReg(comp.name, comp.description))) {
        case Success(c) => complete{
          CompanyInfoResp(c.compId, c.name, c.description)
        }
        case Failure(e) => complete {
          logger.error("SystemError", e)
          SysErrMessage()
        }
      }
    }
  
  def companySearch() =
    parameters('name.as[String], 'limit.as[Int], 'page.as[Int]).as(CompanySearch) {q =>
      onComplete(db.projectService.searchCompany(q.name)) {
        case Success(lst) => complete{
          CompanyList(lst.map(c => CompanyInfoResp(c.compId, c.name, c.desc)).toList)
        }
        case Failure(e) => complete{
          logger.error("SystemError", e)
          SysErrMessage()
        }
      }
    }
  def companyDetail(compId:String) = onComplete(db.projectService.getCompany(compId)) {
    case Success(Some(c)) => complete{
      CompanyInfoResp(c.compId, c.name, c.description)
    }
    case Success(_) => complete{StatusCodes.NotFound}
    case Failure(e) => complete{
      logger.error("SystemError", e)
      SysErrMessage()
    }
  }
  
  def prjCreate(userId:String) =
    entity(as[ProjectRegister]) {prj => 
      onComplete(db.projectService.createProject(ProjectReg(prj.name, prj.description, userId, prj.compId))) {
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
