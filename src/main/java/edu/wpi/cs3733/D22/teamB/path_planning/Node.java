package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import java.util.LinkedList;

public class Node {
  private String nodeId;
  private double XCoord;
  private double YCoord;
  private int floor;
  private double costSoFar;
  private double priority;
  private Node cameFrom;
  private LinkedList<String> edges;

  Node(Location location, LinkedList<String> edges) {
    this.nodeId = location.getNodeID();
    this.XCoord = location.getXCoord();
    this.YCoord = location.getYCoord();
    this.floor = floorStringToInt(location.getFloor());
    this.edges = edges;
    this.costSoFar = 0;
    this.priority = 0;
  }

  public String getNodeId() {
    return nodeId;
  }

  public double getXCoord() {
    return XCoord;
  }

  public double getYCoord() {
    return YCoord;
  }

  public double getFloor() {
    return this.floor;
  }

  public double getCostsoFar() {
    return costSoFar;
  }

  public void setCostsoFar(double costsoFar) {
    this.costSoFar = costsoFar;
  }

  public double getPriority() {
    return this.priority;
  }

  public void setPriority(double priority) {
    this.priority = priority;
  }

  public LinkedList<String> getEdges() {
    return edges;
  }

  public Node getCameFrom() {
    return this.cameFrom;
  }

  public void setCameFrom(Node cameFrom) {
    this.cameFrom = cameFrom;
  }

  private int floorStringToInt(String floor) {
    int floorNum;

    switch (floor) {
      case "L2":
        floorNum = 0;
        break;
      case "L1":
        floorNum = 1;
        break;
      case "1":
        floorNum = 2;
        break;
      case "2":
        floorNum = 3;
        break;
      case "3":
        floorNum = 4;
        break;
      case "4":
        floorNum = 5;
        break;
      case "5":
        floorNum = 6;
        break;
      default:
        floorNum = -1;
        break;
    }

    return floorNum;
  }
}
