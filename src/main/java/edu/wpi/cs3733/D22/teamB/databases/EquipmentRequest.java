package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;

import java.util.Date;

public class EquipmentRequest extends Request {
  private final String equipmentID;
  private MedicalEquipment medicalEquipment;

  public EquipmentRequest(String locationID, String equipmentID, String information) {
    super(locationID, null, "Equipment Request");
    this.equipmentID = equipmentID;
    setMedicalEquipment();
    information = "Equipment Request: " + getEquipmentID() + "\n" + "Notes: " + information;
    medicalEquipment = getMedicalEquipment();
  }

  public EquipmentRequest(
          String requestID,
          String employeeID,
          String locationID,
          String equipmentID,
          String type,
          String status,
          int priority,
          String information,
          Date timeCreated,
          Date lastEdited) {
    super(requestID, employeeID, locationID, null, type, status, priority, information, timeCreated, lastEdited);
    this.equipmentID = equipmentID;
    medicalEquipment = getMedicalEquipment();
  }

  public final String createRequestID() {
    return "EQU" + getHashCode();
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public void setMedicalEquipment() {
    MedicalEquipmentDB medicalEquipmentDB = MedicalEquipmentDB.getInstance();
    medicalEquipment = medicalEquipmentDB.getByID(equipmentID);
  }

  public MedicalEquipment getMedicalEquipment() {
    MedicalEquipmentDB medEqDB = MedicalEquipmentDB.getInstance();
    medicalEquipment = medEqDB.getByID(equipmentID);
    return medicalEquipment;
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
