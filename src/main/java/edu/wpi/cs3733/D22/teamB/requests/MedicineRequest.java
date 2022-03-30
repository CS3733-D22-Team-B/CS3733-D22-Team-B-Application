package edu.wpi.cs3733.D22.teamB.requests;

public class MedicineRequest extends Request {
  private String medicine;

  public MedicineRequest(String employee, String location, String medicine) {
    super(employee, location);
    medicine = this.medicine;
  }

  public String getType() {
    return "Medicine";
  }
}
