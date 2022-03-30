package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseInitializer {

  private Connection connection = null;
  final String DBURL = "jdbc:derby:Databases;create=true";
  private final String locationCSVFilePath =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/TowerLocations.csv";
  private final String medicalEQCSVFilePath =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/MedEquipReq.csv";
  private final String employeesCSVFilePath =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/Employees.csv";
  private final String patientsCSVFilePath =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/Patients.csv";

  public DatabaseInitializer() {
    initDB();
  }

  public void initDB() {
    try {
      // Create database
      connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE Patients");
      // statement.execute("DROP TABLE MedicalEquipment");
      statement.execute("DROP TABLE Employees");
      statement.execute("DROP TABLE Locations");
      statement.execute(
          "CREATE TABLE Locations(nodeID VARCHAR(10), xcoord int, ycoord int, "
              + "floor VARCHAR(10), building VARCHAR(10), nodeType VARCHAR(10), "
              + "longName VARCHAR(100), shortName VARCHAR(50), CONSTRAINT LOCATIONS_PK primary key (nodeID))");
      statement.execute(
          "CREATE TABLE MedicalEquipment(equipmentID VARCHAR(10), nodeID VARCHAR(10), type VARCHAR(50), "
              + "isClean BOOLEAN, isRequested BOOLEAN, CONSTRAINT MEDICAL_EQUIPMENT_PK primary key (equipmentID), "
              + "CONSTRAINT MEDICAL_EQUIPMENT_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
      // statement.execute(
      // "CREATE TABLE Employees(employeeID VARCHAR(10), lastName VARCHAR(25), firstName
      // VARCHAR(25), department VARCHAR(100), position VARCHAR(50), CONSTRAINT  EMPLOYEES_PK
      // primary key (employeeID))");
      statement.execute(
          "CREATE TABLE Patients(patientID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), nodeID VARCHAR(10), CONSTRAINT PATIENTS_PK primary key (patientID), CONSTRAINT PATIENTS_FK foreign key (nodeID) REFERENCES Locations (nodeID))");

      populateDatabase(locationCSVFilePath, "Locations", 8);
      populateDatabase(medicalEQCSVFilePath, "MedicalEquipment", 5);
      // populateDatabase(employeesCSVFilePath, "Employees", 5);
      populateDatabase(patientsCSVFilePath, "Patients", 4);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  public void populateDatabase(String filepath, String databaseName, int Elements) {
    try {
      BufferedReader lineReader = new BufferedReader(new FileReader(filepath));
      String lineText = null;
      lineReader.readLine(); // Skip line with attribute names

      String addToTable = "";
      if (databaseName == "Locations") {
        addToTable =
            "INSERT INTO Locations(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
      } else if (databaseName == "MedicalEquipment") {
        addToTable =
            "INSERT INTO MedicalEquipment(equipmentID, nodeID, type, isClean, isRequested) VALUES(?, ?, ?, ?, ?)";
      } else if (databaseName == "Employees") {
        addToTable =
            "INSERT INTO Employees(employeeID, lastName, firstName, department, position) VALUES(?, ?, ?, ?, ?)";
      } else if (databaseName == "Patients") {
        addToTable =
            "INSERT INTO Patients(patientID, lastName, firstName, nodeID) VALUES(?, ?, ?, ?)";
      }

      PreparedStatement pStatement = connection.prepareStatement(addToTable);

      while ((lineText = lineReader.readLine()) != null) // While line is not empty, read data
      {
        String[] data = lineText.split(",");

        for (int i = 0; i < Elements; i++) {
          if (((Object) data[i]).getClass().getSimpleName().compareTo("Integer") == 0) {
            int tempI = Integer.parseInt(data[i]);
            pStatement.setInt(i + 1, tempI);
          } else if (((Object) data[i]).getClass().getSimpleName().compareTo("Boolean") == 0) {
            boolean tempB = stringToBoolean(data[i]);
            pStatement.setBoolean(i + 1, tempB);
          } else if (((Object) data[i]).getClass().getSimpleName().compareTo("String") == 0) {
            String tempS = data[i];
            pStatement.setString(i + 1, tempS);
          }
        }
        pStatement.addBatch();
        pStatement.executeBatch();
      }
      lineReader.close();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  private boolean stringToBoolean(String input) {
    if (input.compareTo("TRUE") == 0) {
      return true;
    } else {
      return false;
    }
  }

  public void listDB(String databaseName, int Elements) {
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.getResultSet();

      if (databaseName == "Locations") {
        rs = statement.executeQuery("SELECT * FROM Locations");
      } else if (databaseName == "MedicalEquipment") {
        rs = statement.executeQuery("SELECT * FROM MedicalEquipment");
      } else if (databaseName == "Employees") {
        rs = statement.executeQuery("SELECT * FROM Employees");
      } else if (databaseName == "Patients") {
        rs = statement.executeQuery("SELECT * FROM Patients");
      }

      while (rs.next()) {
        System.out.print(databaseName + " { ");
        for (int i = 1; i < Elements + 1; i++) {
          System.out.print(rs.getMetaData().getColumnName(i) + ": ");
          System.out.print(rs.getString(i) + ", ");
        }
        System.out.println(" }");
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }
}

/*
public void listDB(String databaseName, int Elements) {
  try {
    Connection connection = DriverManager.getConnection(DBURL);
    Statement statement = connection.createStatement();
    ResultSet rs = statement.getResultSet();

    if (databaseName == "Locations") {
      rs = statement.executeQuery("SELECT * FROM Locations");
    } else if (databaseName == "MedicalEquipment") {
      rs = statement.executeQuery("SELECT * FROM MedicalEquipment");
    }

    while (rs.next()) {
      for (int i = 1; i < Elements + 1; i++) {
        System.out.print(databaseName + " { ");
        System.out.print(rs.getMetaData().getColumnName(i) + ": ");
        System.out.print(rs.getString(i) + ", ");
      }
      System.out.println(" }");
    }
  } catch (SQLException e) {
    System.out.println("Connection failed. Check output console.");
    e.printStackTrace();
    return;
  }
}
*/
