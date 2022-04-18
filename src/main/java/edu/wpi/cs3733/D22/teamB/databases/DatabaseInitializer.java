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
                + "longName VARCHAR(100), shortName VARCHAR(50), availability BOOLEAN, CONSTRAINT LOCATIONS_PK primary key (nodeID))");
        populateDatabase(Filepath.getInstance().getLocationCSVFilePath(), "Locations", 9);
      }
      if (!tableExists(connection, "MEDICALEQUIPMENT")) {
        statement.execute(
            "CREATE TABLE MedicalEquipment(equipmentID VARCHAR(10), nodeID VARCHAR(10), type VARCHAR(50), "
                + "isClean BOOLEAN, availability VARCHAR(50), name VARCHAR(50), CONSTRAINT MEDICAL_EQUIPMENT_PK primary key (equipmentID), "
                + "CONSTRAINT MEDICAL_EQUIPMENT_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
        populateDatabase(Filepath.getInstance().getMedicalEQCSVFilePath(), "MedicalEquipment", 6);
      }
      if (!tableExists(connection, "EMPLOYEES")) {
        statement.execute(
            "CREATE TABLE Employees(employeeID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), department VARCHAR(100), position VARCHAR(50), username VARCHAR(25), password VARCHAR(25), CONSTRAINT  EMPLOYEES_PK primary key (employeeID))");
        populateDatabase(Filepath.getInstance().getEmployeesCSVFilePath(), "Employees", 7);
      }
      if (!tableExists(connection, "PATIENTS")) {
        statement.execute(
            "CREATE TABLE Patients(patientID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), nodeID VARCHAR(10), information VARCHAR(100), CONSTRAINT PATIENTS_PK primary key (patientID), CONSTRAINT PATIENTS_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
        populateDatabase(Filepath.getInstance().getPatientsCSVFilePath(), "Patients", 5);
      }
      if (!tableExists(connection, "EDGES")) {
        statement.execute(
            "CREATE TABLE Edges(edgeID VARCHAR(21), nodeID1 VARCHAR(10), nodeID2 VARCHAR(10), CONSTRAINT EDGE_PK primary key (edgeID), CONSTRAINT EDGE_NODE1 foreign key (nodeID1) REFERENCES Locations (nodeID), CONSTRAINT EDGE_NODE2 foreign key (nodeID2) REFERENCES Locations (nodeID))");
        populateDatabase(Filepath.getInstance().getEdgesCSVFilePath(), "Edges", 3);
      }
      if (!tableExists(connection, "SERVICEREQUESTS")) {
        statement.execute(
            "CREATE TABLE ServiceRequests(requestID VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), patientID VARCHAR(10), equipmentID VARCHAR(10), testType VARCHAR(50), testDate TIMESTAMP, type VARCHAR(100), status VARCHAR(50), priority int, information VARCHAR(512), timeCreated TIMESTAMP, lastEdited TIMESTAMP, CONSTRAINT SERVICEREQUESTS_PK primary key (requestID), CONSTRAINT EMPLOYEE_FK foreign key (employeeID) REFERENCES Employees (employeeID) ON DELETE CASCADE, CONSTRAINT LOCATION_FK foreign key (locationID) REFERENCES Locations (nodeID) ON DELETE CASCADE, CONSTRAINT PATIENT_FK foreign key (patientID) REFERENCES Patients (patientID) ON DELETE CASCADE, CONSTRAINT EQUIPMENT_FK foreign key (equipmentID) REFERENCES MedicalEquipment (equipmentID) ON DELETE CASCADE)");
        populateServiceRequestsDatabase();
      }
      if (!tableExists(connection, "ACTIVITY")) {
        statement.execute(
            "CREATE TABLE Activity(time TIMESTAMP, employeeID VARCHAR(10), typeID VARCHAR(10), information VARCHAR(100), type VARCHAR(25), action VARCHAR(100), CONSTRAINT ACTIVITY_PK primary key (time), CONSTRAINT ACTIVITY_FK foreign key (employeeID) REFERENCES Employees (employeeID) ON DELETE CASCADE)");
        populateActivityDatabase();
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
            "INSERT INTO Locations(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, availability) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
      } else if (databaseName == "MedicalEquipment") {
        addToTable =
            "INSERT INTO MedicalEquipment(equipmentID, nodeID, type, isClean, availability, name) VALUES(?, ?, ?, ?, ?, ?)";
      } else if (databaseName == "Employees") {
        addToTable =
            "INSERT INTO Employees(employeeID, lastName, firstName, department, position, username, password) VALUES(?, ?, ?, ?, ?, ?, ?)";
      } else if (databaseName == "Patients") {
        addToTable =
            "INSERT INTO Patients(patientID, lastName, firstName, nodeID, information) VALUES(?, ?, ?, ?, ?)";
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

  private void populateServiceRequestsDatabase() {
    Connection connection = null;
    CSVReader reader = new CSVReader();
    try {
      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO ServiceRequests (requestID, employeeID, locationID, patientID, equipmentID, testType, testDate, type, status, priority, information, timeCreated, lastEdited) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        String equipmentID = data[4];
        String testType = data[5];
        String testDate = data[6];
        String type = data[7];
        String status = data[8];
        String priority = data[9];
        String information = data[10];
        String timeCreated = data[11];
        String lastEdited = data[12];

        statement.setString(1, requestID);

        if (employeeID.compareTo("") != 0) {
          statement.setString(2, employeeID);
        } else {
          statement.setString(2, null);
        }

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

        if (equipmentID.compareTo("") != 0) {
          statement.setString(5, equipmentID);
        } else {
          statement.setString(5, null);
        }

        if (testType.compareTo("") != 0) {
          statement.setString(6, testType);
        } else {
          statement.setString(6, null);
        }

        if (testDate.compareTo("") != 0) {
          Timestamp sqlTimestamp1 = Timestamp.valueOf(testDate);
          statement.setTimestamp(7, sqlTimestamp1);
        } else {
          statement.setTimestamp(7, null);
        }

        statement.setString(8, type);
        statement.setString(9, status);
        int priorityInt = Integer.parseInt(priority);
        statement.setInt(10, priorityInt);
        statement.setString(11, information);

        Timestamp sqlTimestamp2 = Timestamp.valueOf(timeCreated);
        statement.setTimestamp(12, sqlTimestamp2);

        Timestamp sqlTimestamp3 = Timestamp.valueOf(lastEdited);
        statement.setTimestamp(13, sqlTimestamp3);

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

  public void populateActivityDatabase() {
    Connection connection = null;
    CSVReader reader = new CSVReader();
    try {
      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO Activity (time, employeeID, typeID, information, type, action) VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);

      BufferedReader lineReader = reader.read(Filepath.getInstance().getActivityCSVFilePath());
      String lineText = null;

      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        String time = data[0];
        String employeeID = data[1];
        String typeID = data[2];
        String information = data[3];
        String type = data[4];
        String action = data[5];

        Timestamp sqlTimestamp = Timestamp.valueOf(time);
        statement.setTimestamp(1, sqlTimestamp);

        statement.setString(2, employeeID);
        statement.setString(3, typeID);

        if (information.compareTo("") != 0) {
          statement.setString(4, information);
        } else {
          statement.setString(4, null);
        }

        statement.setString(5, type);
        statement.setString(6, action);

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
