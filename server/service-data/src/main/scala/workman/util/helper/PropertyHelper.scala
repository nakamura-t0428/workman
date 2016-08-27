package workman.util.helper

import java.net.InetAddress
import com.typesafe.scalalogging.LazyLogging
import java.net.URL
import java.util.Properties
import scala.io.Source

object PropertyHelper extends LazyLogging  {
  private def hostNames:List[String] = {
    val inet = InetAddress.getLocalHost();
    val fqdn = inet.getHostName.trim
    val hostname = fqdn.split('.').toList match {
      case Nil => ""
      case e::Nil => ""
      case host1st::_ => host1st
    }
    val user = System.getenv("USER") match {
      case null => ""
      case s:String => s.replace(' ', '_')
    }

    logger.info("FQDN = " + fqdn)
    logger.info("HOSTNAME = " + hostname)
    logger.info("USER = " + user)
    
    val lst = user::fqdn::hostname::Nil
    lst.filter(_ != "")
  }
  def loadProperties(name:String):URL = {
    val defaultPropName = name + ".properties"
    //getClass.getResource("/database.properties")
    val names:List[String] = hostNames.filterNot(_.isEmpty).map(hname => s"${name}.${hname}"):::(s"${name}.default"::name::Nil)
    val propnames = names.map(name => name + ".properties")
    logger.info("Search Properties: " + propnames.mkString(", "))
    val propUrls = propnames.view.map(p => getClass.getResource(p)).filter(_ != null)
    propUrls.headOption match {
      case Some(url) => {
        logger.info("Properties is found: " + url.getPath())
        url
      }
      case _ => throw new Exception("Properties not found: " + propnames.mkString(", "))
    }
  }
  
  def getProperties(name:String):Properties = {
    val prop = new Properties
    prop.load(Source.fromURL(loadProperties(name)).bufferedReader)
    prop
  }
}
