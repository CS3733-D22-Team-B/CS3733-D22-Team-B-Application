package edu.wpi.cs3733.D22.teamB.databases;

public class EquipmentRequest {
  private String requestID;
  private String type;
  private String employeeID;
  private Employee employee;
  private String locationID;
  private Location location;
  private String status;
  private String equipmentID;
  private MedicalEquipment medicalEquipment;
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

  private void setRequestID(String requestID) {
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

  public Employee getEmployee() {
    EmployeeDB employeeDB = EmployeeDB.getInstance();
    employee = employeeDB.getByID(employeeID);
    return employee;
  }

  public void setEmployee(Employee e) {
    setEmployeeID(e.getEmployeeID());
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }

  public Location getLocation() {
    LocationsDB locationsDB = LocationsDB.getInstance();
    location = locationsDB.getByID(locationID);
    return location;
  }

  public void setLocation(Location l) {
    setLocationID(l.getNodeID());
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

  public MedicalEquipment getMedicalEquipment() {
    MedicalEquipmentDB medicalEquipmentDB = MedicalEquipmentDB.getInstance();
    medicalEquipment = medicalEquipmentDB.getByID(equipmentID);
    return medicalEquipment;
  }

  public void setMedicalEquipment(MedicalEquipment me) {
    setEquipmentID(me.getEquipmentID());
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
