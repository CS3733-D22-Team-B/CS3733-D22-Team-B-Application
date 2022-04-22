package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.SanitationRequest;
import java.io.IOException;
import java.util.LinkedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertController {

  // TODO make these work:
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
    LinkedList<Location> cleanListInit = locDB.listByAttribute("nodeType", "STOR");
    LinkedList<Location> cleanList = new LinkedList<Location>();

    for (int i = 0; i < cleanListInit.size(); i++) {
      if (cleanListInit.get(i).getLongName().contains("Clean Equipment")) {
        cleanList.add(cleanListInit.get(i));
      }
    }

    for (Location l : dirtyList) {
      LinkedList<MedicalEquipment> dirtyMedEq = l.getEquipmentList();
      int dirtyPumps = 0;
      int dirtyBeds = 0;

      for (int i = 0; i < dirtyMedEq.size(); i++) {
        if (dirtyMedEq.get(i).getType().equals("PUMP") && dirtyMedEq.get(i).getIsClean() == false) {
          dirtyPumps++;
        } else if (dirtyMedEq.get(i).getType().equals("BED")
            && dirtyMedEq.get(i).getIsClean() == false) {
          dirtyBeds++;
        }
      }

      // Check third floor areas for dirty pumps
      if (dirtyPumps >= 10 && l.getFloor().equals("3") && !statusDirtyPumpThreeAlert) {
        Alert alert = sendDirtyAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID, alert);
        statusDirtyPumpThreeAlert = true;
      } else if (dirtyPumps < 10 && l.getFloor().equals("3")) {
        statusDirtyPumpThreeAlert = false;
      }

      // Check fourth floor areas for dirty pumps
      if (dirtyPumps >= 10 && l.getFloor().equals("4") && !statusDirtyPumpFourAlert) {
        Alert alert = sendDirtyAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID, alert);
        statusDirtyBedFourAlert = true;
      } else if (dirtyPumps < 10 && l.getFloor().equals("4")) {
        statusDirtyPumpFourAlert = false;
      }

      // Check fifth floor areas for dirty pumps
      if (dirtyPumps >= 10 && l.getFloor().equals("5") && !statusDirtyPumpFiveAlert) {
        Alert alert = sendDirtyAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID, alert);
        statusDirtyBedFiveAlert = true;
      } else if (dirtyPumps < 10 && l.getFloor().equals("5")) {
        statusDirtyPumpFiveAlert = false;
      }

      // Check third floor beds for dirty beds
      if (dirtyBeds >= 6 && l.getFloor().equals("3") && !statusDirtyBedThreeAlert) {
        Alert alert = sendDirtyAlert(l.getFloor(), dirtyBeds, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID, alert);
        statusDirtyBedThreeAlert = true;
      } else if (dirtyBeds < 6 && l.getFloor().equals("3")) {
        statusDirtyBedThreeAlert = false;
      }

      // Check fourth floor for dirty beds
      if (dirtyBeds >= 6 && l.getFloor().equals("4") && !statusDirtyBedFourAlert) {
        Alert alert = sendDirtyAlert(l.getFloor(), dirtyBeds, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID, alert);
        statusDirtyBedFourAlert = true;
      } else if (dirtyBeds < 6 && l.getFloor().equals("4")) {
        statusDirtyBedFourAlert = false;
      }

      // Check fifth floor for dirty beds
      if (dirtyBeds >= 6 && l.getFloor().equals("5") && !statusDirtyBedFiveAlert) {
        Alert alert = sendDirtyAlert(l.getFloor(), dirtyBeds, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID, alert);
        statusDirtyBedFiveAlert = true;
      } else if (dirtyBeds < 6 && l.getFloor().equals("5")) {
        statusDirtyBedFiveAlert = false;
      }
    }

    // We initialize clean pumps outside of the for loop bc this is checked per floor, not per
    // location
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

    if (cleanPumpsThree < 5 && !statusCleanPumpsThreeAlert) {
      sendCleanAlert("3", cleanPumpsThree, "PUMP");
      statusCleanPumpsThreeAlert = true;
    } else if (cleanPumpsThree >= 5 && statusCleanPumpsThreeAlert) {
      statusCleanPumpsThreeAlert = false;
    }

    if (cleanPumpsFour < 5 && !statusCleanPumpsFourAlert) {
      sendCleanAlert("4", cleanPumpsFour, "PUMP");
      statusCleanPumpsFourAlert = true;
    } else if (cleanPumpsFour >= 5 && statusCleanPumpsFourAlert) {
      statusCleanPumpsFourAlert = false;
    }

    if (cleanPumpsFive < 5 && !statusCleanPumpsFiveAlert) {
      sendCleanAlert("5", cleanPumpsFive, "PUMP");
      statusCleanPumpsFiveAlert = true;
    } else if (cleanPumpsFive >= 5 && statusCleanPumpsFiveAlert) {
      statusCleanPumpsFiveAlert = false;
    }

    // Remove any outdated alerts from the queue
    updateAlerts();
  }

  public Alert sendDirtyAlert(String floor, int dirtyPumps, String type) {
    System.out.println("Floor: " + floor + " Number dirty: " + dirtyPumps + " Type: " + type);
    Alert alert = new Alert("", ("Floor " + floor), ("DIRTY_" + type), "N");
    AlertQueue.addAlert(alert);
    showAlert(alert);
    return alert;
  }

  public Alert sendCleanAlert(String floor, int dirtyPumps, String type) {
    System.out.println("Floor: " + floor + " Number clean: " + dirtyPumps + " Type: " + type);
    Alert alert = new Alert("", ("Floor " + floor), ("CLEAN_" + type), "N");
    AlertQueue.addAlert(alert);
    showAlert(alert);
    return alert;
  }

  public void makeServiceRequest(
      LinkedList<MedicalEquipment> equipment, String locationID, Alert alert) {
    for (MedicalEquipment eq : equipment) {
      String information = "Needs to be sanitized. ";
      SanitationRequest eqReq =
          new SanitationRequest(eq.getEquipmentID(), information, 3, "Sanitation");
      DatabaseController.getInstance().add(eqReq);
      alert.addSanitationRequest(eqReq);
    }
  }

  public void updateAlerts() {
    LinkedList<Alert> alertList = AlertQueue.getAlerts();
    for (Alert alert : alertList) {
      alert.updateDirty();
      alert.updateClean();
    }
  }

  public void showAlert(Alert changeStateAlert) {
    UIController currentPlace = UIController.getInstance();
    Scene place = currentPlace.primaryStage.getScene();

    Stage popUpStage = new Stage();
    Parent root;
    root = null;
    if (changeStateAlert.getType().equals("CLEAN_PUMP")) {

      try {
        root =
            FXMLLoader.load(
                getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/popupCLP.fxml"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      popUpStage.setScene(new Scene(root));
      popUpStage.initOwner(place.getWindow());
      popUpStage.initModality(Modality.APPLICATION_MODAL); // popup
      popUpStage.showAndWait();
    }
    if (changeStateAlert.getType().equals("DIRTY_PUMP")) {
      try {
        root =
            FXMLLoader.load(
                getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/popupDTP.fxml"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      popUpStage.setScene(new Scene(root));
      popUpStage.initOwner(place.getWindow());
      popUpStage.initModality(Modality.APPLICATION_MODAL); // popup
      popUpStage.showAndWait();
    }
    if (changeStateAlert.getType().equals("DIRTY_BED")) {
      try {
        root =
            FXMLLoader.load(
                getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/popupDTB.fxml"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      popUpStage.setScene(new Scene(root));
      popUpStage.initOwner(place.getWindow());
      popUpStage.initModality(Modality.APPLICATION_MODAL); // popup
      popUpStage.showAndWait();
    }
  }
}
