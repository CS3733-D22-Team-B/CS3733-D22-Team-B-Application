package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.LinkedList;

public class AlertController {

  private String ORParkID = "bSTOR001L1";
  private String WestPlazaID = "bSTOR00101";

  public AlertController() {
    checkForAlerts();
  }

  public void checkForAlerts() {
    LocationsDB locDB = LocationsDB.getInstance();
    LinkedList<Location> dirtyList = locDB.listByAttribute("nodeType", "DIRT");
    LinkedList<Location> cleanList = locDB.listByAttribute("nodeType", "STOR");

    for (int i = 0; i < cleanList.size(); i++) {
      if (!cleanList.get(i).getLongName().contains("Clean Equipment")) {
        cleanList.remove(i);
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

      if (dirtyPumps >= 10) {
        sendAlert(l.getFloor(), dirtyPumps, "PUMP");
        makeServiceRequest(dirtyMedEq, WestPlazaID);
      }

      if (dirtyBeds >= 6) {
        sendAlert(l.getFloor(), dirtyPumps, "BED");
        makeServiceRequest(dirtyMedEq, ORParkID);
      }
    }

    //    for (Location l : cleanList) {
    //      LinkedList<MedicalEquipment> cleanMedEq = l.getEquipmentList();
    //      int cleanPumps = 0;
    //
    //      for (int i = 0; i < cleanMedEq.size(); i++) {
    //        if (cleanMedEq.get(i).getType().equals("PUMP") && cleanMedEq.get(i).getIsClean() ==
    // true) {
    //          cleanPumps++;
    //        }
    //      }
    //
    //      if (cleanPumps < 5) {
    //        sendAlert(l.getFloor(), cleanPumps, "PUMP");
    //      }
    //    }
  }

  public void sendAlert(String floor, int dirtyPumps, String type) {
    System.out.println("Floor: " + floor + "Number dirty/clean: " + dirtyPumps + "Type: " + type);
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
