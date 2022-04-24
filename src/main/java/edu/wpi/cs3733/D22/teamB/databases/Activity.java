package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.DateHelper;
import java.util.Date;

public class Activity {

  private String activityID;
  private Date dateAndTime;
  private String employeeID;
  private Employee employee;
  private String typeID;
  private String information;
  private String type;
  private String action;

  public Activity(
      Date date, String employeeID, String typeID, String information, String type, String action) {
    this.activityID = createActivityID();
    this.dateAndTime = date;
    this.employeeID = employeeID;
    this.typeID = typeID;
    this.information = information;
    this.type = type;
    this.action = action;

    employee = getEmployee();
  }

  public Activity(
      String activityID,
      Date date,
      String employeeID,
      String typeID,
      String information,
      String type,
      String action) {
    this.activityID = activityID;
    this.dateAndTime = date;
    this.employeeID = employeeID;
    this.typeID = typeID;
    this.information = information;
    this.type = type;
    this.action = action;

    employee = getEmployee();
  }

  public String createActivityID() {
    int activityCount = ActivityDB.getInstance().list().size();
    return String.format("ACT%03d", activityCount + 1);
  }

  public String getActivityID() {
    return activityID;
  }

  public void setActivityID(String activityID) {
    this.activityID = activityID;
  }

  public Date getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(Date dateAndTime) {
    this.dateAndTime = dateAndTime;
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

  public String getTime() {
    return DateHelper.extractTime(dateAndTime);
  }

  public String getSummary() {
    String summary = "";

    switch (type) {
      case "Employee":
        switch (action) {
          case "added to system":
            summary = type + " " + typeID + " " + action;
            break;
          case "username updated":
            summary = type + " " + typeID + " " + action;
            break;
          case "password updated":
            summary = type + " " + typeID + " " + action;
            break;
          case "removed from system":
            summary = type + " " + typeID + " " + action;
            break;
        }
        break;
      case "Patient":
        switch (action) {
          case "checked in":
            summary = type + " " + typeID + " " + action;
            break;
          case "admitted to room":
            summary = type + " " + typeID + " " + action + " " + information;
            break;
          case "checked out":
            summary = type + " " + typeID + " " + action;
            break;
        }
        break;
      case "Location":
        switch (action) {
          case "added":
            summary = type + " " + typeID + " " + action;
            break;
          case "edited":
            summary = type + " " + typeID + " " + action;
            break;
          case "removed":
            summary = type + " " + typeID + " " + action;
            break;
        }
        break;
      case "Medical Equipment":
        switch (action) {
          case "moved to":
            summary = type + " " + typeID + " " + action + " " + information;
            break;
          case "marked as":
            summary = type + " " + typeID + " " + action + " " + information;
            break;
        }
        break;
      case "Request":
        switch (action) {
          case "created":
            summary = type + " " + typeID + " " + action;
            break;
          case "assigned to":
            summary = type + " " + typeID + " " + action + " " + information;
            break;
          case "marked as":
            summary = type + " " + typeID + " " + action + " " + information;
            break;
          case "removed":
            summary = type + " " + typeID + " " + action;
            break;
        }
        break;
    }

    return summary;
  }
}
