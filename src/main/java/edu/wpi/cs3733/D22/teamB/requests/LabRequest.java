package edu.wpi.cs3733.D22.teamB.requests;

public class LabRequest extends Request {

  public LabRequest(String employee, String location) {
    super(employee, location);
  }

  public String getType() {
    return "Lab";
  }
}
