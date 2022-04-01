package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class PatientsDAO extends DatabaseSuperclass implements PatientDAOImpl {
  private final String url = "jdbc:derby:Databases;";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/PatientsBackup.csv";

  private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();;

  public PatientsDAO() {
    super(
        "Patients",
        "patientID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/PatientsBackup.csv");
    initDB();
  }

  public void initDB() {
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

  public LinkedList<Patient> listPatients() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Patient> patList = new LinkedList<Patient>();

    for (int i = 0; i < pkList.size(); i++) {
      patList.add(patientMap.get(pkList.get(i)));
    }
    return patList;
  }

  ////////////////////////////////////////////////////////////// To Fix
  public int updatePatient(Patient patObj) {
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

  public int addPatient(Patient patObj) {
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

  public int deletePatient(Patient patObj) {
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

  public void quit() {
    toCSV();
    listDB("Patients", 4);

    try {
      // Create database
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE Patients");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }
}
