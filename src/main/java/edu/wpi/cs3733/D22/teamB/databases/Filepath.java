package edu.wpi.cs3733.D22.teamB.databases;

public class Filepath {
  private String locationCSVFilePath = "CSVs/Locations.csv";
  private String medicalEQCSVFilePath = "CSVs/MedicalEquipment.csv";
  private String employeesCSVFilePath = "CSVs/Employees.csv";
  private String patientsCSVFilePath = "CSVs/Patients.csv";
  private String equipmentRequestCSVFilePath = "CSVs/EquipmentRequest.csv";
  private String labRequestCSVFilePath = "CSVs/LabRequest.csv";
  private String serviceRequestCSVFilePath = "CSVs/ServiceRequest.csv";
  private String edgesCSVFilePath = "CSVs/Edges.csv";
  private String activityCSVFilePath = "CSVs/Activity.csv";

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

  public String getActivityCSVFilePath() {
    return activityCSVFilePath;
  }
}
