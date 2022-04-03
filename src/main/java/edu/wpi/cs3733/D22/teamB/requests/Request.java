package edu.wpi.cs3733.D22.teamB.requests;

public abstract class Request {
  protected final String requestID;
  protected final String employee;
  protected final String location;
  protected String status;

  public Request(String employee, String location) {
    this.employee = employee;
    this.location = location;
    this.status = "Pending";
    this.requestID = createRequestID();
  }

  public abstract String createRequestID();

  public final String getRequestID() {
    return requestID;
  }

  public final String getEmployee() {
    return employee;
  }

  public final String getLocation() {
    return location;
  }

  public abstract String getType();

  public final String getStatus() {
    return status;
  }

  public final void setStatus(String status) {
    this.status = status;
  }

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c =
        ((employee == null) ? 0 : employee.hashCode())
            + ((location == null) ? 0 : location.hashCode());

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
