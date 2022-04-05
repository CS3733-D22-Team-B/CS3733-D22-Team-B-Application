package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.Date;

public class LabRequest extends Request {
  private final String test;
  private final Date date;

  public LabRequest(String locationID, String test, Date date) {
    super(locationID, "Lab Test");
    this.test = test;
    this.date = date;
    information = "Lab Test: " + test + "\n" + "Testing Time: " + date;
  }

  public LabRequest(
      String requestID,
      String employeeID,
      String nodeID,
      String type,
      String status,
      String test,
      Date date) {
    super(requestID, type, employeeID, nodeID, status, "");
    this.test = test;
    this.date = date;
  }

  public final String createRequestID() {
    return "LAB" + getHashCode();
  }

  public String getNodeID() {
    return locationID;
  }

  public String getTest() {
    return test;
  }

  public Date getDate() {
    return date;
  }
}
