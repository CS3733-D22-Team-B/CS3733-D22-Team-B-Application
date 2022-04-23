package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.SanitationRequest;
import java.util.LinkedList;

public class Alert {
  private String alertID;
  private String locationID;
  private Location location;
  private String type;
  private String isRead;

  private LinkedList<SanitationRequest> requests = new LinkedList<SanitationRequest>();

  DatabaseController DC = DatabaseController.getInstance();

  public Alert(String alertID, String locationID, String type, String isRead) {
    this.alertID = alertID;
    this.locationID = locationID;
    this.type = type;
    this.isRead = isRead;
  }

  public String getAlertID() {
    return alertID;
  }

  private void setAlertID(String alertID) {
    this.alertID = alertID;
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }

  public Location getLocation() {
    location = DC.getLocationByID(getLocationID());
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIsRead() {
    return isRead;
  }

  public void setIsRead(String isRead) {
    this.isRead = isRead;
  }

  public void addSanitationRequest(SanitationRequest req) {
    requests.add(req);
    req.setMyAlert(this);
  }

  public void removeSanitationRequest(SanitationRequest req) {
    requests.remove(req);
  }

  public void updateDirty() {
    if (this.requests.size() == 0) {
      AlertQueue.removeAlert(this);
    }
  }

  public void updateClean() {
    if (this.type.equals("CLEAN_PUMP") == false) {
      return;
    }

    LinkedList<Location> cleanListInit =
        DC.getInstance().listLocationsByAttribute("nodeType", "STOR");
    LinkedList<Location> cleanList = new LinkedList<Location>();

    for (int i = 0; i < cleanListInit.size(); i++) {
      if (cleanListInit.get(i).getLongName().contains("Clean Equipment")) {
        cleanList.add(cleanListInit.get(i));
      }
    }

    int cleanPumpsThree = 0;
    int cleanPumpsFour = 0;
    int cleanPumpsFive = 0;

    for (Location l : cleanList) {
      LinkedList<MedicalEquipment> cleanMedEq = l.getEquipmentList();

      for (int i = 0; i < cleanMedEq.size(); i++) {
        if (cleanMedEq.get(i).getType().equals("PUMP") && cleanMedEq.get(i).getIsClean()) {
          switch (l.getFloor()) {
            case "3":
              cleanPumpsThree++;
              break;
            case "4":
              cleanPumpsFour++;
              break;
            case "5":
              cleanPumpsFive++;
              break;
            default:
              break;
          }
        }
      }
    }

    if (this.type.substring(type.length() - 1).equals("3") && cleanPumpsThree >= 5) {
      AlertQueue.removeAlert(this);
    } else if (this.type.substring(type.length() - 1).equals("4") && cleanPumpsFour >= 5) {
      AlertQueue.removeAlert(this);
    } else if (this.type.substring(type.length() - 1).equals("5") && cleanPumpsFive >= 5) {
      AlertQueue.removeAlert(this);
    }
  }

  ////////////////////////// Location /////////////////////////////
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

  public boolean equals(Object o) {
    try {
      ((Alert) o).getLocationID();
    } catch (Exception e) {
      return false;
    }

    if (this.locationID.equals(((Alert) o).getLocationID())
        && this.type.equals(((Alert) o).getType())) {
      return true;
    }
    return false;
  }
}
