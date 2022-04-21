package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequest;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import java.util.Date;

public class SanitationRequest extends EquipmentRequest {

  private DatabaseController DC = DatabaseController.getInstance();

  public SanitationRequest(String equipmentID, String information, int priority, String type) {
    super(null, equipmentID, information, priority);
    this.type = "Sanitation";
  }

  public SanitationRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String equipmentID,
      String testType,
      Date testDate,
      String type,
      String status,
      int priority,
      String information,
      Date timeCreated,
      Date lastEdited) {
    super(
        requestID,
        employeeID,
        locationID,
        patientID,
        equipmentID,
        testType,
        testDate,
        type,
        status,
        priority,
        information,
        timeCreated,
        lastEdited);
  }

  public final String createRequestID() {
    return "SAN" + getHashCode();
  }

  public void updateMedicalEquipmentStatus() {
    MedicalEquipment medEq = getMedicalEquipment();
    if (this.status.equals("Completed")) {
      medEq.setIsClean(true);
      medEq.setAvailability("Available");
      medEq.moveToClean();
      DatabaseController DC = DatabaseController.getInstance();
      DC.update(medEq);
    }
  }
}
