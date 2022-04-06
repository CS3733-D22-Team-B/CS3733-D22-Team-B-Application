package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class PatientsDB extends DatabaseSuperclass implements IDatabases<Patient> {
  private final String url = "jdbc:derby:src/Databases;";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupPatients.csv";
  private static PatientsDB patientsDBManager;

  private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();

  public HashMap<String, Patient> getPatientMap() {
    return patientMap;
  }

  public void setPatientMap(HashMap<String, Patient> patientMap) {
    this.patientMap = patientMap;
  }

  private PatientsDB() {
    super(
        "Patients",
        "patientID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationPatients.csv");
    initDB();
  }

  public static PatientsDB getInstance() {
    if (patientsDBManager == null) {
      patientsDBManager = new PatientsDB();
    }
    return patientsDBManager;
  }

  protected void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Patient patOb =
            new Patient(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
        patientMap.put(rs.getString(1), patOb);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Patient> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Patient> patList = new LinkedList<Patient>();

    for (int i = 0; i < pkList.size(); i++) {
      patList.add(patientMap.get(pkList.get(i)));
    }
    return patList;
  }

  public Patient getByID(String id) {
    if (!patientMap.containsKey(id)) {
      return null;
    }
    return patientMap.get(id);
  }

  public LinkedList<Patient> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Patient> locList = new LinkedList<Patient>();
    for (int i = 0; i < pkList.size(); i++) {
      locList.add(patientMap.get(pkList.get(i)));
    }
    return locList;
  }

  public int update(Patient patObj) {
    if (!patientMap.containsKey(patObj.getPatientID())) {
      return -1;
    }
    return transform(
        patObj,
        "UPDATE Patients SET lastName = ?, firstName = ?, nodeID = ? WHERE patientID = ?",
        true);
  }

  public int add(Patient patObj) {
    if (patientMap.containsKey(patObj.getPatientID())) {
      return -1;
    }
    return transform(patObj, "INSERT INTO Patients VALUES(?,?,?,?)", false);
  }

  public int delete(Patient patObj) {
    if (!patientMap.containsKey(patObj.getPatientID())) {
      return -1;
    }
    patientMap.remove(patObj.getPatientID());
    return deleteFrom(patObj.getPatientID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(Patient patObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(4, patObj.getPatientID());
        offset = -1;
      } else {
        pStatement.setString(1, patObj.getPatientID());
      }

      pStatement.setString(2 + offset, patObj.getLastName());
      pStatement.setString(3 + offset, patObj.getFirstName());
      pStatement.setString(4 + offset, patObj.getNodeID());

      pStatement.addBatch();
      pStatement.executeBatch();
      patientMap.put(patObj.getPatientID(), patObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
