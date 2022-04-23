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
  HashMap<String, Node> nodeMap = new HashMap<String, Node>();
  EdgeGetter edgeGetter = EdgeGetter.getInstance();

  public AStar(Location startLoc, Location targetLoc) {
    this.startNode = new Node(startLoc, edgeGetter.getEdges(startLoc.getNodeID()));
    this.targetNode = new Node(targetLoc, edgeGetter.getEdges(targetLoc.getNodeID()));
  }

  /**
   * Calculate the Euclidian distance between two points
   *
   * @param firstNode starting node
   * @param secondNode end node
   * @return the distance between them
   */
  private double getCurrentCost(Node firstNode, Node secondNode) {
    double xDiff = Math.abs(secondNode.getXCoord() - firstNode.getXCoord());
    double yDiff = Math.abs(secondNode.getYCoord() - firstNode.getYCoord());

    double cost = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

    return cost;
  }

  /**
   * Calculates a heuristic value for the algorithm. Heuristic is defined as the horizontal distance
   * to go plus a penalty per floor
   *
   * @param node current node
   * @param targetNode the goal node
   * @return a guess for the cost remaining
   */
  public double getHeuristicValue(Node node, Node targetNode) {
    double floorPenalty = 40;
    double value = getCurrentCost(node, targetNode);

    value += Math.abs(node.getFloor() - targetNode.getFloor()) * floorPenalty;

    return value;
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
        System.out.println("I found a path!");
        break;
      }

      // We pull the current list of neighbors from the edgeMap and then iterate thru it
      LinkedList<String> neighbors = edgeGetter.getEdges(currentNode.getNodeId());
      for (int i = 0; i < neighbors.size(); i++) {
        Node nextNode = null;
        boolean isNewNode = false;

        // If the node does not yet exist in the hash map, create it and add to the map
        if (!nodeMap.containsKey(neighbors.get(i))) {
          nextNode =
              new Node(locDB.getByID(neighbors.get(i)), edgeGetter.getEdges(neighbors.get(i)));
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

          // Here we add a heuristic value. We use the heuristic value function defined above
          double priority = newCost + getHeuristicValue(nextNode, targetNode);
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
      System.out.println(node.getNodeId());
      node = node.getCameFrom();
    }
    path.add(0, locDB.getByID(startNode.getNodeId()));

    return path;
  }
}
