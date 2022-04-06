package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;

public class DatabaseInitializer {

  private Connection connection = null;
  final String DBURL = "jdbc:derby:Databases;create=true";
  private String locationCSVFilePath = "CSVs/ApplicationLocations.csv";
  private String medicalEQCSVFilePath = "CSVs/ApplicationMedicalEquipment.csv";
  private String employeesCSVFilePath = "CSVs/ApplicationEmployees.csv";
  private String patientsCSVFilePath = "CSVs/ApplicationPatients.csv";
  private String equipmentRequestCSVFilePath = "CSVs/ApplicationEquipmentRequest.csv";
  private String labRequestCSVFilePath = "CSVs/ApplicationLabRequest.csv";
  private String serviceRequestCSVFilePath = "CSVs/ApplicationServiceRequest.csv";


  public void setLocationCSVFilePath(String newPath) {
    this.locationCSVFilePath = newPath;
  }

  public void setMedicalEQCSVFilePath(String newPath) {
    this.medicalEQCSVFilePath = newPath;
  }

  public void setEmployeesCSVFilePath(String newPath) {
    this.employeesCSVFilePath = newPath;
  }

  public void setPatientsCSVFilePath(String newPath) {
    this.patientsCSVFilePath = newPath;
  }

  public void setEquipmentRequestCSVFilePath(String newPath) {
    this.equipmentRequestCSVFilePath = newPath;
  }

  public void setLabRequestCSVFilePath(String newPath) {
    this.labRequestCSVFilePath = newPath;
  }

  public void setServiceRequestCSVFilePath(String newPath) {
    this.serviceRequestCSVFilePath = newPath;
  }

  public DatabaseInitializer() {}

