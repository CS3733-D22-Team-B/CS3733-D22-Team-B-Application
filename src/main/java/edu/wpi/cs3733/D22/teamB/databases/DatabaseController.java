package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.LinkedList;

public class DatabaseController {

  private boolean isRemote = false;

  public DatabaseController() {
    DatabaseInitializer DI = new DatabaseInitializer(isRemote);
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
    ServiceRequestsDB.getInstance().listDB();
  }

  public LinkedList<Location> listLocations() {
    return LocationsDB.getInstance().list();
  }

  public LinkedList<Employee> listEmployees() {
    return EmployeesDB.getInstance().list();
  }

  public LinkedList<Patient> listPatients() {
    return PatientsDB.getInstance().list();
  }

  public LinkedList<MedicalEquipment> listMedicalEquipment() {
    return MedicalEquipmentDB.getInstance().list();
  }

  public LinkedList<EquipmentRequest> listEquipmentRequests() {
    return EquipmentRequestDB.getInstance().list();
  }

  public LinkedList<LabRequest> listLabRequests() {
    return LabRequestsDB.getInstance().list();
  }

  public LinkedList<Request> listRequests() {
    return ServiceRequestsDB.getInstance().list();
  }

  public int add(Location loc) {
    return LocationsDB.getInstance().add(loc);
  }

  public int add(Employee emp) {
    return EmployeesDB.getInstance().add(emp);
  }

  public int add(Patient pat) {
    return PatientsDB.getInstance().add(pat);
  }

  public int add(MedicalEquipment medEq) {
    return MedicalEquipmentDB.getInstance().add(medEq);
  }

  public int add(EquipmentRequest eqReq) {
    return EquipmentRequestDB.getInstance().add(eqReq);
  }

  public int add(LabRequest labReq) {
    return LabRequestsDB.getInstance().add(labReq);
  }

  public int add(Request req) {
    return ServiceRequestsDB.getInstance().add(req);
  }

  public int update(Location loc) {
    return LocationsDB.getInstance().update(loc);
  }

  public int update(Employee emp) {
    return EmployeesDB.getInstance().update(emp);
  }

  public int update(Patient pat) {
    return PatientsDB.getInstance().update(pat);
  }

  public int update(MedicalEquipment medEq) {
    return MedicalEquipmentDB.getInstance().update(medEq);
  }

  public int update(EquipmentRequest eqReq) {
    return EquipmentRequestDB.getInstance().update(eqReq);
  }

  public int update(LabRequest labReq) {
    return LabRequestsDB.getInstance().update(labReq);
  }

  public int update(Request req) {
    return ServiceRequestsDB.getInstance().update(req);
  }

  public int delete(Location loc) {
    return LocationsDB.getInstance().delete(loc);
  }

  public int delete(Employee emp) {
    return EmployeesDB.getInstance().delete(emp);
  }

  public int delete(Patient pat) {
    return PatientsDB.getInstance().delete(pat);
  }

  public int delete(MedicalEquipment medEq) {
    return MedicalEquipmentDB.getInstance().delete(medEq);
  }

  public int delete(EquipmentRequest eqReq) {
    return EquipmentRequestDB.getInstance().delete(eqReq);
  }

  public int delete(LabRequest labReq) {
    return LabRequestsDB.getInstance().delete(labReq);
  }

  public int delete(Request req) {
    return ServiceRequestsDB.getInstance().delete(req);
  }

  public void resetAllDBs() {
    // Get Instances
    EmployeesDB employeesDB = EmployeesDB.getInstance();
    EquipmentRequestDB equipmentRequestDB = EquipmentRequestDB.getInstance();
    LabRequestsDB labRequestsDB = LabRequestsDB.getInstance();
    LocationsDB locationsDB = LocationsDB.getInstance();
    MedicalEquipmentDB medicalEquipmentDB = MedicalEquipmentDB.getInstance();
    PatientsDB patientsDB = PatientsDB.getInstance();
    ServiceRequestsDB serviceRequestsDB = ServiceRequestsDB.getInstance();

    // Drop All
    equipmentRequestDB.quit();
    medicalEquipmentDB.quit();
    labRequestsDB.quit();
    serviceRequestsDB.quit();
    patientsDB.quit();
    employeesDB.quit();
    locationsDB.quit();

    // ReInitialize
    DatabaseInitializer DI = new DatabaseInitializer(isRemote);
    // ReInit
    DI.initDB();

    // ReInit HasMaps
    medicalEquipmentDB.initDB();
    equipmentRequestDB.initDB();
    labRequestsDB.initDB();
    serviceRequestsDB.initDB();
    patientsDB.initDB();
    employeesDB.initDB();
    locationsDB.initDB();
  }

  public void switchConnection() {
    this.isRemote = !isRemote;
    DatabaseInitializer DI = new DatabaseInitializer(isRemote);
    EmployeesDB.getInstance().switchConnection(isRemote);
    EquipmentRequestDB.getInstance().switchConnection(isRemote);
    LabRequestsDB.getInstance().switchConnection(isRemote);
    LocationsDB.getInstance().switchConnection(isRemote);
    MedicalEquipmentDB.getInstance().switchConnection(isRemote);
    PatientsDB.getInstance().switchConnection(isRemote);
    ServiceRequestsDB.getInstance().switchConnection(isRemote);
  }
}
