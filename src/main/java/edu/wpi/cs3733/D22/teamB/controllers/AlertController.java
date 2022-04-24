package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.SanitationRequest;
import java.util.LinkedList;
import javafx.scene.control.Alert;

public class AlertController {

  private boolean statusDirtyBedThreeAlert = false;
  private boolean statusDirtyBedFourAlert = false;
  private boolean statusDirtyBedFiveAlert = false;

  private boolean statusDirtyPumpThreeAlert = false;
  private boolean statusDirtyPumpFourAlert = false;
  private boolean statusDirtyPumpFiveAlert = false;

  private boolean statusCleanPumpsThreeAlert = false;
  private boolean statusCleanPumpsFourAlert = false;
  private boolean statusCleanPumpsFiveAlert = false;

  private String ORParkID = "bSTOR001L1";
  private String WestPlazaID = "bSTOR00101";

  private AlertController() {}

  private static AlertController AlertControllerManager;

  public static AlertController getInstance() {
    if (AlertControllerManager == null) {
      AlertControllerManager = new AlertController();
    }
    return AlertControllerManager;
  }

  public void checkForAlerts() {
    LocationsDB locDB = LocationsDB.getInstance();
    LinkedList<Location> dirtyList = locDB.listByAttribute("nodeType", "DIRT");
    LinkedList<Location> storageLocationsList = locDB.listByAttribute("nodeType", "STOR");
    LinkedList<Location> cleanList = new LinkedList<Location>();

    for (int i = 0; i < storageLocationsList.size(); i++) {
      if (storageLocationsList.get(i).getLongName().contains("Clean Equipment")) {
        cleanList.add(storageLocationsList.get(i));
      }
    }

    for (Location location : dirtyList) {
      LinkedList<MedicalEquipment> dirtyMedEq = location.getEquipmentList();
      int dirtyPumps = 0;
      int dirtyBeds = 0;

      for (int i = 0; i < dirtyMedEq.size(); i++) {
        if (!dirtyMedEq.get(i).getIsClean()) {
          if (dirtyMedEq.get(i).getType().equals("PUMP")) {
            dirtyPumps++;
          } else if (dirtyMedEq.get(i).getType().equals("BED")) {
            dirtyBeds++;
          }
        }
      }

      // Check third floor areas for dirty pumps
      switch (location.getFloor()) {
        case "3":
          if (dirtyPumps >= 10 && !statusDirtyPumpThreeAlert) {
            EquipmentAlert alert = sendDirtyAlert("3", "PUMP");
            makeServiceRequest(dirtyMedEq, WestPlazaID, alert);
            statusDirtyPumpThreeAlert = true;
          } else if (dirtyPumps < 10) {
            statusDirtyPumpThreeAlert = false;
          }
          if (dirtyBeds >= 6 && !statusDirtyBedThreeAlert) {
            EquipmentAlert alert = sendDirtyAlert("3", "BED");
            makeServiceRequest(dirtyMedEq, ORParkID, alert);
            statusDirtyBedThreeAlert = true;
          } else if (dirtyBeds < 6) {
            statusDirtyBedThreeAlert = false;
          }
          break;
        case "4":
          if (dirtyPumps >= 10 && !statusDirtyPumpFourAlert) {
            EquipmentAlert alert = sendDirtyAlert("4", "PUMP");
            makeServiceRequest(dirtyMedEq, WestPlazaID, alert);
            statusDirtyBedFourAlert = true;
          } else if (dirtyPumps < 10) {
            statusDirtyPumpFourAlert = false;
          }
          if (dirtyBeds >= 6 && !statusDirtyBedFourAlert) {
            EquipmentAlert alert = sendDirtyAlert("4", "BED");
            makeServiceRequest(dirtyMedEq, ORParkID, alert);
            statusDirtyBedFourAlert = true;
          } else if (dirtyBeds < 6) {
            statusDirtyBedFourAlert = false;
          }
          break;
        case "5":
          if (dirtyPumps >= 10 && !statusDirtyPumpFiveAlert) {
            EquipmentAlert alert = sendDirtyAlert("5", "PUMP");
            makeServiceRequest(dirtyMedEq, WestPlazaID, alert);
            statusDirtyBedFiveAlert = true;
          } else if (dirtyPumps < 10) {
            statusDirtyPumpFiveAlert = false;
          }
          if (dirtyBeds >= 6 && !statusDirtyBedFiveAlert) {
            EquipmentAlert alert = sendDirtyAlert("5", "BED");
            makeServiceRequest(dirtyMedEq, ORParkID, alert);
            statusDirtyBedFiveAlert = true;
          } else if (dirtyBeds < 6) {
            statusDirtyBedFiveAlert = false;
          }
          break;
        default:
          break;
      }
    }

    // We initialize clean pumps outside of the for loop bc this is checked per floor, not per
    // location
    int cleanPumpsThree = 0;
    int cleanPumpsFour = 0;
    int cleanPumpsFive = 0;

    for (Location location : cleanList) {
      LinkedList<MedicalEquipment> cleanMedEq = location.getEquipmentList();

      for (int i = 0; i < cleanMedEq.size(); i++) {
        if (cleanMedEq.get(i).getType().equals("PUMP") && cleanMedEq.get(i).getIsClean()) {
          switch (location.getFloor()) {
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

    if (cleanPumpsThree < 5 && !statusCleanPumpsThreeAlert) {
      sendCleanAlert("3", "PUMP");
      statusCleanPumpsThreeAlert = true;
    } else if (cleanPumpsThree >= 5 && statusCleanPumpsThreeAlert) {
      statusCleanPumpsThreeAlert = false;
    }

    if (cleanPumpsFour < 5 && !statusCleanPumpsFourAlert) {
      sendCleanAlert("4", "PUMP");
      statusCleanPumpsFourAlert = true;
    } else if (cleanPumpsFour >= 5 && statusCleanPumpsFourAlert) {
      statusCleanPumpsFourAlert = false;
    }

    if (cleanPumpsFive < 5 && !statusCleanPumpsFiveAlert) {
      sendCleanAlert("5", "PUMP");
      statusCleanPumpsFiveAlert = true;
    } else if (cleanPumpsFive >= 5 && statusCleanPumpsFiveAlert) {
      statusCleanPumpsFiveAlert = false;
    }

    // Remove any outdated alerts from the queue
    updateAlerts();
  }

  public EquipmentAlert sendDirtyAlert(String floor, String type) {
    EquipmentAlert alert = new EquipmentAlert(floor, "DIRTY " + type);
    AlertQueue.addAlert(alert);
    showAlert(alert);
    return alert;
  }

  public EquipmentAlert sendCleanAlert(String floor, String type) {
    EquipmentAlert alert = new EquipmentAlert(floor, "CLEAN " + type);
    AlertQueue.addAlert(alert);
    showAlert(alert);
    return alert;
  }

  public void makeServiceRequest(
      LinkedList<MedicalEquipment> equipment, String locationID, EquipmentAlert alert) {
    for (MedicalEquipment eq : equipment) {
      String information = "Needs to be sanitized.";
      SanitationRequest eqReq =
          new SanitationRequest(eq.getEquipmentID(), information, 5, "Sanitation");
      DatabaseController.getInstance().add(eqReq);
      alert.addSanitationRequest(eqReq);
    }
  }

  public void updateAlerts() {
    LinkedList<EquipmentAlert> alertList = AlertQueue.getAlerts();
    for (EquipmentAlert alert : alertList) {
      alert.updateDirty();
      alert.updateClean();
    }
  }

  public void showAlert(EquipmentAlert changeStateAlert) {
    Alert alert;

    switch (changeStateAlert.getType()) {
      case "DIRTY BED":
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dirty Bed Alert");
        alert.setHeaderText(
            "There are too many dirty beds on floor " + changeStateAlert.getFloor());
        alert.setContentText("Maybe you should clean them.");
        alert.showAndWait();
        break;
      case "DIRTY PUMP":
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dirty Pump Alert");
        alert.setHeaderText(
            "There are too many dirty pumps on floor " + changeStateAlert.getFloor());
        alert.setContentText("Maybe you should clean them.");
        alert.showAndWait();
        break;
      case "CLEAN PUMP":
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Clean Pump Alert");
        alert.setHeaderText(
            "There are too few clean pumps on floor " + changeStateAlert.getFloor());
        alert.setContentText("Maybe you should clean some dirty ones.");
        alert.showAndWait();
        break;
      default:
        break;
    }
  }
}
