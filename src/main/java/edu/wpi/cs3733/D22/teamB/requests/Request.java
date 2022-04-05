package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeeDB;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;

public abstract class Request {
  protected final String requestID;
  protected final String type;
  protected String employeeID;
  protected Employee employee;
  protected final String locationID;
  protected Location location;
  protected String status;
  protected String information;

  public Request(String locationID, String type) {
    this.type = type;
    this.employeeID = null;
    this.locationID = locationID;
    this.status = "Pending";
    this.requestID = createRequestID();
    setLocation();
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
    setEmployee();
  }

  public final Employee getEmployee() {
    return employee;
  }

  public final void setEmployee() {
    EmployeeDB employeeDB = EmployeeDB.getInstance();
    employee = employeeDB.getByID(employeeID);
  }

  public final String getLocationID() {
    return locationID;
  }

  public final Location getLocation() {
    return location;
  }

  public void setLocation() {
    LocationsDB locationsDB = LocationsDB.getInstance();
    location = locationsDB.getByID(locationID);
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

  public abstract void setInformation();

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c =
            type.hashCode() +
            + ((locationID == null) ? 0 : locationID.hashCode());

    // calculate the hash
    int hash = (int) (37 * result + c);

    // convert hash to string
    String hashCode = Integer.toString(Math.abs(hash));

    // pad with zeros
    while (hashCode.length() < 8) {
      hashCode = "0" + hashCode;
    }

    return hashCode.substring(0, 8);
  }
}
