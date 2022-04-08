package edu.wpi.cs3733.D22.teamB.databases;

import java.util.HashMap;

public class DatabaseController {
  // private final String locationCSVFilePath = "CSVs/ApplicationLocations.csv";
  // private final String medicalEQCSVFilePath = "CSVs/ApplicationMedicalEquipment.csv";
  // private final String employeesCSVFilePath = "CSVs/ApplicationEmployees.csv";
  // private final String patientsCSVFilePath = "CSVs/ApplicationPatients.csv";
  // private final String equipmentRequestCSVFilePath = "CSVs/ApplicationEquipmentRequest.csv";
  // private final String labRequestCSVFilePath = "CSVs/ApplicationLabRequest.csv";
  // private final String serviceRequestCSVFilePath =
  // "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationServiceRequest.csv";

  // private final String locationBCSVFilePath = "CSVs/BackupLocations.csv";
  // private final String medicalEQBCSVFilePath = "CSVs/BackupMedicalEquipment.csv";
  // private final String employeesBCSVFilePath = "CSVs/BackupEmployees.csv";
  // private final String patientsBCSVFilePath = "CSVs/BackupPatients.csv";
  // private final String equipmentRequestBCSVFilePath = "CSVs/BackupEquipmentRequest.csv";
  // private final String labRequestBCSVFilePath = "CSVs/BackupLabRequest.csv";
  // private final String serviceRequestBCSVFilePath =
  // "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupServiceRequest.csv";

  public DatabaseController() {
    DatabaseInitializer DI = new DatabaseInitializer();
    DI.initDB();
  }

  private static DatabaseController DatabaseControllerManager;

  public static DatabaseController getInstance() {
    if (DatabaseControllerManager == null) {
      DatabaseControllerManager = new DatabaseController();
    }
    return DatabaseControllerManager;
  }

  public void printAllDBs() {
    EmployeesDB.getInstance().listDB();
    EquipmentRequestDB.getInstance().listDB();
    LabRequestsDB.getInstance().listDB();
    LocationsDB.getInstance().listDB();
    MedicalEquipmentDB.getInstance().listDB();
    PatientsDB.getInstance().listDB();
    // ServiceRequestsDB.getInstance().listDB();
  }

  public void resetAllDBs() {
    // Get Instances
    EmployeesDB employeesDB = EmployeesDB.getInstance();
    EquipmentRequestDB equipmentRequestDB = EquipmentRequestDB.getInstance();
    LabRequestsDB labRequestsDB = LabRequestsDB.getInstance();
    LocationsDB locationsDB = LocationsDB.getInstance();
    MedicalEquipmentDB medicalEquipmentDB = MedicalEquipmentDB.getInstance();
    PatientsDB patientsDB = PatientsDB.getInstance();
    // ServiceRequestsDB serviceRequestsDB = ServiceRequestsDB.getInstance();

    // Drop All
    equipmentRequestDB.quit();
    medicalEquipmentDB.quit();
    labRequestsDB.quit();
    // serviceRequestsDB.quit();
    patientsDB.quit();
    employeesDB.quit();
    locationsDB.quit();

    // ReInitialize
    DatabaseInitializer DI = new DatabaseInitializer();

    // ReInit
    DI.initDB();

    // Wipe AllHashMaps
    medicalEquipmentDB.setMedicalEquipmentMap(new HashMap<>());
    equipmentRequestDB.setEquipmentRequestMap(new HashMap<>());
    labRequestsDB.setLabRequestMap(new HashMap<>());
    // serviceRequestsDB.setRequestMap(new HashMap<>());
    patientsDB.setPatientMap(new HashMap<>());
    employeesDB.setEmployeeMap(new HashMap<>());
    locationsDB.setLocationMap(new HashMap<>());

    // ReInit HasMaps
    medicalEquipmentDB.initDB();
    equipmentRequestDB.initDB();
    labRequestsDB.initDB();
    // serviceRequestsDB.initDB();
    patientsDB.initDB();
    employeesDB.initDB();
    locationsDB.initDB();

    // ChangeBackCSVs
    // DI.setEmployeesCSVFilePath(Filepath.getInstance().getEmployeesCSVFilePath());
    // DI.setEquipmentRequestCSVFilePath(Filepath.getInstance().getEquipmentRequestCSVFilePath());
    // DI.setLabRequestCSVFilePath(Filepath.getInstance().getLabRequestCSVFilePath());
    // DI.setLocationCSVFilePath(Filepath.getInstance().getLocationCSVFilePath());
    // DI.setMedicalEQCSVFilePath(Filepath.getInstance().getMedicalEQCSVFilePath());
    // DI.setPatientsCSVFilePath(Filepath.getInstance().getPatientsCSVFilePath());
    // DI.setServiceRequestCSVFilePath(Filepath.getInstance().getServiceRequestCSVFilePath());

    // Drop All
    equipmentRequestDB.quit();
    medicalEquipmentDB.quit();
    labRequestsDB.quit();
    // serviceRequestsDB.quit();
    patientsDB.quit();
    employeesDB.quit();
    locationsDB.quit();

    // ReInit
    DI.initDB();
  }
}
