package edu.wpi.cs3733.D22.teamB.databases;

public class Patient {
  private String patientID;
  private String lastName;
  private String firstName;
  private String nodeID;
  private Location location;
  private String information;

  public Patient(String lastName, String firstName, String nodeID) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.nodeID = nodeID;
    this.patientID = "PA" + getHashCode();
  }

  public Patient(
      String newPatientID,
      String newLastName,
      String newFirstName,
      String newNodeID,
      String newInformation) {
    setPatientID(newPatientID);
    setLastName(newLastName);
    setFirstName(newFirstName);
    setNodeID(newNodeID);
    setInformation(newInformation);
  }

  public void setPatientID(String newPatientID) {
    patientID = newPatientID;
  }

  public String getPatientID() {
    return patientID;
  }

  public void setLastName(String newLastName) {
    lastName = newLastName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String newFirstName) {
    firstName = newFirstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setNodeID(String newNodeID) {
    nodeID = newNodeID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setLocation(Location l) {
    setNodeID(l.getNodeID());
  }

  public Location getLocation() {
    LocationsDB locDB = LocationsDB.getInstance();
    location = locDB.getByID(nodeID);
    return location;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public String getInformation() {
    return information;
  }

  public String getOverview() {
    return firstName + " " + lastName + " (" + patientID + ")";
  }

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c =
        firstName.hashCode() + lastName.hashCode() + ((nodeID == null) ? 0 : nodeID.hashCode());

    // calculate the hash
    int hash = (int) (37 * result + c);

    // convert hash to string
    String hashCode = Integer.toString(Math.abs(hash));

    // pad with zeros
    while (hashCode.length() < 6) {
      hashCode = "0" + hashCode;
    }

    return hashCode.substring(0, 6);
  }

  /////////////////// LOCATION GETTERS////////////////////
  public int getXCoord() {
    return getLocation().getXCoord();
  }

  public int getYCoord() {
    return getLocation().getYCoord();
  }

  public String getFloor() {
    return getLocation().getFloor();
  }

  public String getBuilding() {
    return getLocation().getBuilding();
  }

  public String getNodeType() {
    return getLocation().getNodeType();
  }

  public String getLongName() {
    return getLocation().getLongName();
  }

  public String getShortName() {
    return getLocation().getShortName();
  }
}
