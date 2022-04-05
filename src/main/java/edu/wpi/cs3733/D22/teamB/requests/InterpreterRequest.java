package edu.wpi.cs3733.D22.teamB.requests;

public class InterpreterRequest extends Request {

  public InterpreterRequest(String locationID, String information) {
    super(locationID, "Interpreter");
    this.information = "Language: " + information;
  }

  public InterpreterRequest(
      String requestID,
      String employeeID,
      String locationID,
      String type,
      String status,
      String information) {
    super(requestID, type, employeeID, locationID, status, information);
  }

  public final String createRequestID() {
    return "INT" + getHashCode();
  }
}
