# Challenge #

## Build & Run ##

It uses Scalatra for the web part, but you can easily build the dependencies by using sbt.

Import project to Eclipse, setup the Run Configuration to pass the file name as argument (I included simpleInput and complexInput) as examples.

After running, it will build the initial forest from the input file and create 2 endpoinds:

- localhost:8080/points

This shows the points sorted by nodes in a JSON format.

- localhost:8080/add/:from/:to

Adds an invitation from the node "from" to the node "to".

## How it works ##

When adding a new invitation (edge), it adds a new edge only if the "to" vertex has not appeared before (has not been invited by anyone yet).

Notice that the invitation network is not necessarily a tree, since you may have disconnected parts - it is a forest.

To get the points, we must first compute the points for leaf nodes and only then start moving up to levels closer to the root. Therefore, I used a DFS approach that computes the points for the children, then check if the current node is able to have points (only if it has at least a grandchild). If it does, it gets a point for each child and half the sum of points of all its children. Otherwise, it has 0 points.