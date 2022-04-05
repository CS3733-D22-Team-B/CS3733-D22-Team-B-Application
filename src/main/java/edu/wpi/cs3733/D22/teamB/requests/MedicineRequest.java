package edu.wpi.cs3733.D22.teamB.requests;

public class MedicineRequest extends Request {
  private String medicine;

  public MedicineRequest(String locationID, String medicine) {
    super(locationID, "Medicine");
    medicine = this.medicine;
    setInformation();
  }

  public final String createRequestID() {
    return "MED" + getHashCode();
  }

  public final void setInformation() {
    information = "Medicine Request: " + medicine;
  }

  public final String getMedicine() {
    return medicine;
  }
}
