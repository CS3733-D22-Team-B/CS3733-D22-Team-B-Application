package edu.wpi.cs3733.D22.teamB.requests;

public class InterpreterRequest extends Request {

  public InterpreterRequest(String locationID, String information) {
    super(locationID, null, "Interpreter");
    this.information = "Language: " + information;
  }

  public InterpreterRequest(
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
    return "INT" + getHashCode();
  }
}