  public void initDB() {
    try {
      // Create database
      connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      // statement.execute("DROP TABLE MedicalEquipment");
      // statement.execute("DROP TABLE Patients");
      // statement.execute("DROP TABLE Locations");
      statement.execute(
          "CREATE TABLE Locations(nodeID VARCHAR(10), xcoord int, ycoord int, "
              + "floor VARCHAR(10), building VARCHAR(10), nodeType VARCHAR(10), "
              + "longName VARCHAR(100), shortName VARCHAR(50), CONSTRAINT LOCATIONS_PK primary key (nodeID))");
      statement.execute(
          "CREATE TABLE MedicalEquipment(equipmentID VARCHAR(10), nodeID VARCHAR(10), type VARCHAR(50), "
              + "isClean BOOLEAN, isRequested BOOLEAN, CONSTRAINT MEDICAL_EQUIPMENT_PK primary key (equipmentID), "
              + "CONSTRAINT MEDICAL_EQUIPMENT_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
      statement.execute(
          "CREATE TABLE Employees(employeeID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), department VARCHAR(100), position VARCHAR(50), username VARCHAR(25), password VARCHAR(25), CONSTRAINT  EMPLOYEES_PK primary key (employeeID))");
      statement.execute(
          "CREATE TABLE Patients(patientID VARCHAR(10), lastName VARCHAR(25), firstName VARCHAR(25), nodeID VARCHAR(10), CONSTRAINT PATIENTS_PK primary key (patientID), CONSTRAINT PATIENTS_FK foreign key (nodeID) REFERENCES Locations (nodeID))");
      statement.execute(
          "CREATE TABLE EquipmentRequests(requestID VARCHAR(10), type VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), status VARCHAR(15), equipmentID VARCHAR(10), notes VARCHAR(50), CONSTRAINT EQUIPMENTREQUESTS_PK primary key (requestID), CONSTRAINT EQUIPMENTREQUESTS_LOC foreign key (locationID) REFERENCES Locations (nodeID), CONSTRAINT EQUIPMENTREQUESTS_EQUIP foreign key (equipmentID) REFERENCES MedicalEquipment (equipmentID))");
      statement.execute(
          "CREATE TABLE LabRequests(requestID VARCHAR(10), employeeID VARCHAR(10), nodeID VARCHAR(10), testRoomID VARCHAR(10),"
              + "type VARCHAR(10), status VARCHAR(15), test VARCHAR(15), date TIMESTAMP, CONSTRAINT LAB_REQUEST_PK primary key (requestID), "
              + "CONSTRAINT LAB_REQUEST_EMP foreign key (employeeID) REFERENCES Employees (employeeID), CONSTRAINT LAB_REQUEST_LOC foreign key (nodeID) REFERENCES Locations(nodeID), CONSTRAINT TEST_ROOM_LOC foreign key (testRoomID) REFERENCES Locations (nodeID))");
      /*
      statement.execute(
          "CREATE TABLE ServiceRequests(requestID VARCHAR(10), employeeID VARCHAR(10), locationID VARCHAR(10), transferID VARCHAR(10), type VARCHAR(10), status VARCHAR(25), information VARCHAR(250), CONSTRAINT SERVICEREQUESTS_PK primary key (requestID), CONSTRAINT EMPLOYEE_FK foreign key (employeeID) REFERENCES Employees (employeeID), CONSTRAINT LOCATION_FK foreign key (locationID) REFERENCES Locations (nodeID), CONSTRAINT TRANSFER_FK foreign key (transferID) REFERENCES Locations (nodeID))");
       */

      populateDatabase(locationCSVFilePath, "Locations", 8);
      populateDatabase(medicalEQCSVFilePath, "MedicalEquipment", 5);
      populateDatabase(employeesCSVFilePath, "Employees", 7);
      populateDatabase(patientsCSVFilePath, "Patients", 4);
      populateDatabase(equipmentRequestCSVFilePath, "EquipmentRequests", 7);
      populateDatabaseLabRequestDB(labRequestCSVFilePath, "LabRequests", 7);
      // populateServiceRequestsDatabase();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  public void populateDatabase(String filepath, String databaseName, int Elements) {
    try {
      InputStream is = getClass().getResourceAsStream(filepath);
      BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
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
            "INSERT INTO EquipmentRequests(requestID, type, employeeID, locationID, status, equipmentID, notes) VALUES(?, ?, ?, ?, ?, ?, ?)";
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

  private void populateDatabaseLabRequestDB(String filepath, String databaseName, int Elements) {
    Connection connection = null;

    try {

      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO LabRequests (requestID, employeeID, nodeID, testRoomID, type, status, test, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);

      InputStream is = getClass().getResourceAsStream(labRequestCSVFilePath);
      BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
      String lineText = null;

      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        String requestID = data[0];
        String employeeID = data[1];
        String nodeID = data[2];
        String testRoomID = data[3];
        String type = data[4];
        String status = data[5];
        String test = data[6];
        String date = data[7];

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        statement.setString(3, nodeID);
        statement.setString(4, testRoomID);
        statement.setString(5, type);
        statement.setString(6, status);
        statement.setString(7, test);

        Timestamp sqlTimestamp = Timestamp.valueOf(date);
        statement.setTimestamp(8, sqlTimestamp);

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

    try {
      connection = DriverManager.getConnection(DBURL);

      String sql =
          "INSERT INTO ServiceRequests (requestID, employeeID, locationID, transferID, type, status, information) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);

      InputStream is = getClass().getResourceAsStream(serviceRequestCSVFilePath);
      BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
      String lineText = null;

      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        String requestID = data[0];
        String employeeID = data[1];
        String locationID = data[2];
        String transferID = data[3];
        String type = data[4];
        String status = data[5];
        String information = data[6];

        statement.setString(1, requestID);
        statement.setString(2, employeeID);
        statement.setString(3, locationID);
        if (transferID.compareTo("") != 0) {
          statement.setString(4, transferID);
        }
        statement.setString(5, type);
        statement.setString(6, status);
        if (information.compareTo("") != 0) {
          statement.setString(7, information);
        }

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
}
