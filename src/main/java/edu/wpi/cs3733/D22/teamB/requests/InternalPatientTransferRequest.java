package edu.wpi.cs3733.D22.teamB.requests;

public class InternalPatientTransferRequest extends Request {

  public InternalPatientTransferRequest(String patientID, String locationID) {
    super(locationID, patientID, "Internal Patient Transfer");
    information =
        "Internal Patient Transfer Request from "
            + getPatient().getOverview()
            + " to "
            + getLocation().getLongName();
  }

  public InternalPatientTransferRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String type,
      String status,
      int priority,
      String information) {
    super(requestID, employeeID, locationID, patientID, type, status, priority, information);
  }

  public final String createRequestID() {
    return "IPT" + getHashCode();
  }
}
