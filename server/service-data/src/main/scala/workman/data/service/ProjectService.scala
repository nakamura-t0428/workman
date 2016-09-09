package workman.data.service

import workman.data.db.ServiceDb
import workman.data.model._
import workman.data.dto._
import workman.util.helper.UUIDHelper
import workman.util.helper.DateHelper.now
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ProjectService(val dbm:ServiceDb) {
  import dbm.db
  import dbm.driver.api._
  
  def createCompany(reg:CompanyReg) = {
    val comp = Company(
        compId = UUIDHelper.uuidAsBase64,
        name = reg.name,
        description = reg.desc,
        regDate = now
        )
    val q = dbm.companyTbl += comp
    db.run(q.map(_ => comp))
  }
  
  def updateCompany(reg:CompanyBaseUpdate) = {
    val q = dbm.companyTbl.filter(_.compId === reg.compId).map(c => (c.name, c.description))
    db.run(q.update(reg.name, reg.description))
  }
  
  def searchCompany(name:String) = {
    val q = if(name.isEmpty()) dbm.companyTbl
      else dbm.companyTbl.filter(_.name like s"%${name}%")
    val res = q.sortBy(_.name).result.map(_.map(c => CompanyInfo(c.compId, c.name, c.description, c.regDate)))
    db.run(res)
  }
  
  def getCompany(compId:String) = {
    val q = dbm.companyTbl.filter(_.compId === compId)
    db.run(q.result.headOption)
  }
  
  def createProject(reg:ProjectReg) = {
    println(reg.compId)
    val prj = Project(
        prjId = UUIDHelper.uuidAsBase64,
        name = reg.name,
        description = reg.description,
        compId = reg.compId,
        ownerId = reg.ownerId,
        regDate = now
        )
    println(prj.compId)
    val q = dbm.projectTbl += prj
    db.run(q.map(_ => prj))
  }
  
  def updateProject(reg:ProjectBaseUpdate) = {
    val q = dbm.projectTbl.filter(_.prjId === reg.prjId).map(c => (c.name, c.description, c.compId))
    db.run(q.update(reg.name, reg.description, reg.compId))
  }
  
  def ownProjects(userId:String, limit:Limit):Future[Seq[ProjectInfo]] = {
    val q = for{
      prj <- dbm.projectTbl if prj.ownerId === userId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.name, owner.userId, owner.name, prj.regDate)
    
    val res = q.sortBy(_._5).drop(limit.limit * limit.page).take(limit.limit)
      .result.map(_.map{case (prjId, prjName, ownerId, ownerName, regDate) =>
        {ProjectInfo(prjId, prjName, UserInfo(ownerId, ownerName))}})
    
    db.run(res)
  }
  
  def myProjects(userId:String, limit:Limit = DefaultLimit):Future[Seq[ProjectInfo]] = {
    val q1 = for{
      prj <- dbm.projectTbl if prj.ownerId === userId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.name, owner.userId, owner.name, prj.regDate)
    val q2 = for{
      prjUser <- dbm.prjUserTbl if prjUser.userId === userId
      prj <- dbm.projectTbl if prj.prjId === prjUser.prjId
      owner <- dbm.userTbl if owner.userId === prj.ownerId
    } yield (prj.prjId, prj.name, owner.userId, owner.name, prj.regDate)
    
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
    } yield (prj.prjId, prj.name, owner.userId, owner.name, prj.regDate)
    val qMem = for{
      prjUser <- dbm.prjUserTbl if prjUser.prjId === prjId
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
