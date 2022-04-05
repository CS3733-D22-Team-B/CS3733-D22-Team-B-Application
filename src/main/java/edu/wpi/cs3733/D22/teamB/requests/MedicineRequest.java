package edu.wpi.cs3733.D22.teamB.requests;

public class MedicineRequest extends Request {

  public MedicineRequest(String locationID, String information) {
    super(locationID, "Medicine");
    this.information = "Medicine Request: " + information;
  }

  public MedicineRequest(
      String requestID,
      String employeeID,
      String locationID,
      String type,
      String status,
      String information) {
    super(requestID, type, employeeID, locationID, status, information);
  }

  public final String createRequestID() {
    return "MED" + getHashCode();
  }
}
