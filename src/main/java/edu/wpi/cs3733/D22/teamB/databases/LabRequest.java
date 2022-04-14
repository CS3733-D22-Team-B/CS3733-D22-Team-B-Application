package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.Date;

public class LabRequest extends Request {
  private final String testRoomID;
  private Location testRoom;
  private final String test;
  private final Date date;

  public LabRequest(
      String patientID,
      String test,
      Date date,
      String testRoomID,
      String information,
      int priority) {
    super(null, patientID, information, priority, "Lab Test");
    this.test = test;
    this.date = date;
    this.testRoomID = testRoomID;
    this.information =
        "Lab Test: "
            + test
            + "\nTesting Time: "
            + date
            + "\nAdditional Information: "
            + information;

    testRoom = getTestRoom();
  }

  public LabRequest(
      String requestID,
      String employeeID,
      String patientID,
      String testRoomID,
      String type,
      String status,
      int priority,
      String information,
      String test,
      Date date,
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
    this.test = test;
    this.date = date;
    this.testRoomID = testRoomID;

    testRoom = getTestRoom();
  }

  public final String createRequestID() {
    return "LAB" + getHashCode();
  }

  public String getTest() {
    return test;
  }

  public Date getDate() {
    return date;
  }

  public String getTestRoomID() {
    return testRoomID;
  }

  public Location getTestRoom() {
    LocationsDB locationsDB = LocationsDB.getInstance();
    testRoom = locationsDB.getByID(testRoomID);
    return testRoom;
  }
}
