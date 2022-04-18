package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class SecurityRequest extends Request {

  public SecurityRequest(String locationID, String information, int priority) {
    super(locationID, null, information, priority, "Security");
    this.information = information;
  }

  public SecurityRequest(
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
        null,
        null,
        null,
        type,
        status,
        priority,
        information,
        timeCreated,
        lastEdited);
  }

  public final String createRequestID() {
    return "SEC" + getHashCode();
  }
}
