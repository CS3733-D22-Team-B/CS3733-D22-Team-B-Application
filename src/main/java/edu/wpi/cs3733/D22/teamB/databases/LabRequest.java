package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.Date;

public class LabRequest extends Request {

  public LabRequest(
      String patientID,
      String test,
      Date date,
      String testRoomID,
      String information,
      int priority) {
    super(testRoomID, patientID, information, priority, "Lab Test");
    this.testType = test;
    this.testDate = date;
    this.information =
        "Lab Test: "
            + test
            + "\nTesting Time: "
            + date
            + "\nAdditional Information: "
            + information;
  }

  public LabRequest(
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
        testType,
        testDate,
        type,
        status,
        priority,
        information,
        timeCreated,
        lastEdited);
    this.testType = testType;
    this.testDate = testDate;
  }

  public final String createRequestID() {
    return "LAB" + getHashCode();
  }
}
