package edu.wpi.cs3733.D22.teamB.databases;

public class Edge {
  String edgeID;
  String nodeID1;
  String nodeID2;

  Edge(String edgeID, String nodeID1, String nodeID2) {
    this.edgeID = edgeID;
    this.nodeID1 = nodeID1;
    this.nodeID2 = nodeID2;
  }

  public String getEdgeID() {
    return edgeID;
  }

  public String getNodeID1() {
    return nodeID1;
  }

  public void setNodeID1(String nodeID1) {
    this.nodeID1 = nodeID1;
  }

  public String getNodeID2() {
    return nodeID2;
  }

  public void setNodeID2(String nodeID2) {
    this.nodeID2 = nodeID2;
  }
}
