package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.LinkedList;

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
        sendDirtyAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID);
        statusDirtyPumpThreeAlert = true;
      } else if (dirtyPumps < 10 && l.getFloor().equals("3")) {
        statusDirtyPumpThreeAlert = false;
      }

      // Check fourth floor areas for dirty pumps
      if (dirtyPumps >= 10 && l.getFloor().equals("4") && !statusDirtyPumpFourAlert) {
        sendDirtyAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID);
        statusDirtyBedFourAlert = true;
      } else if (dirtyPumps < 10 && l.getFloor().equals("4")) {
        statusDirtyPumpFourAlert = false;
      }

      // Check fifth floor areas for dirty pumps
      if (dirtyPumps >= 10 && l.getFloor().equals("5") && !statusDirtyPumpFiveAlert) {
        sendDirtyAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID);
        statusDirtyBedFiveAlert = true;
      } else if (dirtyPumps < 10 && l.getFloor().equals("5")) {
        statusDirtyPumpFiveAlert = false;
      }

      // Check third floor beds for dirty beds
      if (dirtyBeds >= 6 && l.getFloor().equals("3") && !statusDirtyBedThreeAlert) {
        sendDirtyAlert(l.getFloor(), dirtyBeds, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID);
        statusDirtyBedThreeAlert = true;
      } else if (dirtyBeds < 6 && l.getFloor().equals("3")) {
        statusDirtyBedThreeAlert = false;
      }

      // Check fourth floor for dirty beds
      if (dirtyBeds >= 6 && l.getFloor().equals("4") && !statusDirtyBedFourAlert) {
        sendDirtyAlert(l.getFloor(), dirtyBeds, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID);
        statusDirtyBedFourAlert = true;
      } else if (dirtyBeds < 6 && l.getFloor().equals("4")) {
        statusDirtyBedFourAlert = false;
      }

      // Check fifth floor for dirty beds
      if (dirtyBeds >= 6 && l.getFloor().equals("5") && !statusDirtyBedFiveAlert) {
        sendDirtyAlert(l.getFloor(), dirtyBeds, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID);
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
  }

  public void sendDirtyAlert(String floor, int dirtyPumps, String type) {
    System.out.println("Floor: " + floor + " Number dirty: " + dirtyPumps + " Type: " + type);
  }

  public void sendCleanAlert(String floor, int dirtyPumps, String type) {
    System.out.println("Floor: " + floor + " Number clean: " + dirtyPumps + " Type: " + type);
  }

  public void makeServiceRequest(LinkedList<MedicalEquipment> equipment, String locationID) {
    for (MedicalEquipment eq : equipment) {
      String information = "Needs to be sanitized. ";
      EquipmentRequest eqReq =
          new EquipmentRequest(locationID, eq.getEquipmentID(), information, 3);
      DatabaseController.getInstance().add(eqReq);
    }
  }
}
