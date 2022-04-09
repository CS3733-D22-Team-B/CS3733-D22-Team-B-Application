package edu.wpi.cs3733.D22.teamB.requests;

public class CustomRequest extends Request {

  public CustomRequest(String locationID, String requestType, String information) {
    super(locationID, requestType);
    this.information = "Custom Request: " + information;
  }

  public CustomRequest(
      String requestID,
      String employeeID,
      String locationID,
      String type,
      String status,
      String information) {
    super(requestID, type, employeeID, locationID, status, information);
  }

  public final String createRequestID() {
    return "CUS" + getHashCode();
  }
}
