package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.lang.Math;

public class AStar {
  LocationsDB locDB;
  Node startNode;
  Node targetNode;
  HashMap<String, LinkedList<String>> edgeMap = new HashMap<String, LinkedList<String>>();

  PriorityQueue<Location> frontier = new PriorityQueue<Location>();

  public AStar(Location startNode, Location targetNode) {
      EdgeGetter edgy = new EdgeGetter();
      this.edgeMap = edgy.getEdges();

      this.startNode = new Node(startNode, edgeMap.get(startNode.getNodeID()));
      this.targetNode = new Node(targetNode, edgeMap.get(targetNode.getNodeID()));
  }


  private double getCurrentCost(Location firstNode, Location secondNode){
      double xDiff = secondNode.getXCoord() - firstNode.getXCoord();
      double yDiff = secondNode.getYCoord() - firstNode.getYCoord();

      double cost = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

      return cost;
  }

  public LinkedList<Location> getPath(){
      LinkedList<Location> path = new LinkedList<Location>();

      PriorityQueue<Node> frontier = new PriorityQueue(300, new NodeComparator());
      frontier.add(startNode);

      while(!frontier.isEmpty()){
          Node currentNode = frontier.poll();



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
