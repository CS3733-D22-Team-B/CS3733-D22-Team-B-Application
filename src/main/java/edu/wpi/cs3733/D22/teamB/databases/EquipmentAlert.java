package edu.wpi.cs3733.D22.teamB.databases;

public class EquipmentAlert {
  private String alertID;
  private String floor;
  private String type;

  DatabaseController DC = DatabaseController.getInstance();

  public EquipmentAlert(String floor, String type) {
    this.floor = floor;
    this.type = type;
    this.alertID = "ALT" + getHashCode();
  }

  public String getAlertID() {
    return alertID;
  }

  private void setAlertID(String alertID) {
    this.alertID = alertID;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  // Josh Bloch's Hashing method
  protected final String getHashCode() {
    // generate random value between 0 and 1 inclusive
    double result = (Math.random() + 1) / 2.0;

    // calculate the field component weights
    long c = floor.hashCode() + type.hashCode();

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
}
