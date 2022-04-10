package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;

public class DatabaseInitializer {

  private Connection connection = null;
  private String embedded = "jdbc:derby:Databases;create=true";
  private String remote = "jdbc:derby://localhost:1527/Databases;create=true";
  private String DBURL;

  public DatabaseInitializer(boolean isRemote) {
    if (isRemote) {
      DBURL = remote;
    } else {
      DBURL = embedded;
    }
  }

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
            "CREATE TABLE EquipmentRequests(requestID VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), equipmentID VARCHAR(10), type VARCHAR(100), status VARCHAR(25), priority int, information VARCHAR(512), timeCreated TIMESTAMP, lastEdited TIMESTAMP, CONSTRAINT EQUIPMENTREQUESTS_PK primary key (requestID), CONSTRAINT ER_EMPLOYEE_FK foreign key (employeeID) REFERENCES Employees (employeeID),CONSTRAINT EQUIPMENTREQUESTS_LOC foreign key (locationID) REFERENCES Locations (nodeID), CONSTRAINT EQUIPMENTREQUESTS_EQUIP foreign key (equipmentID) REFERENCES MedicalEquipment (equipmentID))");
        populateDatabaseEquipmentRequestDB();
      }
      if (!tableExists(connection, "LABREQUESTS")) {
        statement.execute(
            "CREATE TABLE LabRequests(requestID VARCHAR(10), employeeID VARCHAR(10), patientID VARCHAR(10), testRoomID VARCHAR(10),"
                + "type VARCHAR(50), status VARCHAR(50), priority int, test VARCHAR(50), date TIMESTAMP, timeCreated TIMESTAMP, lastEdited TIMESTAMP, CONSTRAINT LAB_REQUEST_PK primary key (requestID), "
                + "CONSTRAINT LAB_REQUEST_EMP foreign key (employeeID) REFERENCES Employees (employeeID), CONSTRAINT LAB_REQUEST_PAT foreign key (patientID) REFERENCES Patients (patientID), CONSTRAINT TEST_ROOM_LOC foreign key (testRoomID) REFERENCES Locations (nodeID))");
        populateDatabaseLabRequestDB(
            Filepath.getInstance().getLabRequestCSVFilePath(), "LabRequests", 7);
      }
      if (!tableExists(connection, "SERVICEREQUESTS")) {
        statement.execute(
            "CREATE TABLE ServiceRequests(requestID VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), patientID VARCHAR(10), type VARCHAR(100), status VARCHAR(50), priority int, information VARCHAR(512), timeCreated TIMESTAMP, lastEdited TIMESTAMP, CONSTRAINT SERVICEREQUESTS_PK primary key (requestID), CONSTRAINT EMPLOYEE_FK foreign key (employeeID) REFERENCES Employees (employeeID), CONSTRAINT LOCATION_FK foreign key (locationID) REFERENCES Locations (nodeID), CONSTRAINT PATIENT_FK foreign key (patientID) REFERENCES Patients (patientID))");
        populateServiceRequestsDatabase();
      }

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

  private void populateDatabaseEquipmentRequestDB() {
    Connection connection = null;
    CSVReader reader = new CSVReader();
    try {

      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO EquipmentRequests (requestID, employeeID, locationID, equipmentID, type, status, priority, information, timeCreated, lastEdited) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);

      BufferedReader lineReader =
          reader.read(Filepath.getInstance().getEquipmentRequestCSVFilePath());
      String lineText = null;

      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        String requestID = data[0];
        String employeeID = data[1];
        String locationID = data[2];
        String equipmentID = data[3];
        String type = data[4];
        String status = data[5];
        String priority = data[6];
        String information = data[7];
        String timeCreated = data[8];
        String lastEdited = data[9];

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        statement.setString(3, locationID);
        statement.setString(4, equipmentID);
        statement.setString(5, type);
        statement.setString(6, status);
        int priorityInt = Integer.parseInt(priority);
        statement.setInt(7, priorityInt);
        statement.setString(8, information);

        Timestamp sqlTimestamp1 = Timestamp.valueOf(timeCreated);
        statement.setTimestamp(9, sqlTimestamp1);

        Timestamp sqlTimestamp2 = Timestamp.valueOf(lastEdited);
        statement.setTimestamp(10, sqlTimestamp2);

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

  private void populateDatabaseLabRequestDB(String filepath, String databaseName, int Elements) {
    Connection connection = null;
    CSVReader reader = new CSVReader();
    try {

      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO LabRequests (requestID, employeeID, patientID, testRoomID, type, status, priority, test, date, timeCreated, lastEdited) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        String timeCreated = data[9];
        String lastEdited = data[10];

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        statement.setString(3, patientID);
        statement.setString(4, testRoomID);
        statement.setString(5, type);
        statement.setString(6, status);
        int priorityInt = Integer.parseInt(priority);
        statement.setInt(7, priorityInt);
        statement.setString(8, test);

        Timestamp sqlTimestamp1 = Timestamp.valueOf(date);
        statement.setTimestamp(9, sqlTimestamp1);

        Timestamp sqlTimestamp2 = Timestamp.valueOf(timeCreated);
        statement.setTimestamp(10, sqlTimestamp2);

        Timestamp sqlTimestamp3 = Timestamp.valueOf(lastEdited);
        statement.setTimestamp(11, sqlTimestamp3);

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
          "INSERT INTO ServiceRequests (requestID, employeeID, locationID, patientID, type, status, priority, information, timeCreated, lastEdited) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        String information = data[7];
        String timeCreated = data[8];
        String lastEdited = data[9];

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        if (locationID.compareTo("") != 0) {
          statement.setString(3, locationID);
        } else {
          statement.setString(3, null);
        }
        if (patientID.compareTo("") != 0) {
          statement.setString(4, patientID);
        } else {
          statement.setString(4, null);
        }
        statement.setString(5, type);
        statement.setString(6, status);
        int priorityInt = Integer.parseInt(priority);
        statement.setInt(7, priorityInt);
        statement.setString(8, information);

        Timestamp sqlTimestamp1 = Timestamp.valueOf(timeCreated);
        statement.setTimestamp(9, sqlTimestamp1);

        Timestamp sqlTimestamp2 = Timestamp.valueOf(lastEdited);
        statement.setTimestamp(10, sqlTimestamp2);

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
