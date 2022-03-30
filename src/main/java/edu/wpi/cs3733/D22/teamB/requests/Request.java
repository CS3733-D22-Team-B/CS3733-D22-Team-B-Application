package edu.wpi.cs3733.D22.teamB.requests;

public abstract class Request {
  protected final String employee;
  protected final String location;

  public Request(String employee, String location) {
    this.employee = employee;
    this.location = location;
  }

  public final String getEmployee() {
    return employee;
  }

  public final String getLocation() {
    return location;
  }

  public abstract String getType();
}
