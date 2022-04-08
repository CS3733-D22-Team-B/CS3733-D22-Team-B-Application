package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;

public class InternalPatientTransferRequest extends Request {
  private String destinationID;
  private Location destination;

  public InternalPatientTransferRequest(String patientID, String destinationID) {
    super(null, patientID, "Internal Patient Transfer");
    this.destinationID = destinationID;
    information =
        "Internal Patient Transfer Request from "
            + getPatient().getOverview()
            + " to "
            + getDestination().getLongName();
  }

  public InternalPatientTransferRequest(
      String requestID,
      String employeeID,
      String locationID,
      String patientID,
      String transferID,
      String type,
      String status,
      int priority,
      String information) {
    super(requestID, type, employeeID, locationID, patientID, status, priority, information);
    this.destinationID = transferID;
  }

  public final String createRequestID() {
    return "IPT" + getHashCode();
  }

  private void setDestinationID(String destinationID) {
    this.destinationID = destinationID;
  }

  public final String getDestinationID() {
    return destinationID;
  }

  public final Location getDestination() {
    LocationsDB locDB = LocationsDB.getInstance();
    destination = locDB.getByID(destinationID);
    return destination;
  }

  public final void setDestination(Location l) {
    setDestinationID(l.getNodeID());
  }
}
