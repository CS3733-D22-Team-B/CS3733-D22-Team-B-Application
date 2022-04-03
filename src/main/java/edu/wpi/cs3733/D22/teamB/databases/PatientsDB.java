package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class PatientsDB extends DatabaseSuperclass implements IDatabases<Patient> {
  private final String url = "jdbc:derby:Databases;";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupPatients.csv";
  private static PatientsDB patientsDBManager;

  private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();

  private PatientsDB() {
    super(
        "Patients",
        "patientID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationPatients.csv");
    initPatDB();
  }

  public static PatientsDB getInstance() {
    if (patientsDBManager == null) {
      patientsDBManager = new PatientsDB();
    }
    return patientsDBManager;
  }

  public HashMap<String, Patient> getMap() {
    return patientMap;
  }

  public void initPatDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM Patients");

      while (rs.next()) {
        String patientID = rs.getString("patientID");
        String lastName = rs.getString("lastName");
        String firstName = rs.getString("firstName");
        String nodeID = rs.getString("nodeID");

        Patient patOb = new Patient(patientID, lastName, firstName, nodeID);
        patientMap.put(patientID, patOb);
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

  public LinkedList<Patient> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Patient> locList = new LinkedList<Patient>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(patientMap.get(pkList.get(i)));
    }
    return locList;
  }

  ////////////////////////////////////////////////////////////// To Fix
  public int update(Patient patObj) {
    try {
      Connection connection = DriverManager.getConnection(url);

      // If the patient does not exist in the database, return -1
      if (patientMap.containsKey(patObj.getPatientID()) == false) {
        return -1;
      }

      String sql =
          "UPDATE Patients SET lastName = ?, firstName = ?, nodeID = ? WHERE patientID = ?";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, patObj.getLastName());
      pStatement.setString(2, patObj.getFirstName());
      pStatement.setString(3, patObj.getNodeID());
      pStatement.setString(4, patObj.getPatientID());

      pStatement.addBatch();
      pStatement.executeBatch();

      patientMap.put(patObj.getPatientID(), patObj);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  public int add(Patient patObj) {
    // patientID has to be unique
    if (patientMap.containsKey(patObj.getPatientID())) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);

      String sql = "INSERT INTO Patients VALUES(?,?,?,?)";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, patObj.getPatientID());
      pStatement.setString(2, patObj.getLastName());
      pStatement.setString(3, patObj.getFirstName());
      pStatement.setString(4, patObj.getNodeID());

      pStatement.addBatch();
      pStatement.executeBatch();

      patientMap.put(patObj.getPatientID(), patObj);

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public int delete(Patient patObj) {
    // patientID has to exist
    if (patientMap.containsKey(patObj.getPatientID()) == false) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      String sql = "DELETE FROM Patients WHERE patientID = '" + patObj.getPatientID() + "'";
      statement.executeUpdate(sql);

      patientMap.remove(patObj.getPatientID());

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
