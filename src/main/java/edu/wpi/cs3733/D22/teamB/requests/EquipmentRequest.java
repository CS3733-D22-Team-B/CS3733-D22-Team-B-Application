package edu.wpi.cs3733.D22.teamB.requests;

public class EquipmentRequest extends Request {
  private String equipment;
  private String notes;

  public EquipmentRequest(String employee, String location, String equipment, String notes) {
    super(employee, location);
    this.equipment = equipment;
    this.notes = notes;
  }

  public final String createRequestID() {
    return "EQU" + getHashCode();
  }

  public final String getType() {
    return "Equipment";
  }

  public String getEquipment() {
    return equipment;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
