package edu.wpi.cs3733.D22.teamB.databases;

import java.util.Date;

public class Activity {

  private Date time;
  private String employeeID;
  private Employee employee;
  private String typeID;
  private String information;
  private String type;
  private String action;

  public Activity(
      Date date, String employeeID, String typeID, String information, String type, String action) {
    this.time = date;
    this.employeeID = employeeID;
    this.typeID = typeID;
    this.information = information;
    this.type = type;
    this.action = action;

    employee = getEmployee();
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
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

  public String getTypeID() {
    return typeID;
  }

  public void setTypeID(String typeID) {
    this.typeID = typeID;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
