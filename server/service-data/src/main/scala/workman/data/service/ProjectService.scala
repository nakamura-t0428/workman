package workman.data.service

import workman.data.db.ServiceDb
import workman.data.dto.ProjectReg
import workman.util.helper.UUIDHelper
import workman.data.model.Project
import workman.util.helper.DateHelper.now
import scala.concurrent.ExecutionContext.Implicits.global
import workman.data.dto.ProjectInfo
import workman.data.dto.UserInfo
import workman.data.dto.ProjectInfo
import workman.data.dto.UserInfo
import scala.concurrent.Future
import workman.data.dto.Limit
import workman.data.dto.DefaultLimit
import workman.data.dto.MemberInfo

class ProjectService(val dbm:ServiceDb) {
  import dbm.db
  import dbm.driver.api._
  
  def createProject(prjReg:ProjectReg) = {
    val prj = Project(
        UUIDHelper.uuidAsBase64,
        prjReg.name,
        prjReg.ownerId,
        now
        )
    val q = dbm.projectTbl += prj
    db.run(q.map(_ => prj))
  }
  
  def ownProjects(userId:String, limit:Limit):Future[Seq[ProjectInfo]] = {
    val q = for{
      prj <- dbm.projectTbl if prj.ownerId === userId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.prjName, owner.userId, owner.name, prj.regDate)
    
    val res = q.sortBy(_._5).drop(limit.limit * limit.page).take(limit.limit)
      .result.map(_.map{case (prjId, prjName, ownerId, ownerName, regDate) =>
        {ProjectInfo(prjId, prjName, UserInfo(ownerId, ownerName))}})
    
    db.run(res)
  }
  
  def myProjects(userId:String, limit:Limit = DefaultLimit):Future[Seq[ProjectInfo]] = {
    val q1 = for{
      prj <- dbm.projectTbl if prj.ownerId === userId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.prjName, owner.userId, owner.name, prj.regDate)
    val q2 = for{
      prjUser <- dbm.PrjUserTbl if prjUser.userId === userId
      prj <- dbm.projectTbl if prj.prjId === prjUser.prjId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.prjName, owner.userId, owner.name, prj.regDate)
    
    val q = {q1 union q2}.distinctOn(_._1).sortBy(_._5)
    
    val res = q.drop(limit.limit * limit.page).take(limit.limit)
      .result.map(_.map{case (prjId, prjName, ownerId, ownerName, regDate) =>
        {ProjectInfo(prjId, prjName, UserInfo(ownerId, ownerName))}})
    
    db.run(res)
  }
  
  def myProject(userId:String, prjId:String) = {
    val qPrj = for{
      prj <- dbm.projectTbl if prj.prjId === prjId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.prjName, owner.userId, owner.name, prj.regDate)
    val qMem = for{
      prjUser <- dbm.PrjUserTbl if prjUser.prjId === prjId
      member <- dbm.userTbl if member.userId === prjUser.userId
    } yield (member.userId, member.name)
    
    val resPrj = qPrj.result.map(_.map{case (prjId, prjName, ownerId, ownerName, regDate) =>
      ProjectInfo(prjId, prjName, UserInfo(ownerId, ownerName))}.head)
    val resMem = qMem.result.map(_.map{case (userId, userName) => MemberInfo(userId, userName)})
    
    db.run(resPrj).zip(db.run(resMem)).filter{case (p, mems) => {p.owner.userId == userId || mems.exists(_.userId == userId)}}
  }
  
  def deleteProject(userId:String, prjId:String) = {
    val qPrj = dbm.projectTbl.filter(prj => prj.prjId === prjId && prj.ownerId === userId)    
    db.run(qPrj.delete.map(_ <= 0))
  }
}
