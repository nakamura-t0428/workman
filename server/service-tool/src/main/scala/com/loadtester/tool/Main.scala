package com.loadtester.tool

import com.loadtester.tool.funcs.InitDb

object Main extends App {
  val funcs = Map[String, List[String] => Unit](
      "usage" -> usage,
      "initDb" -> InitDb.initDb
      )
      
  def usage(args:List[String]):Unit = println(funcs.keys.mkString(", "))
  
  args.toList match {
    case funcName::funcArgs => {
      funcs.get(funcName) match {
        case Some(f) => {
          println("Execute: " + funcName)
          f(funcArgs)
          println("Finished.")
        }
        case None => {
          println("Unknown Command: " + funcName)
          usage(funcArgs)
        }
      }
    }
    case _ => {
      usage(Nil)
    }
  }
}