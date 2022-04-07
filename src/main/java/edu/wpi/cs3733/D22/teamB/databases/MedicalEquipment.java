package edu.wpi.cs3733.D22.teamB.databases;

public class MedicalEquipment {

  private String equipmentID;
  private String nodeID;
  private Location location;
  private String type;
  private boolean isClean;
  private String isSterilized;
  private boolean isRequested;

  // Pending, In-Progress, Complete

  public MedicalEquipment(
      String equipmentID, String nodeID, String type, boolean isClean, boolean isRequested) {
    setEquipmentID(equipmentID);
    setNodeID(nodeID);
    setType(type);
    setIsClean(isClean);
    setIsRequested(isRequested);
  }

  private void setEquipmentID(String newEquipmentID) {
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

  public void setLocation(Location l) {
    setNodeID(l.getNodeID());
  }

  public Location getLocation() {
    LocationsDB locDB = LocationsDB.getInstance();
    location = locDB.getByID(nodeID);
    return location;
  }

  public void setType(String newType) {
    type = newType;
  }

  public String getType() {
    return type;
  }

  public void setIsClean(boolean newIsClean) {
    isClean = newIsClean;
    isSterilized = (isClean) ? "\u2713" : "\u2717";
  }

  public boolean getIsClean() {
    return isClean;
  }

  public String getIsSterilized() {
    return isSterilized;
  }

  public void setIsRequested(boolean newIsRequested) {
    isRequested = newIsRequested;
  }

  public boolean getIsRequested() {
    return isRequested;
  }

  /////////////////// LOCATION GETTERS////////////////////
  public int getXCoord() {
    return location.getXCoord();
  }

  public int getYCoord() {
    return location.getYCoord();
  }

  public String getFloor() {
    return location.getFloor();
  }

  public String getBuilding() {
    return location.getBuilding();
  }

  public String getNodeType() {
    return location.getNodeType();
  }

  public String getLongName() {
    return location.getLongName();
  }

  public String getShortName() {
    return location.getShortName();
  }
}
