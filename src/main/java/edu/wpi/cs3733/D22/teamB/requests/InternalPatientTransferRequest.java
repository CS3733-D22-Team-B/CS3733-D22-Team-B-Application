package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;

public class InternalPatientTransferRequest extends Request {
  private String destinationID;
  private Location destination;

  public InternalPatientTransferRequest(String locationID, String destinationID) {
    super(locationID, "Internal Patient Transfer");
    this.destinationID = destinationID;
    setDestination();
  }

  public final String createRequestID() {
    return "IPT" + getHashCode();
  }

  public final void setInformation() {
    information =
        "Internal Patient Transfer Request from "
            + location.getLongName()
            + " to "
            + destination.getLongName();
  }

  public final String getDestinationID() {
    return destinationID;
  }

  public final Location getDestination() {
    return destination;
  }

  public final void setDestination() {
    LocationsDB locationsDB = LocationsDB.getInstance();
    destination = locationsDB.getByID(destinationID);
  }
}
