package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class LabRequest extends Request {
  private String test;
  private Date testingTime;

  public LabRequest(String employee, String location, String test, Date testingTime) {
    super(employee, location);
    this.test = test;
    this.testingTime = testingTime;
  }

  public final String createRequestID() {
    return "LAB" + getHashCode();
  }

  public final String getType() {
    return "Lab Test";
  }

  public final String getTest() {
    return test;
  }

  public final Date getTestingTime() {
    return testingTime;
  }
}
