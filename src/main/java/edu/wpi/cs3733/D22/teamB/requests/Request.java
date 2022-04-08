package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.*;

public abstract class Request {
  protected final String requestID;
  protected String type;
  protected String employeeID;
  protected Employee employee;
  protected final String locationID;
  protected Location location;
  protected final String patientID;
  protected Patient patient;
  protected String status;
  protected int priority;
  protected String information;

  public Request(String locationID, String patientID, String type) {
    this.type = type;
    this.employeeID = "0";
    this.locationID = locationID;
    this.patientID = patientID;
    this.status = "Pending";
    this.priority = 0;
    this.requestID = createRequestID();
  }

  public Request(
      String requestID,
      String type,
      String employeeID,
      String locationID,
      String patientID,
      String status,
      int priority,
      String information) {
    this.requestID = requestID;
    this.type = type;
    this.employeeID = employeeID;
    this.locationID = locationID;
    this.patientID = patientID;
    this.status = status;
    this.priority = priority;
    this.information = information;
  }

  public abstract String createRequestID();

  public final String getRequestID() {
    return requestID;
  }

  public final String getType() {
    return type;
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

  public final String getStatus() {
    return status;
  }

  public final void setStatus(String status) {
    this.status = status;
  }

  public final String getInformation() {
    return information;
  }

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c = type.hashCode() + +((locationID == null) ? 0 : locationID.hashCode());

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
}
