package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.Date;
import java.util.Random;

public abstract class Request {
  protected final String requestID;
  protected String employeeID;
  protected Employee employee;
  protected final String locationID;
  protected Location location;
  protected final String patientID;
  protected Patient patient;
  protected String equipmentID;
  protected MedicalEquipment medicalEquipment;
  protected String testType;
  protected Date testDate;
  protected String type;
  protected String status;
  protected int priority;
  protected String information;
  protected Date timeCreated;
  protected Date lastEdited;

  public Request(
      String locationID, String patientID, String information, int priority, String type) {
    this.employeeID = "0";
    this.locationID = locationID;
    this.patientID = patientID;
    this.equipmentID = null;
    this.testType = null;
    this.testDate = null;
    this.type = type;
    this.status = "Pending";
    this.priority = priority;
    this.information = information;
    this.requestID = createRequestID();
    this.timeCreated = new Date();
    this.lastEdited = new Date();

    employee = getEmployee();
    location = getLocation();
    patient = getPatient();
  }

  public Request(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String equipmentID,
      String testType,
      Date testDate,
      String type,
      String status,
      int priority,
      String information,
      Date timeCreated,
      Date lastEdited) {
    this.requestID = requestID;
    this.employeeID = employeeID;
    this.locationID = locationID;
    this.patientID = patientID;
    this.equipmentID = equipmentID;
    this.testType = testType;
    this.testDate = testDate;
    this.type = type;
    this.status = status;
    this.priority = priority;
    this.information = information;
    this.timeCreated = timeCreated;
    this.lastEdited = lastEdited;

    employee = getEmployee();
    location = getLocation();
    patient = getPatient();
  }

  public abstract String createRequestID();

  public final String getRequestID() {
    return requestID;
  }

  public final String getEmployeeID() {
    return employeeID;
  }

  public final void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public Employee getEmployee() {
    EmployeesDB employeesDB = EmployeesDB.getInstance();
    employee = employeesDB.getByID(employeeID);
    return employee;
  }

  public void setEmployee(Employee e) {
    setEmployeeID(e.getEmployeeID());
  }

  public final String getLocationID() {
    return locationID;
  }

  public Location getLocation() {
    LocationsDB locationsDB = LocationsDB.getInstance();
    location = locationsDB.getByID(locationID);
    return location;
  }

  public final String getPatientID() {
    return patientID;
  }

  public Patient getPatient() {
    PatientsDB patientsDB = PatientsDB.getInstance();
    patient = patientsDB.getByID(patientID);
    return patient;
  }

  public final String getEquipmentID() {
    return this.equipmentID;
  }

  public final String getTestType() {
    return this.testType;
  }

  public final Date getTestDate() {
    return this.testDate;
  }

  public final String getType() {
    return type;
  }

  public final String getStatus() {
    return status;
  }

  public final void setStatus(String status) {
    this.status = status;
  }

  public final String getInformation() {
    return information;
  }

  public final void setInformation(String information) {
    this.information = information;
  }

  public final int getPriority() {
    return priority;
  }

  public final Date getTimeCreated() {
    return timeCreated;
  }

  public final Date getLastEdited() {
    return lastEdited;
  }

  public final void setLastEdited(Date d) {
    lastEdited = d;
  }

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    Random generator = new Random();
    double result = generator.nextDouble();
    // generate random value between 0 and 1 inclusive
    // double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    double c =
        type.hashCode() * Math.random(); // + +((locationID == null) ? 0 : locationID.hashCode());

    // calculate the hash
    int hash = (int) (37 * result + c);

    // convert hash to string
    String hashCode = Integer.toString(Math.abs(hash));

    // pad with zeros
    while (hashCode.length() < 6) {
      hashCode = "0" + hashCode;
    }

    return hashCode.substring(0, 6);
  }

  public boolean equals(Object o) {
    try {
      ((Request) o).getRequestID();
    } catch (Exception e) {
      return false;
    }

    if (this.requestID.equals(((Request) o).getRequestID())) {
      return true;
    }
    return false;
  }
}
