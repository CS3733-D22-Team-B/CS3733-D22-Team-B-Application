package edu.wpi.cs3733.D22.teamB.databases;

public class MedicalEquipment {

  private String equipmentID;
  private String nodeID;
  private String location;
  private String type;
  private boolean isClean;
  private boolean isRequested;

  // Requested, Processing, Complete

  public MedicalEquipment(
      String equipmentID, String nodeID, String type, boolean isClean, boolean isRequested) {
    setEquipmentID(equipmentID);
    setNodeID(nodeID);
    setType(type);
    setIsClean(isClean);
    setIsRequested(isRequested);
  }

  public void setEquipmentID(String newEquipmentID) {
    equipmentID = newEquipmentID;
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public void setNodeID(String newNodeID) {
    nodeID = newNodeID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setType(String newType) {
    type = newType;
  }

  public String getType() {
    return type;
  }

  public void setIsClean(boolean newIsClean) {
    isClean = newIsClean;
  }

  public boolean getIsClean() {
    return isClean;
  }

  public void setIsRequested(boolean newIsRequested) {
    isRequested = newIsRequested;
  }

  public boolean getIsRequested() {
    return isRequested;
  }

  public void setLocation(String newLocation) {
    location = newLocation;
  }

  public String getLocation() {
    return location;
  }
}
