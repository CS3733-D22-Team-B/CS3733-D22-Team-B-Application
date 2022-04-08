package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.Location;

import java.util.LinkedList;

public class Node {
    private double xCoord;
    private double yCoord;
    private double costSoFar;
    private double priority;
    private Node cameFrom;
    private LinkedList<String> edges;

    Node(Location location, LinkedList<String> edges){
        this.xCoord = location.getXCoord();
        this.yCoord = location.getYCoord();
        this.edges = edges;
        this.costSoFar = 0;
        this.priority = 0;
    }

    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public double getCostsoFar() {
        return costSoFar;
    }

    public void setCostsoFar(double costsoFar) {
        this.costSoFar = costsoFar;
    }

    public double getPriority(){
        return this.priority;
    }

    public LinkedList<String> getEdges() {
        return edges;
    }

    public void setCameFrom(Node cameFrom){
        this.cameFrom = cameFrom;
    }
}
