package edu.wpi.cs3733.D22.teamB.databases;

import java.util.HashMap;

public class DatabaseController {

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
    EdgesDB.getInstance().listDB();
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
    EdgesDB edgesDB = EdgesDB.getInstance();

    // Drop All
    equipmentRequestDB.quit();
    medicalEquipmentDB.quit();
    labRequestsDB.quit();
    edgesDB.quit();
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
    edgesDB.setEdgeMap(new HashMap<>());
    // serviceRequestsDB.setRequestMap(new HashMap<>());
    patientsDB.setPatientMap(new HashMap<>());
    employeesDB.setEmployeeMap(new HashMap<>());
    locationsDB.setLocationMap(new HashMap<>());

    // ReInit HasMaps
    medicalEquipmentDB.initDB();
    equipmentRequestDB.initDB();
    labRequestsDB.initDB();
    edgesDB.initDB();
    // serviceRequestsDB.initDB();
    patientsDB.initDB();
    employeesDB.initDB();
    locationsDB.initDB();

    // Drop All
    equipmentRequestDB.quit();
    medicalEquipmentDB.quit();
    labRequestsDB.quit();
    edgesDB.quit();
    // serviceRequestsDB.quit();
    patientsDB.quit();
    employeesDB.quit();
    locationsDB.quit();

    // ReInit
    DI.initDB();
  }
}
