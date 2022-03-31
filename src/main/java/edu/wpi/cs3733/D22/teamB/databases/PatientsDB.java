package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class PatientsDB implements IPatientsDB {
  private final String url = "jdbc:derby:Databases;";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/PatientsBackup.csv";

  private HashMap<String, Patient> patientMap = new HashMap<String, Patient>();;

  public PatientsDB() {
    patientMap = PatientsInit();
  }

  private HashMap<String, Patient> PatientsInit() {
    HashMap<String, Patient> patHM = new HashMap<String, Patient>();
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
        patHM.put(patientID, patOb);
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return patHM;
  }

  public LinkedList<Patient> listPatients() {
    LinkedList<Patient> patientList = new LinkedList<Patient>();

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM Patients");

      while (rs.next()) {
        String patientID = rs.getString("patientID");

        patientList.add(patientMap.get(patientID));
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return patientList;
  }

  public void patientsToCSV() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM Patients");

      // Create file writer and create header row
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(backupFile));
      fileWriter.write("patientID, lastName, firstName, nodeID");

      while (rs.next()) {
        String patientID = rs.getString("patientID");
        String lastName = rs.getString("lastName");
        String firstName = rs.getString("firstName");
        String nodeID = rs.getString("nodeID");

        String line = String.format("%s,%s,%s,%s", patientID, lastName, firstName, nodeID);

        fileWriter.newLine();
        fileWriter.write(line);
      }
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
      return;
    }
  }

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
    this.patientsToCSV();

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
