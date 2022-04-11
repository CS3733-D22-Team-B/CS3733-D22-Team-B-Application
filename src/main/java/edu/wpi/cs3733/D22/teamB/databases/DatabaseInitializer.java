package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;

public class DatabaseInitializer {

  private Connection connection = null;
  final String DBURL = "jdbc:derby:Databases;create=true";

  public DatabaseInitializer() {}

  public void initDB() {
    try {
      // Create database
      connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();

      if (!tableExists(connection, "LOCATIONS")) {
        statement.execute(
            "CREATE TABLE Locations(nodeID VARCHAR(10), xcoord int, ycoord int, "
                + "floor VARCHAR(10), building VARCHAR(10), nodeType VARCHAR(10), "
                + "longName VARCHAR(100), shortName VARCHAR(50), CONSTRAINT LOCATIONS_PK primary key (nodeID))");
        populateDatabase(Filepath.getInstance().getLocationCSVFilePath(), "Locations", 8);
      }
      if (!tableExists(connection, "MEDICALEQUIPMENT")) {
        statement.execute(
            "CREATE TABLE MedicalEquipment(equipmentID VARCHAR(10), nodeID VARCHAR(10), type VARCHAR(50), "
                + "isClean BOOLEAN, isRequested BOOLEAN, CONSTRAINT MEDICAL_EQUIPMENT_PK primary key (equipmentID), "
                + "CONSTRAINT MEDICAL_EQUIPMENT_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
        populateDatabase(Filepath.getInstance().getMedicalEQCSVFilePath(), "MedicalEquipment", 5);
      }
      if (!tableExists(connection, "EMPLOYEES")) {
        statement.execute(
            "CREATE TABLE Employees(employeeID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), department VARCHAR(100), position VARCHAR(50), username VARCHAR(25), password VARCHAR(25), CONSTRAINT  EMPLOYEES_PK primary key (employeeID))");
        populateDatabase(Filepath.getInstance().getEmployeesCSVFilePath(), "Employees", 7);
      }
      if (!tableExists(connection, "PATIENTS")) {
        statement.execute(
            "CREATE TABLE Patients(patientID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), nodeID VARCHAR(10), CONSTRAINT PATIENTS_PK primary key (patientID), CONSTRAINT PATIENTS_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
        populateDatabase(Filepath.getInstance().getPatientsCSVFilePath(), "Patients", 4);
      }
      if (!tableExists(connection, "EQUIPMENTREQUESTS")) {
        statement.execute(
            "CREATE TABLE EquipmentRequests(requestID VARCHAR(10), type VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), status VARCHAR(15), equipmentID VARCHAR(10), notes VARCHAR(50), CONSTRAINT EQUIPMENTREQUESTS_PK primary key (requestID), CONSTRAINT EQUIPMENTREQUESTS_LOC foreign key (locationID) REFERENCES Locations (nodeID), CONSTRAINT EQUIPMENTREQUESTS_EQUIP foreign key (equipmentID) REFERENCES MedicalEquipment (equipmentID))");
        populateDatabase(
            Filepath.getInstance().getEquipmentRequestCSVFilePath(), "EquipmentRequests", 7);
      }
      if (!tableExists(connection, "LABREQUESTS")) {
        statement.execute(
            "CREATE TABLE LabRequests(requestID VARCHAR(10), employeeID VARCHAR(10), nodeID VARCHAR(10), testRoomID VARCHAR(10),"
                + "type VARCHAR(10), status VARCHAR(15), test VARCHAR(15), date TIMESTAMP, CONSTRAINT LAB_REQUEST_PK primary key (requestID), "
                + "CONSTRAINT LAB_REQUEST_EMP foreign key (employeeID) REFERENCES Employees (employeeID), CONSTRAINT LAB_REQUEST_LOC foreign key (nodeID) REFERENCES Locations(nodeID), CONSTRAINT TEST_ROOM_LOC foreign key (testRoomID) REFERENCES Locations (nodeID))");
        populateDatabaseLabRequestDB(
            Filepath.getInstance().getLabRequestCSVFilePath(), "LabRequests", 7);
      }
      if (!tableExists(connection, "EDGES")) {
        statement.execute(
            "CREATE TABLE Edges(edgeID VARCHAR(21), nodeID1 VARCHAR(10), nodeID2 VARCHAR(10), CONSTRAINT EDGE_PK primary key (edgeID), CONSTRAINT EDGE_NODE1 foreign key (nodeID1) REFERENCES Locations (nodeID), CONSTRAINT EDGE_NODE2 foreign key (nodeID2) REFERENCES Locations (nodeID))");
        populateDatabase(Filepath.getInstance().getEdgesCSVFilePath(), "Patients", 4);
      }
      /*
      if (!tableExists(connection, "SERVICEREQUESTS")) {
        statement.execute(
                "CREATE TABLE ServiceRequests(requestID VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), transferID VARCHAR(10), type VARCHAR(10), status VARCHAR(25), information VARCHAR(250), CONSTRAINT SERVICEREQUESTS_PK primary key (requestID), CONSTRAINT EMPLOYEE_FK foreign key (employeeID) REFERENCES Employees (employeeID), CONSTRAINT LOCATION_FK foreign key (locationID) REFERENCES Locations (nodeID), CONSTRAINT TRANSFER_FK foreign key (transferID) REFERENCES Locations (nodeID))");
        // populateServiceRequestsDatabase();
      }
       */

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  public void populateDatabase(String filepath, String databaseName, int Elements) {
    CSVReader reader = new CSVReader();
    try {
      BufferedReader lineReader = reader.read(filepath);
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
            "INSERT INTO Employees(employeeID, lastName, firstName, department, position, username, password) VALUES(?, ?, ?, ?, ?, ?, ?)";
      } else if (databaseName == "Patients") {
        addToTable =
            "INSERT INTO Patients(patientID, lastName, firstName, nodeID) VALUES(?, ?, ?, ?)";
      } else if (databaseName == "EquipmentRequests") {
        addToTable =
            "INSERT INTO EquipmentRequests(requestID, employeeID, locationID, equipmentID, type, status, priority, information) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
      } else if (databaseName == "Edges") {
        addToTable = "INSERT INTO Edges(edgeID, nodeID1, nodeID2) VALUES(?, ?, ?)";
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

  private boolean tableExists(Connection connection, String tableName) throws SQLException {
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet rs = meta.getTables(null, null, tableName, null);
    return rs.next();
  }

  private void populateDatabaseLabRequestDB(String filepath, String databaseName, int Elements) {
    Connection connection = null;
    CSVReader reader = new CSVReader();
    try {

      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO LabRequests (requestID, employeeID, patientID, testRoomID, type, status, priority, test, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);

      BufferedReader lineReader = reader.read(filepath);
      String lineText = null;

      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        String requestID = data[0];
        String employeeID = data[1];
        String patientID = data[2];
        String testRoomID = data[3];
        String type = data[4];
        String status = data[5];
        String priority = data[6];
        String test = data[7];
        String date = data[8];

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        statement.setString(3, patientID);
        statement.setString(4, testRoomID);
        statement.setString(5, type);
        statement.setString(6, status);
        int priorityInt = Integer.parseInt(priority);
        statement.setInt(7, priorityInt);
        statement.setString(8, test);

        Timestamp sqlTimestamp = Timestamp.valueOf(date);
        statement.setTimestamp(9, sqlTimestamp);

        statement.addBatch();
        statement.executeBatch();
      }

      lineReader.close();
      connection.commit();
      connection.close();

    } catch (IOException ex) {
      System.err.println(ex);
    } catch (SQLException ex) {
      ex.printStackTrace();
      try {
        connection.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void populateServiceRequestsDatabase() {
    Connection connection = null;
    CSVReader reader = new CSVReader();
    try {
      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO ServiceRequests (requestID, employeeID, locationID, patientID, type, status, priority, information) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);

      BufferedReader lineReader =
          reader.read(Filepath.getInstance().getServiceRequestCSVFilePath());
      String lineText = null;

      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        String requestID = data[0];
        String employeeID = data[1];
        String locationID = data[2];
        String patientID = data[3];
        String type = data[4];
        String status = data[5];
        String priority = data[6];
        String information = data.length == 8 ? data[7] : "";

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        if (locationID.compareTo("") != 0) {
          statement.setString(3, locationID);
        } else {
          statement.setString(3, null);
        }
        if (patientID.compareTo("") != 0) {
          statement.setString(4, patientID);
        }
        statement.setString(5, type);
        statement.setString(6, status);
        int priorityInt = Integer.parseInt(priority);
        statement.setInt(7, priorityInt);
        statement.setString(8, information);

        statement.executeUpdate();
      }
      lineReader.close();
      connection.commit();
      connection.close();

    } catch (IOException ex) {
      System.err.println(ex);

    } catch (SQLException ex) {
      ex.printStackTrace();
      try {
        connection.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
