


class Node(var nodeValue: Int, var childrenArray: List[Node]) {
  val value: Int = nodeValue
  var numberOfPoints: Double = -1
  var children: List[Node] = childrenArray

  def addChild(newChild: Node) = {
    children = newChild :: children
  }

  def calculatePoints(): Double = {
    if (numberOfPoints < 0) {
      var hasValidChildren: Boolean = false
      var totalChildrenPoints: Double = 0

      for (childNode <- children) {
        if (childNode.children.size > 0) {
          hasValidChildren = true
        }

        childNode.calculatePoints()
        totalChildrenPoints += childNode.numberOfPoints
      }

      if (hasValidChildren) {
        numberOfPoints = 0.5 * totalChildrenPoints + children.size
      } else {
        numberOfPoints = 0
      }
    }
    
    return numberOfPoints
  }
}