package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class EquipmentRequestDB extends DatabaseSuperclass implements IDatabases<EquipmentRequest> {

  private final String url = "jdbc:derby:Databases";
  private final String backupFile = "CSVs/BackupEquipmentRequest.csv";

  private static EquipmentRequestDB equipmentRequestDBManager;

  private HashMap<String, EquipmentRequest> equipmentRequestMap =
      new HashMap<String, EquipmentRequest>();

  public HashMap<String, EquipmentRequest> getEquipmentRequestMap() {
    return equipmentRequestMap;
  }

  public void setEquipmentRequestMap(HashMap<String, EquipmentRequest> equipmentRequestMap) {
    this.equipmentRequestMap = equipmentRequestMap;
  }

  private EquipmentRequestDB() {
    super("EquipmentRequests", "requestID", "CSVs/ApplicationEquipmentRequest.csv");
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
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        EquipmentRequest eqreqOb =
            new EquipmentRequest(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7));
        equipmentRequestMap.put(rs.getString(1), eqreqOb);
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

  public LinkedList<EquipmentRequest> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<EquipmentRequest> locList = new LinkedList<EquipmentRequest>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(equipmentRequestMap.get(pkList.get(i)));
    }
    return locList;
  }

  public EquipmentRequest getByID(String id) {
    if (!equipmentRequestMap.containsKey(id)) {
      return null;
    }
    return equipmentRequestMap.get(id);
  }

  public int update(EquipmentRequest eqreqObj) {
    if (!equipmentRequestMap.containsKey(eqreqObj.getRequestID())) {
      return -1;
    }
    return transform(
        eqreqObj,
        "UPDATE EquipmentRequests SET type = ?, employeeID = ?, locationID = ?, status = ?, equipmentID = ?, notes = ? WHERE requestID = ?",
        true);
  }

  public int add(EquipmentRequest eqreqObj) {
    if (equipmentRequestMap.containsKey(eqreqObj.getRequestID())) {
      return -1;
    }
    return transform(eqreqObj, "INSERT INTO EquipmentRequests VALUES(?,?,?,?,?,?,?)", false);
  }

  public int delete(EquipmentRequest eqreqObj) {
    if (!equipmentRequestMap.containsKey(eqreqObj.getRequestID())) {
      return -1;
    }
    equipmentRequestMap.remove(eqreqObj.getRequestID());
    return deleteFrom(eqreqObj.getRequestID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(EquipmentRequest eqreqObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(7, eqreqObj.getRequestID());
        offset = -1;
      } else {
        pStatement.setString(1, eqreqObj.getRequestID());
      }

      pStatement.setString(2 + offset, eqreqObj.getType());
      pStatement.setString(3 + offset, eqreqObj.getEmployeeID());
      pStatement.setString(4 + offset, eqreqObj.getLocationID());
      pStatement.setString(5 + offset, eqreqObj.getStatus());
      pStatement.setString(6 + offset, eqreqObj.getEquipmentID());
      pStatement.setString(7 + offset, eqreqObj.getNotes());

      pStatement.addBatch();
      pStatement.executeBatch();
      equipmentRequestMap.put(eqreqObj.getRequestID(), eqreqObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
