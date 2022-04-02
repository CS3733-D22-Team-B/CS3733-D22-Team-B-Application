package edu.wpi.cs3733.D22.teamB.requests;

public class Request {
  protected final String employee;
  protected final String location;
  protected final String type;
  protected String status;

  public Request(String employee, String location, String type) {
    this.employee = employee;
    this.location = location;
    this.type = type;
    this.status = "Pending";
  }

  public final String getEmployee() {
    return employee;
  }

  public final String getLocation() {
    return location;
  }

  public final String getType() {
    return type;
  }

  public final void setStatus(String status) {
    this.status = status;
  }
}
