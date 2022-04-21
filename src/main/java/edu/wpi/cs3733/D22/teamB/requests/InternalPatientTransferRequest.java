package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class InternalPatientTransferRequest extends Request {

  public InternalPatientTransferRequest(
      String patientID, String locationID, String information, int priority) {
    super(locationID, patientID, information, priority, "Patient Transfer");
    this.information =
        "Internal Patient Transfer Request from "
            + getPatient().getOverview()
            + " to "
            + getLocation().getLongName()
            + "\n\nAdditional Information: "
            + information;
  }

  public InternalPatientTransferRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String equipmentID,
      String testType,
      Date testDate,
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
        patientID,
        null,
        null,
        null,
        type,
        status,
        priority,
        information,
        timeCreated,
        lastEdited);
  }

  public final String createRequestID() {
    return "IPT" + getHashCode();
  }
}
