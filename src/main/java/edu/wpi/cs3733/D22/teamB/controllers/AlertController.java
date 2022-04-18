package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.LinkedList;

public class AlertController {

  public AlertController() {
    checkForAlerts();
  }

  public void checkForAlerts() {
    LocationsDB locDB = LocationsDB.getInstance();
    LinkedList<Location> dirtyList = locDB.listByAttribute("nodeType", "DIRT");
    // LinkedList<Location> cleanList = locDB.listByAttribute("nodeType", "STOR");

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
        makeDirtyPumpServiceRequest(dirtyMedEq);
      }
      //      } else if (cleanPumps < 5){
      //        //send alert
      //      }

      if (dirtyBeds >= 6) {
        sendAlert(l.getFloor(), dirtyPumps, "BED");
      }
    }
  }

  public void sendAlert(String floor, int dirtyPumps, String type) {
    System.out.println("Floor: " + floor + "Number dirty/clean: " + dirtyPumps + "Type: " + type);
  }

  public void makeDirtyPumpServiceRequest(LinkedList<MedicalEquipment> equipment) {
    for (MedicalEquipment eq : equipment) {
      String information = "Needs to be sanitized. ";
      EquipmentRequest eqReq =
          new EquipmentRequest("bSTOR00101", eq.getEquipmentID(), information, 3);
      DatabaseController.getInstance().add(eqReq);
    }
  }
}
