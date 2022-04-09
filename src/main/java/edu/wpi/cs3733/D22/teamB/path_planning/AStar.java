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

    // System.out.println(startLoc.getNodeID());
    this.startNode = new Node(startLoc, edgeMap.get(startLoc.getNodeID()));
    this.targetNode = new Node(targetLoc, edgeMap.get(targetLoc.getNodeID()));

    //    nodeMap.put(startNode.getNodeId(), startNode);
    //    nodeMap.put(targetNode.getNodeId(), targetNode);
  }

  private double getCurrentCost(Node firstNode, Node secondNode) {
    double xDiff = secondNode.getXCoord() - firstNode.getXCoord();
    double yDiff = secondNode.getYCoord() - firstNode.getYCoord();

    double cost = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

    return cost;
  }

  public ArrayList<Location> getPath() {
    ArrayList<Location> path = new ArrayList<Location>();

    PriorityQueue<Node> frontier = new PriorityQueue(300, new NodeComparator());
    frontier.add(startNode);

    while (!frontier.isEmpty()) {
      Node currentNode = frontier.poll();

      if (currentNode.getNodeId().equals(targetNode.getNodeId())) {
        // System.out.println("Found Target");
        // System.out.println("Target came from: " + targetNode.getCameFrom());
        targetNode = currentNode;
        break;
      }

      // System.out.println(currentNode.getNodeId());
      // System.out.println(edgeMap.get(currentNode.getNodeId()));
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

          //          LinkedList<String> nextNeighbors = nextNode.getEdges();
          //          for(int j = 0; j < nextNeighbors.size(); j++){
          //            if(!nodeMap.containsKey(nextNeighbors.get(i))){
          //              Node nextNeighbor =
          //            }
          //            frontier.add(nodeMap.get(nextNeighbors.get(i)));
          //          }
        }
      }
    }

    Node node = targetNode;

    while (!node.equals(startNode)) {
      System.out.println(node.getNodeId());
      path.add(0, locDB.getByID(node.getNodeId()));
      node = node.getCameFrom();
    }

    return path;
  }

  /*
  frontier = PriorityQueue()
  frontier.put(start, 0)
  came_from = dict()
  cost_so_far = dict()
  came_from[start] = None
  cost_so_far[start] = 0

  while not frontier.empty():
      current = frontier.get()

  if current == goal:
      break

  for next in graph.neighbors(current):
      new_cost = cost_so_far[current] + graph.cost(current, next)
      if next not in cost_so_far or new_cost < cost_so_far[next]:
          cost_so_far[next] = new_cost
          priority = new_cost + heuristic(goal, next)
          frontier.put(next, priority)
          came_from[next] = current
   */

}
