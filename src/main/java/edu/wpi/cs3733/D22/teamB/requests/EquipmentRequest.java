package edu.wpi.cs3733.D22.teamB.requests;

public class EquipmentRequest extends Request {
  private String equipment;
  private String notes;

  public EquipmentRequest(String equipment, String employee, String location, String notes) {
    super(employee, location);
    this.equipment = equipment;
    this.notes = notes;
  }

  public String getEquipment() {
    return equipment;
  }

  public String getNotes() {
    return notes;
  }

  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
