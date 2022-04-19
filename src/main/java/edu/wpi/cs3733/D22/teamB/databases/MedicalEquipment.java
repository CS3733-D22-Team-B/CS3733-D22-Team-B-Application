package edu.wpi.cs3733.D22.teamB.databases;

public class MedicalEquipment {

  private String equipmentID;
  private String nodeID;
  private Location location;
  private String type;
  private boolean isClean;
  private String availability;
  private String name;

  // Pending, In-Progress, Complete

  public MedicalEquipment(
      String equipmentID,
      String nodeID,
      String type,
      boolean isClean,
      String availability,
      String name) {
    setEquipmentID(equipmentID);
    setNodeID(nodeID);
    setType(type);
    setIsClean(isClean);
    setAvailability(availability);
    setName(name);
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
    this.setAvailability("Unavailable");
    if(newIsClean == false) {
      this.moveToDirty();
    }
  }

  public boolean getIsClean() {
    return isClean;
  }

  public String getIsSterilized() {
    return (isClean) ? "\u2713" : "\u2717";
  }

  public void setAvailability(String newAvailability) {
    availability = newAvailability;
  }

  public String getAvailability() {
    return availability;
  }

  public void setName(String newName) {
    name = newName;
  }

  public String getName() {
    return name;
  }

  public void moveToDirty() {
    Location loc = getLocation();
    String floor = loc.getFloor();

    switch (floor) {
      case "3":
        this.setNodeID("bDIRT00103");
        break;
        //      case "4":
        //        this.setNodeID("bDIRT00104");
        //        break;
        //      case "5":
        //        this.setNodeID("bDIRT00105");
        //        break;
      default:
        this.setNodeID("bDIRT00103");
    }
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
