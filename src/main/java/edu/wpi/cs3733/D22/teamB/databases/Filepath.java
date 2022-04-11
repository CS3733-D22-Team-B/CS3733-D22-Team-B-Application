package edu.wpi.cs3733.D22.teamB.databases;

public class Filepath {
  private String locationCSVFilePath = "CSVs/ApplicationLocations.csv";
  private String medicalEQCSVFilePath = "CSVs/ApplicationMedicalEquipment.csv";
  private String employeesCSVFilePath = "CSVs/ApplicationEmployees.csv";
  private String patientsCSVFilePath = "CSVs/ApplicationPatients.csv";
  private String equipmentRequestCSVFilePath = "CSVs/ApplicationEquipmentRequest.csv";
  private String labRequestCSVFilePath = "CSVs/ApplicationLabRequest.csv";
  private String serviceRequestCSVFilePath = "CSVs/ApplicationServiceRequest.csv";
  private String edgesCSVFilePath = "CSVs/Edges.csv";

  private static Filepath filepathManager;

  private Filepath() {}

  public static Filepath getInstance() {
    if (filepathManager == null) {
      filepathManager = new Filepath();
    }
    return filepathManager;
  }

  public String getLocationCSVFilePath() {
    return locationCSVFilePath;
  }

  public String getMedicalEQCSVFilePath() {
    return medicalEQCSVFilePath;
  }

  public String getEmployeesCSVFilePath() {
    return employeesCSVFilePath;
  }

  public String getPatientsCSVFilePath() {
    return patientsCSVFilePath;
  }

  public String getEquipmentRequestCSVFilePath() {
    return equipmentRequestCSVFilePath;
  }

  public String getLabRequestCSVFilePath() {
    return labRequestCSVFilePath;
  }

  public String getServiceRequestCSVFilePath() {
    return serviceRequestCSVFilePath;
  }

  public String getEdgesCSVFilePath() {
    return edgesCSVFilePath;
  }
}
