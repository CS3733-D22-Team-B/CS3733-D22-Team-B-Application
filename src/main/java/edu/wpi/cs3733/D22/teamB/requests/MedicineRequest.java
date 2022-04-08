package edu.wpi.cs3733.D22.teamB.requests;

public class MedicineRequest extends Request {

  public MedicineRequest(String patientID, String information) {
    super(null, patientID, "Medicine");
    this.information = "Medicine Request: " + information;
  }

  public MedicineRequest(
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
    return "MED" + getHashCode();
  }
}
