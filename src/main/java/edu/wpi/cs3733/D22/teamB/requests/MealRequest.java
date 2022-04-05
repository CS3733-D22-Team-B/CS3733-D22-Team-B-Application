package edu.wpi.cs3733.D22.teamB.requests;

public class MealRequest extends Request {

  public MealRequest(String locationID, String information) {
    super(locationID, "Meal");
    this.information = "Meal: " + information;
  }

  public MealRequest(
      String requestID,
      String employeeID,
      String locationID,
      String type,
      String status,
      String information) {
    super(requestID, type, employeeID, locationID, status, information);
  }

  public final String createRequestID() {
    return "MEL" + getHashCode();
  }
}
