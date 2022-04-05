package edu.wpi.cs3733.D22.teamB.databases;

public class Employee {
  private String employeeID;
  private String lastName;
  private String firstName;
  private String department;
  private String position;

  public Employee(
      String employeeID, String lastname, String firstName, String department, String position) {
    setEmployeeID(employeeID);
    setLastName(lastname);
    setFirstName(firstName);
    setDepartment(department);
    setPosition(position);
  }

  public String getEmployeeID() {
    return employeeID;
  }

  private void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getOverview() {
    return firstName + " " + lastName + " (" + employeeID + ")";
  }
}
