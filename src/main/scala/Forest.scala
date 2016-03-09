

import scala.collection.immutable.HashMap
import scala.collection.immutable.ListMap

import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write

object Forest {
  // This hashmap will be used so we don't have to search for a Node every time
  private var intToNodeMap = HashMap.empty[Int, Node]

  def tryToAddEdge(from: Int, to: Int) = {
    def addNewNode(nodeValue: Int) = {
      intToNodeMap = intToNodeMap + (nodeValue -> new Node(nodeValue, List[Node]()))
    }

    def addEdge() = {
      val fromNode: Node = intToNodeMap(from)
      val toNode: Node = intToNodeMap(to)

      fromNode.addChild(toNode)
    }

    if (!intToNodeMap.contains(from)) {
      addNewNode(from)
    }
    if (!intToNodeMap.contains(to)) {
      addNewNode(to)
      addEdge()
    }
  }

  def calculatePoints(): ListMap[Int, Double] = {
    def getSortedIntToNodeMap(): ListMap[Int, Node] = {
      return ListMap(intToNodeMap.toSeq.sortBy(_._1): _*)
    }
    
    var points = ListMap.empty[Int, Double]

    // Reset points for all nodes
    intToNodeMap.foreach {
      keyVal =>
        keyVal._2.numberOfPoints = -1
    }

    // Calculate new points
    getSortedIntToNodeMap().foreach {
      keyVal =>
        points = points + ((keyVal._1) -> keyVal._2.calculatePoints())
    }

    return points
  }

  def displayPoints(): String = {
    val points = calculatePoints()

    implicit val formats = DefaultFormats
    return write(points)
  }
}