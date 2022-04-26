package edu.wpi.cs3733.D22.teamB.databases;

public class WaitlistObject {

  private String locationID;
  private Location location;
  private String type;
  private String information;
  private int priority;

  public WaitlistObject(String locationID, String type) {
    this.locationID = locationID;
    this.type = type;

    location = getLocation();
  }

  public WaitlistObject(String locationID, Location location, String type) {
    this.locationID = locationID;
    this.location = location;
    this.type = type;
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }

  public Location getLocation() {
    location = DatabaseController.getInstance().getLocationByID(locationID);
    return location;
  }

  public void setLocation(Location location) {
    setLocationID(location.getNodeID());
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public boolean equals(Object o) {
    try {
      ((WaitlistObject) o).getLocationID();
    } catch (Exception e) {
      return false;
    }

    if (((WaitlistObject) o).getLocationID().equals(this.locationID)
        && ((WaitlistObject) o).getType().equals(this.type)) {
      return true;
    }
    return false;
  }
}
