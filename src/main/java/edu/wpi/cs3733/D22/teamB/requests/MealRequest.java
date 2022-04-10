package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

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
    return "MEL" + getHashCode();
  }
}
