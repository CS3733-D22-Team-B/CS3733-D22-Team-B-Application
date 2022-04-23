package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.SanitationRequest;
import java.util.LinkedList;

public class EquipmentAlert {
  private String alertID;
  private String floor;
  private String type;

  private LinkedList<SanitationRequest> requests = new LinkedList<SanitationRequest>();

  DatabaseController DC = DatabaseController.getInstance();

  public EquipmentAlert(String floor, String type) {
    this.floor = floor;
    this.type = type;
    this.alertID = "ALT" + getHashCode();
  }

  public String getAlertID() {
    return alertID;
  }

  private void setAlertID(String alertID) {
    this.alertID = alertID;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c = floor.hashCode() + type.hashCode();

    // calculate the hash
    int hash = (int) (37 * result + c);

    // convert hash to string
    String hashCode = Integer.toString(Math.abs(hash));

    // pad with zeros
    while (hashCode.length() < 6) {
      hashCode = "0" + hashCode;
    }

    return hashCode.substring(0, 6);
  }

  public void addSanitationRequest(SanitationRequest req) {
    requests.add(req);
    req.setMyAlert(this);
  }

  public void removeSanitationRequest(SanitationRequest req) {
    requests.remove(req);
  }

  public void updateDirty() {
    if (this.requests.size() == 0 && this.type.contains("DIRTY")) {
      AlertQueue.removeAlert(this);
    }
  }

  public void updateClean() {
    if (this.type.contains("CLEAN") == false) {
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

    if (this.floor.equals("3") && cleanPumpsThree >= 5) {
      AlertQueue.removeAlert(this);
    } else if (this.floor.equals("4") && cleanPumpsFour >= 5) {
      AlertQueue.removeAlert(this);
    } else if (this.floor.equals("5") && cleanPumpsFive >= 5) {
      AlertQueue.removeAlert(this);
    }
  }

  public boolean equals(Object o) {
    try {
      ((EquipmentAlert) o).getAlertID();
    } catch (Exception e) {
      return false;
    }

    if (this.alertID.equals(((EquipmentAlert) o).getAlertID())) {
      return true;
    }
    return false;
  }
}
