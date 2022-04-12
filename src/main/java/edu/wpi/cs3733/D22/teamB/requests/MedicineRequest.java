package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class MedicineRequest extends Request {

  public MedicineRequest(String patientID, String information, int priority) {
    super(null, patientID, information, priority, "Medicine");
    this.information = information;
  }

  public MedicineRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String type,
      String status,
      int priority,
      String information,
      Date timeCreated,
      Date lastEdited) {
    super(
        requestID,
        employeeID,
        null,
        patientID,
        type,
        status,
        priority,
        information,
        timeCreated,
        lastEdited);
  }

  public final String createRequestID() {
    return "MED" + getHashCode();
  }
}
