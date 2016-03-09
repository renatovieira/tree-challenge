
import scala.io.Source
import org.json4s.DefaultFormats
import scala.collection.immutable.HashMap
import scala.collection.immutable.ListMap
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import org.eclipse.jetty.servlet.DefaultServlet

object Main {
  def main(args: Array[String]): Unit = {
    val inputfileName = args(0)

    def buildForestFromInputFile() = {
      try {
        for (line <- Source.fromFile(inputfileName).getLines()) {
          val edges = line.split(" ")

          val from = edges(0).toInt
          val to = edges(1).toInt

          Forest.tryToAddEdge(from, to)
        }
      } catch {
        case ex: Exception => println("File does not exist")
      }
    }

    buildForestFromInputFile()
    
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[DefaultServlet], "/")

    server.setHandler(context)

    server.start
    server.join
  }
}