package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStar {
  LocationsDB locDB = LocationsDB.getInstance();
  Node startNode;
  Node targetNode;
  HashMap<String, LinkedList<String>> edgeMap;
  HashMap<String, Node> nodeMap = new HashMap<String, Node>();

  public AStar(Location startLoc, Location targetLoc) {
    EdgeGetter edgy = new EdgeGetter();
    this.edgeMap = edgy.getEdges();

    this.startNode = new Node(startLoc, edgeMap.get(startLoc.getNodeID()));
    this.targetNode = new Node(targetLoc, edgeMap.get(targetLoc.getNodeID()));
  }

  /**
   * Calculate the Euclidian distance between two points
   *
   * @param firstNode starting node
   * @param secondNode end node
   * @return the distance between them
   */
  private double getCurrentCost(Node firstNode, Node secondNode) {
    double xDiff = secondNode.getXCoord() - firstNode.getXCoord();
    double yDiff = secondNode.getYCoord() - firstNode.getYCoord();

    double cost = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

    return cost;
  }

  /**
   * Runs the AStar algorithm between the start node and target node declared in the constructor.
   *
   * @return
   */
  public ArrayList<Location> getPath() {
    ArrayList<Location> path = new ArrayList<>();

    PriorityQueue<Node> frontier = new PriorityQueue(300, new NodeComparator());
    frontier.add(startNode);

    while (!frontier.isEmpty()) {
      Node currentNode = frontier.poll();

      if (currentNode.getNodeId().equals(targetNode.getNodeId())) {
        // We need to save the cameFrom attribute from current node to targetNode
        // We can do that by just setting them equal
        targetNode = currentNode;
        break;
      }

      // We pull the current list of neighbors from the edgeMap and then iterate thru it
      LinkedList<String> neighbors = edgeMap.get(currentNode.getNodeId());
      for (int i = 0; i < neighbors.size(); i++) {
        Node nextNode = null;
        boolean isNewNode = false;

        // If the node does not yet exist in the hash map, create it and add to the map
        if (!nodeMap.containsKey(neighbors.get(i))) {
          nextNode = new Node(locDB.getByID(neighbors.get(i)), edgeMap.get(neighbors.get(i)));
          nodeMap.put(nextNode.getNodeId(), nextNode);
          isNewNode = true;
        }
        // Otherwise, we get the next node
        else {
          nextNode = nodeMap.get(neighbors.get(i));
        }

        // Add the cost from current -> neighbor to cost so far for current
        double newCost = getCurrentCost(currentNode, nextNode) + currentNode.getCostsoFar();
        if (isNewNode || newCost < nextNode.getCostsoFar()) {
          nextNode.setCostsoFar(newCost);
          nextNode.setCameFrom(currentNode);

          // Here we add a heuristic value. In this case, it is just the distance between
          // the target node and the next node, so we can reuse the cost function
          double priority = newCost + getCurrentCost(nextNode, targetNode);
          nextNode.setPriority(priority);
          frontier.add(nextNode);
        }
      }
    }

    // Here we get the path starting from the targetNode
    Node node = targetNode;
    while (!node.equals(startNode)) {
      // Trace back the paths using the cameFrom attribute
      path.add(0, locDB.getByID(node.getNodeId()));
      node = node.getCameFrom();
    }

    return path;
  }
}
