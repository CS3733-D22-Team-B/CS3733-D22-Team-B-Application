package edu.wpi.cs3733.D22.teamB.databases;

public class Patient {
  private String patientID;
  private String lastName;
  private String firstName;
  private String nodeID;

  public Patient(String newPatientID, String newLastName, String newFirstName, String newNodeID) {
    setPatientID(newPatientID);
    setLastName(newLastName);
    setFirstName(newFirstName);
    setNodeID(newNodeID);
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
}
