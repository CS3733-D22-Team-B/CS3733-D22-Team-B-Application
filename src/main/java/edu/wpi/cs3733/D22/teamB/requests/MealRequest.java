package edu.wpi.cs3733.D22.teamB.requests;

public class MealRequest extends Request {

  public MealRequest(String patientID, String information) {
    super(null, patientID, "Meal");
    this.information = "Meal: " + information;
  }

  public MealRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String type,
      String status,
      int priority,
      String information) {
    super(requestID, employeeID, null, patientID, type, status, priority, information);
  }

  public final String createRequestID() {
    return "MEL" + getHashCode();
  }
}
