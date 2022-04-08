package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.lang.Math;

public class AStar {
  LocationsDB locDB;
  Node startNode;
  Node targetNode;
  HashMap<String, LinkedList<String>> edgeMap = new HashMap<String, LinkedList<String>>();
  HashMap<String, Node> nodeMap = new HashMap<String, Node>();


    public AStar(Location startLoc, Location targetLoc) {
      EdgeGetter edgy = new EdgeGetter();
      this.edgeMap = edgy.getEdges();

      this.startNode = new Node(startLoc, edgeMap.get(startLoc.getNodeID()));
      this.targetNode = new Node(targetLoc, edgeMap.get(targetLoc.getNodeID()));

      nodeMap.put(startNode.getNodeId(), startNode);
      nodeMap.put(targetNode.getNodeId(), targetNode);
  }

  private double getCurrentCost(Node firstNode, Node secondNode){
      double xDiff = secondNode.getXCoord() - firstNode.getXCoord();
      double yDiff = secondNode.getYCoord() - firstNode.getYCoord();

      double cost = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

      return cost;
  }

  public ArrayList<Location> getPath(){
      ArrayList<Location> path = new ArrayList<Location>();

      PriorityQueue<Node> frontier = new PriorityQueue(300, new NodeComparator());
      frontier.add(startNode);

      while(!frontier.isEmpty()){
          Node currentNode = frontier.poll();

          if(currentNode.getNodeId().equals(targetNode.getNodeId())){
              break;
          }

          LinkedList<String> neighbors  = edgeMap.get(currentNode.getNodeId());
          for(int i = 0; i < neighbors.size(); i++){
              Node neighborNode = null;
              boolean isNewNode = false;

              // If the node does not yet exist in the hash map, create it and add to the map
              if(!nodeMap.containsKey(neighbors.get(i))) {
                  neighborNode = new Node(locDB.getByID(neighbors.get(i)), edgeMap.get(neighbors.get(i)));
                  nodeMap.put(neighborNode.getNodeId(), neighborNode);
                  isNewNode = true;
              } else{
                  neighborNode = nodeMap.get(neighbors.get(i));
              }

              // Add the cost from current -> neighbor to cost so far for current
              double newCost = getCurrentCost(currentNode, neighborNode) + currentNode.getCostsoFar();
              if(isNewNode || newCost < neighborNode.getCostsoFar()){
                  neighborNode.setCostsoFar(newCost);
                  neighborNode.setCameFrom(currentNode);

                  double priority  = newCost + getCurrentCost(neighborNode, targetNode);

                  frontier.add(neighborNode);
              }
              neighborNode.setCostsoFar(newCost);
          }
      }

      path.add(0, locDB.getByID(targetNode.getNodeId()));




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
