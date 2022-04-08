package edu.wpi.cs3733.D22.teamB.requests;

public class CustomRequest extends Request {

  public CustomRequest(
      String locationID, String patientID, String requestType, String information) {
    super(locationID, patientID, requestType);
    this.information = "Custom Request: " + information;
  }

  public CustomRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String type,
      String status,
      int priority,
      String information) {
    super(requestID, type, employeeID, locationID, patientID, status, priority, information);
  }

  public final String createRequestID() {
    return "CUS" + getHashCode();
  }
}
