package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;

public class EquipmentRequest extends Request {
  private final String equipmentID;
  private MedicalEquipment medicalEquipment;
  private String notes;

  public EquipmentRequest(String locationID, String equipmentID, String notes) {
    super(locationID, "Equipment Request");
    this.equipmentID = equipmentID;
    this.notes = notes;
    setMedicalEquipment();
    setInformation();
  }

  public final String createRequestID() {
    return "EQU" + getHashCode();
  }

  public final void setInformation() {
    information = "Equipment Request: " + getEquipmentID() + "\n" + "Notes: " + getNotes();
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public MedicalEquipment getMedicalEquipment() {
    return medicalEquipment;
  }

  public void setMedicalEquipment() {
    MedicalEquipmentDB medicalEquipmentDB = MedicalEquipmentDB.getInstance();
    medicalEquipment = medicalEquipmentDB.getByID(equipmentID);
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  /////////////////// EMPLOYEE GETTERS////////////////////
  public String getLastName() {
    return employee.getLastName();
  }

  public String getFirstName() {
    return employee.getFirstName();
  }

  public String getDepartment() {
    return employee.getDepartment();
  }

  public String getPosition() {
    return employee.getPosition();
  }

  /////////////////// LOCATION GETTERS////////////////////
  public int getXCoord() {
    return location.getXCoord();
  }

  public int getYCoord() {
    return location.getYCoord();
  }

  public String getFloor() {
    return location.getFloor();
  }

  public String getBuilding() {
    return location.getBuilding();
  }

  public String getNodeType() {
    return location.getNodeType();
  }

  public String getLongName() {
    return location.getLongName();
  }

  public String getShortName() {
    return location.getShortName();
  }

  /////////////////// MEDICAL EQUIPMENT GETTERS////////////////////
  public String getNodeID() {
    return medicalEquipment.getNodeID();
  }
}
