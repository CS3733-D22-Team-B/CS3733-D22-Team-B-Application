package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public class Location {
  private String nodeID;
  private int xCoord;
  private int yCoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;
  private boolean availability;

  private LinkedList<MedicalEquipment> equipmentList;

  public Location(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      boolean availability) {
    setNodeID(nodeID);
    setXCoord(xCoord);
    setYCoord(yCoord);
    setFloor(floor);
    setBuilding(building);
    setNodeType(nodeType);
    setLongName(longName);
    setShortName(shortName);
    setAvailability(availability);
  }

  private void setNodeID(String newNodeID) {
    nodeID = newNodeID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setXCoord(int newXCoord) {
    xCoord = newXCoord;
  }

  public int getXCoord() {
    return xCoord;
  }

  public void setYCoord(int newYCoord) {
    yCoord = newYCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public void setFloor(String newFloor) {
    floor = newFloor;
  }

  public String getFloor() {
    return floor;
  }

  public void setBuilding(String newBuilding) {
    building = newBuilding;
  }

  public String getBuilding() {
    return building;
  }

  public void setNodeType(String newNodeType) {
    nodeType = newNodeType;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setLongName(String newLongName) {
    longName = newLongName;
  }

  public String getLongName() {
    return longName;
  }

  public void setShortName(String newShortName) {
    shortName = newShortName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setAvailability(boolean newAvailability) {
    availability = newAvailability;
  }

  public boolean getAvailability() {
    return availability;
  }

  public LinkedList<MedicalEquipment> getEquipmentList() {
    MedicalEquipmentDB medEqDB = MedicalEquipmentDB.getInstance();
    equipmentList = medEqDB.listByAttribute("nodeID", getNodeID());

    return equipmentList;
  }
}
