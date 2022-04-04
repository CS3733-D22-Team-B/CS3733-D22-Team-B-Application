package edu.wpi.cs3733.D22.teamB.databases;

import java.util.Date;

public class LabRequest {
  private String requestID;
  private String employeeID;
  private Employee employee;
  private String nodeID;
  private Location location;
  private String type;
  private String status;
  private String test;
  private Date date;

  LabRequest(
      String requestID,
      String employeeID,
      String nodeID,
      String type,
      String status,
      String test,
      Date date) {
    setRequestID(requestID);
    setEmployeeID(employeeID);
    setNodeID(nodeID);
    setType(type);
    setStatus(status);
    setTest(test);
    setDate(date);
  }

  public String getRequestID() {
    return requestID;
  }

  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public Employee getEmployee() {
    EmployeeDB empDB = EmployeeDB.getInstance();
    employee = empDB.getByID(employeeID);
    return employee;
  }

  public void setEmployee(Employee employee) {
    setEmployeeID(employee.getEmployeeID());
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public Location getLocation() {
    LocationsDB locDB = LocationsDB.getInstance();
    location = locDB.getByID(nodeID);
    return location;
  }

  public void setLocation(Location location) {
    setNodeID(location.getNodeID());
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTest() {
    return test;
  }

  public void setTest(String test) {
    this.test = test;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
