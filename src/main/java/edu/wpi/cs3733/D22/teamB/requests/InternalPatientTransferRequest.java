package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;

public class InternalPatientTransferRequest extends Request {
  private String destinationID;
  private Location destination;

  public InternalPatientTransferRequest(String locationID, String destinationID) {
    super(locationID, "Internal Patient Transfer");
    this.destinationID = destinationID;
    information =
            "Internal Patient Transfer Request from "
                    + getLocation().getLongName()
                    + " to "
                    + getDestination().getLongName();
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
