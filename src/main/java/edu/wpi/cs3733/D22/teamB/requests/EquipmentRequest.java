package edu.wpi.cs3733.D22.teamB.requests;

import java.util.LinkedList;

public class EquipmentRequest extends Request {
  public static LinkedList<EquipmentRequest> equipmentRequests = new LinkedList<EquipmentRequest>();

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

  public String getType() {
    return "Equipment";
  }
}
