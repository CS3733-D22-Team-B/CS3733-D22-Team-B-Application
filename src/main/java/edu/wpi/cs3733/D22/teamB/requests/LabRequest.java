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

  public String getType() {
    return "Lab";
  }
}
