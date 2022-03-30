package edu.wpi.cs3733.D22.teamB.requests;

public class MedicineRequest extends Request {

  public MedicineRequest(String employee, String location) {
    super(employee, location);
  }

  public String getType() {
    return "Medicine";
  }
}
