package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class EquipmentRequestDB extends DatabaseSuperclass implements IDatabases<EquipmentRequest> {

  private final String url = "jdbc:derby:Databases";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupEquipmentRequest.csv";
  private static EquipmentRequestDB equipmentRequestDBManager;

  private HashMap<String, EquipmentRequest> equipmentRequestMap =
      new HashMap<String, EquipmentRequest>();

  private EquipmentRequestDB() {
    super(
        "EquipmentRequests",
        "requestID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationEquipmentRequest.csv");
    initDB();
  }

  public static EquipmentRequestDB getInstance() {
    if (equipmentRequestDBManager == null) {
      equipmentRequestDBManager = new EquipmentRequestDB();
    }
    return equipmentRequestDBManager;
  }

  public void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM EquipmentRequests");

      while (rs.next()) {
        String requestID = rs.getString("requestID");
        String type = rs.getString("type");
        String employeeID = rs.getString("employeeID");
        String locationID = rs.getString("locationID");
        String status = rs.getString("status");
        String equipmentID = rs.getString("equipmentID");
        String notes = rs.getString("notes");

        EquipmentRequest eqreqOb =
            new EquipmentRequest(
                requestID, type, employeeID, locationID, status, equipmentID, notes);
        equipmentRequestMap.put(requestID, eqreqOb);
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<EquipmentRequest> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<EquipmentRequest> eqreqList = new LinkedList<EquipmentRequest>();

    for (int i = 0; i < pkList.size(); i++) {
      eqreqList.add(equipmentRequestMap.get(pkList.get(i)));
    }
    return eqreqList;
  }

  public EquipmentRequest getByID(String id) {
    if (!equipmentRequestMap.containsKey(id)) {
      return null;
    }
    return equipmentRequestMap.get(id);
  }

  public int update(EquipmentRequest eqreqObj) {
    try {
      Connection connection = DriverManager.getConnection(url);

      // If the location does not exist in the database, return -1
      if (equipmentRequestMap.containsKey(eqreqObj.getRequestID()) == false) {
        return -1;
      }

      String sql =
          "UPDATE EquipmentRequests SET type = ?, employeeID = ?, locationID = ?, status = ?, equipmentID= ?, notes = ? WHERE requestID = ?";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, eqreqObj.getType());
      pStatement.setString(2, eqreqObj.getEmployeeID());
      pStatement.setString(3, eqreqObj.getLocationID());
      pStatement.setString(4, eqreqObj.getStatus());
      pStatement.setString(5, eqreqObj.getEquipmentID());
      pStatement.setString(6, eqreqObj.getNotes());
      pStatement.setString(7, eqreqObj.getRequestID());

      pStatement.addBatch();
      pStatement.executeBatch();

      equipmentRequestMap.put(eqreqObj.getRequestID(), eqreqObj);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  public int add(EquipmentRequest eqreqObj) {
    // nodeID has to be unique
    if (equipmentRequestMap.containsKey(eqreqObj.getRequestID())) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);

      String sql = "INSERT INTO EquipmentRequests VALUES(?,?,?,?,?,?,?)";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, eqreqObj.getRequestID());
      pStatement.setString(2, eqreqObj.getType());
      pStatement.setString(3, eqreqObj.getEmployeeID());
      pStatement.setString(4, eqreqObj.getLocationID());
      pStatement.setString(5, eqreqObj.getStatus());
      pStatement.setString(6, eqreqObj.getEquipmentID());
      pStatement.setString(7, eqreqObj.getNotes());

      pStatement.addBatch();
      pStatement.executeBatch();

      equipmentRequestMap.put(eqreqObj.getRequestID(), eqreqObj);

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public int delete(EquipmentRequest eqreqObj) {
    // nodeID has to exist
    if (equipmentRequestMap.containsKey(eqreqObj.getRequestID()) == false) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      String sql =
          "DELETE FROM EquipmentRequests WHERE requestID = '" + eqreqObj.getRequestID() + "'";
      statement.executeUpdate(sql);

      equipmentRequestMap.remove(eqreqObj.getRequestID());

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public void quit() {
    toCSV();
    listDB();

    try {
      // Create database
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE EquipmentRequests");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }
}
