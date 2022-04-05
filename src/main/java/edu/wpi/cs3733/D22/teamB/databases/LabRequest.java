package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;

public class LabRequest extends Request {
  private final String test;
  private final TestingTime testingTime;

  public LabRequest(String locationID, String test, TestingTime testingTime) {
    super(locationID, "Lab Test");
    this.test = test;
    this.testingTime = testingTime;
    setInformation();
  }

  public final String createRequestID() {
    return "LAB" + getHashCode();
  }

  public final void setInformation() {
    information = "Lab Test: " + test + "\n" + "Testing Time: " + testingTime;
  }

  public String getTest() {
    return test;
  }

  public TestingTime getTestingTime() {
    return testingTime;
  }
}
