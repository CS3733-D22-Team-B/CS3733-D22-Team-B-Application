package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class InterpreterRequest extends Request {

  public InterpreterRequest(String locationID, String information, int priority) {
    super(locationID, null, information, priority, "Interpreter");
    this.information = information;
  }

  public InterpreterRequest(
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
        locationID,
        null,
        type,
        status,
        priority,
        information,
        timeCreated,
        lastEdited);
  }

  public final String createRequestID() {
    return "INT" + getHashCode();
  }
}
