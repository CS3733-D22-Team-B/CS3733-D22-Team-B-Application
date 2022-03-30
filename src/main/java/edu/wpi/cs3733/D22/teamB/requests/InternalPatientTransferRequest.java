package edu.wpi.cs3733.D22.teamB.requests;

public class InternalPatientTransferRequest extends Request {
  private String destination;

  public InternalPatientTransferRequest(String employee, String location, String destination) {
    super(employee, location);

    this.destination = destination;
  }

  public String getType() {
    return "Internal Patient Transfer";
  }
}
