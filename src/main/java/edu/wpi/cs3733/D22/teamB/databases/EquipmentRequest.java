package edu.wpi.cs3733.D22.teamB.databases;

public class EquipmentRequest {
  private String requestID;
  private String type;
  private String employeeID;
  private String locationID;
  private String status;
  private String equipmentID;
  private String notes;

  EquipmentRequest(
      String requestID,
      String type,
      String employeeID,
      String locationID,
      String status,
      String equipmentID,
      String notes) {
    setRequestID(requestID);
    setType(type);
    setEmployeeID(employeeID);
    setLocationID(locationID);
    setStatus(status);
    setEquipmentID(equipmentID);
    setNotes(notes);
  }

  public String getRequestID() {
    return requestID;
  }

  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public void setEquipmentID(String equipmentID) {
    this.equipmentID = equipmentID;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
