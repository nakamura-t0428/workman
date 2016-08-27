package com.loadtester.data.service

import org.scalatest._
import com.loadtester.data.service.lib.{TestDB => db}
import com.loadtester.data.service.lib.DBTestBase
import com.loadtester.data.dto.UserReg
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success,Failure}
import scala.concurrent.Await
import scala.concurrent.duration._
import com.loadtester.data.dto.UserAuth
import com.loadtester.data.dto.UserBase

class UserServiceSpec extends FunSpec with DBTestBase {
  val userReg1 = UserReg("userId1", "email1@test.test", "password1", "テスト 太郎")
  val userReg2 = UserReg("userId2", "email2@test.test", "password2", "テスト 次郎")
  
  describe("registerUser") {
    it("未登録なら登録できること") {
      val res = db.userService.registerUser(userReg1)
      Await.ready(res, DurationInt(30).second)
      val r = res.value.get match {
        case Success(user) => {
          assertResult(userReg1.email)(user.email)
        }
        case Failure(t) => {
          fail(s"Register Failed.: ${t}")
        }
      }
    }
    it("登録済なら登録できないこと") {
      val res = db.userService.registerUser(userReg1)
      Await.ready(res, DurationInt(30).second)
      val r = res.value.get match {
        case Success(user) => {
          val res = db.userService.registerUser(userReg1)
          Await.ready(res, DurationInt(30).second)
          val r = res.value.get match {
            case Success(user) => {
              fail("登録できてはいけない")
            }
            case Failure(t) => {
              // 登録済みのため登録できない
            }
          }
        }
        case Failure(t) => {
          fail(s"Register Failed.: ${t}")
        }
      }
    }
  }
  describe("authenticate") {
    it("正しいパスワードで認証ができること") {
      val res = db.userService.registerUser(userReg1)
      Await.ready(res, 30.second)
      val r = res.value.get match {
        case Success(user) => {
          val res2 = db.userService.authenticate(UserAuth(userReg1.email, userReg1.passwd))
          Await.ready(res2, 30.second)
          res2.value.get match {
            case Success(op) => {
              assert(op.isDefined)
            }
            case Failure(t) => {
              fail("authentication failed.")
            }
          }
          assertResult(userReg1.email)(user.email)
        }
        case Failure(t) => {
          fail(s"Register Failed.: ${t}")
        }
      }
    }
    it("間違ったパスワードで認証ができ無いこと") {
      val res = db.userService.registerUser(userReg1)
      Await.ready(res, 30.second)
      val r = res.value.get match {
        case Success(user) => {
          val res2 = db.userService.authenticate(UserAuth(userReg1.email, "wrongpassword"))
          Await.ready(res2, 30.second)
          res2.value.get match {
            case Success(op) => {
              assert(op.isEmpty)
            }
            case Failure(t) => {
              fail("authentication failed.")
            }
          }
          assertResult(userReg1.email)(user.email)
        }
        case Failure(t) => {
          fail(s"Register Failed.: ${t}")
        }
      }
    }
    it("正しいパスワードでユーザ情報が取得できること") {
      val res = db.userService.registerUser(userReg1)
      Await.ready(res, 30.second)
      val r = res.value.get match {
        case Success(user) => {
          val res2 = db.userService.authenticate(UserAuth(userReg1.email, userReg1.passwd))
          Await.ready(res2, 30.second)
          res2.value.get match {
            case Success(op) => {
              assertResult(Some(UserBase(userReg1.userId, userReg1.email, userReg1.name)))(op)
            }
            case Failure(t) => {
              fail("authentication failed.")
            }
          }
          assertResult(userReg1.email)(user.email)
        }
        case Failure(t) => {
          fail(s"Register Failed.: ${t}")
        }
      }
    }
  }
}
