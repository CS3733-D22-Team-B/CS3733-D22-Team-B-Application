package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class CustomRequest extends Request {

  public CustomRequest(
      String locationID, String patientID, String requestType, String information, int priority) {
    super(locationID, patientID, information, priority, requestType);
    this.information = "Custom Request: " + information;
  }

  public CustomRequest(
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
    return "CUS" + getHashCode();
  }
}
