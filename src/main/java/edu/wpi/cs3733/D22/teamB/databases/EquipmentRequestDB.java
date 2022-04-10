package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class EquipmentRequestDB extends DatabaseSuperclass implements IDatabases<EquipmentRequest> {
  // private final String url = "jdbc:derby:Databases";

  private static EquipmentRequestDB equipmentRequestDBManager;
  private HashMap<String, EquipmentRequest> equipmentRequestMap =
      new HashMap<String, EquipmentRequest>();

  private EquipmentRequestDB() {
    super(
        "EquipmentRequests", "requestID", Filepath.getInstance().getEquipmentRequestCSVFilePath());
    initDB();
  }

  public static EquipmentRequestDB getInstance() {
    if (equipmentRequestDBManager == null) {
      equipmentRequestDBManager = new EquipmentRequestDB();
    }
    return equipmentRequestDBManager;
  }

  public void initDB() {
    equipmentRequestMap.clear(); // Remove residual objects in hashmap
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
                rs.getInt(7),
                rs.getString(8),
                new java.util.Date(rs.getTimestamp(9).getTime()),
                new java.util.Date(rs.getTimestamp(10).getTime()));
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

  public LinkedList<EquipmentRequest> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<EquipmentRequest> list = new LinkedList<EquipmentRequest>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(equipmentRequestMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<EquipmentRequest> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<EquipmentRequest> list = new LinkedList<EquipmentRequest>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(equipmentRequestMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<EquipmentRequest> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<EquipmentRequest> list = new LinkedList<EquipmentRequest>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(equipmentRequestMap.get(pkList.get(i)));
    }
    return list;
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
        "UPDATE EquipmentRequests SET employeeID = ?, locationID = ?, equipmentID = ?, type = ?, status = ?, priority = ?, information = ?, timeCreated = ?, lastEdited = ? WHERE requestID = ?",
        true);
  }

  public int add(EquipmentRequest eqreqObj) {
    if (equipmentRequestMap.containsKey(eqreqObj.getRequestID())) {
      return -1;
    }
    return transform(eqreqObj, "INSERT INTO EquipmentRequests VALUES(?,?,?,?,?,?,?,?,?,?)", false);
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
        pStatement.setString(10, eqreqObj.getRequestID());
        offset = -1;
      } else {
        pStatement.setString(1, eqreqObj.getRequestID());
      }

      pStatement.setString(2 + offset, eqreqObj.getEmployeeID());
      pStatement.setString(3 + offset, eqreqObj.getLocationID());
      pStatement.setString(4 + offset, eqreqObj.getEquipmentID());
      pStatement.setString(5 + offset, eqreqObj.getType());
      pStatement.setString(6 + offset, eqreqObj.getStatus());
      pStatement.setInt(7 + offset, eqreqObj.getPriority());
      pStatement.setString(8 + offset, eqreqObj.getInformation());
      pStatement.setTimestamp(9 + offset, new Timestamp(eqreqObj.getTimeCreated().getTime()));
      pStatement.setTimestamp(10 + offset, new Timestamp(eqreqObj.getLastEdited().getTime()));

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
