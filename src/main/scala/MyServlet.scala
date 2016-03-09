
import org.scalatra._
import scala.io.Source
import json._
import org.json4s.{ DefaultFormats, Formats }

class MyServlet extends ChallengeStack {
  protected implicit val jsonFormats = DefaultFormats

  get("/points") {

    { Forest.displayPoints() }
  }

  get("/add/:from/:to") {
    { Forest.tryToAddEdge(params("from").toInt, params("to").toInt) }

    redirect("/points")
  }

}
