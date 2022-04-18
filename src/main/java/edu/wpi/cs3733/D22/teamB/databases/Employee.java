package edu.wpi.cs3733.D22.teamB.databases;

public class Employee {
  private String employeeID;
  private String lastName;
  private String firstName;
  private String department;
  private String position;
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Employee(
      String lastname,
      String firstName,
      String department,
      String position,
      String username,
      String password) {
    setLastName(lastname);
    setFirstName(firstName);
    setDepartment(department);
    setPosition(position);
    setUsername(username);
    setPassword(password);
    employeeID = createEmployeeID();
  }

  public Employee(
      String employeeID,
      String lastname,
      String firstName,
      String department,
      String position,
      String username,
      String password) {
    setEmployeeID(employeeID);
    setLastName(lastname);
    setFirstName(firstName);
    setDepartment(department);
    setPosition(position);
    setUsername(username);
    setPassword(password);
  }

  public String createEmployeeID() {
    return getDepartment().substring(0, 2).toUpperCase()
        + getPosition().substring(0, 2).toUpperCase()
        + getHashCode();
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

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c =
        firstName.hashCode() + lastName.hashCode() + department.hashCode() + position.hashCode();

    // calculate the hash
    int hash = (int) (37 * result + c);

    // convert hash to string
    String hashCode = Integer.toString(Math.abs(hash));

    // pad with zeros
    while (hashCode.length() < 2) {
      hashCode = "0" + hashCode;
    }

    return hashCode.substring(0, 2);
  }
}
