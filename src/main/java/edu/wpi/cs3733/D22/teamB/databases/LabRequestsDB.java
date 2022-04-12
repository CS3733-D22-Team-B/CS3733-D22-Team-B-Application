package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;

public class LabRequestsDB extends DatabaseSuperclass implements IDatabases<LabRequest> {
  // private final String url = "jdbc:derby:Databases";

  private static LabRequestsDB labRequestsDBManager;
  private HashMap<String, LabRequest> labRequestMap = new HashMap<String, LabRequest>();

  private LabRequestsDB() {
    super("LabRequests", "requestID", Filepath.getInstance().getLabRequestCSVFilePath());
    initDB();
  }

  public static LabRequestsDB getInstance() {
    if (labRequestsDBManager == null) {
      labRequestsDBManager = new LabRequestsDB();
    }
    return labRequestsDBManager;
  }

  @Override
  protected void initDB() {
    labRequestMap.clear(); // Remove residual objects in hashmap
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        LabRequest labReqObj =
            new LabRequest(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7),
                rs.getString(8),
                rs.getString(9),
                new java.util.Date(rs.getTimestamp(10).getTime()),
                new java.util.Date(rs.getTimestamp(11).getTime()),
                new java.util.Date(rs.getTimestamp(12).getTime()));
        labRequestMap.put(rs.getString(1), labReqObj);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  @Override
  public LinkedList<LabRequest> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<LabRequest> labReqList = new LinkedList<LabRequest>();

    for (int i = 0; i < pkList.size(); i++) {
      labReqList.add(labRequestMap.get(pkList.get(i)));
    }
    return labReqList;
  }

  @Override
  public LabRequest getByID(String id) {
    if (!labRequestMap.containsKey(id)) {
      return null;
    }
    return labRequestMap.get(id);
  }

  @Override
  public LinkedList<LabRequest> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<LabRequest> labReqList = new LinkedList<LabRequest>();

    for (int i = 0; i < pkList.size(); i++) {
      labReqList.add(labRequestMap.get(pkList.get(i)));
    }
    return labReqList;
  }

  public LinkedList<LabRequest> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<LabRequest> list = new LinkedList<LabRequest>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(labRequestMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<LabRequest> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<LabRequest> list = new LinkedList<LabRequest>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(labRequestMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<LabRequest> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<LabRequest> list = new LinkedList<LabRequest>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(labRequestMap.get(pkList.get(i)));
    }
    return list;
  }

  @Override
  public int update(LabRequest labReqObj) {
    if (!labRequestMap.containsKey(labReqObj.getRequestID())) {
      return -1;
    }
    return transform(
        labReqObj,
        "UPDATE LabRequests SET employeeID = ?, patientID = ?, testRoomID = ?, type = ?, status = ?, priority = ?, information = ?, test = ?, date = ?, timeCreated = ?, lastEdited = ? WHERE requestID = ?",
        true);
  }

  @Override
  public int add(LabRequest labReq) {
    if (labRequestMap.containsKey(labReq.getRequestID())) {
      return -1;
    }
    return transform(labReq, "INSERT INTO LabRequests VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", false);
  }

  @Override
  public int delete(LabRequest labReq) {
    if (!labRequestMap.containsKey(labReq.getRequestID())) {
      return -1;
    }
    labRequestMap.remove(labReq.getRequestID());
    return deleteFrom(labReq.getRequestID());
  }

  private int transform(LabRequest labReq, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(12, labReq.getRequestID());
        offset = -1;
      } else {
        pStatement.setString(1, labReq.getRequestID());
      }

      pStatement.setString(2 + offset, labReq.getEmployeeID());
      pStatement.setString(3 + offset, labReq.getPatientID());
      pStatement.setString(4 + offset, labReq.getTestRoomID());
      pStatement.setString(5 + offset, labReq.getType());
      pStatement.setString(6 + offset, labReq.getStatus());
      pStatement.setInt(7 + offset, labReq.getPriority());
      pStatement.setString(8 + offset, labReq.getInformation());
      pStatement.setString(9 + offset, labReq.getTest());
      pStatement.setTimestamp(10 + offset, new Timestamp(labReq.getDate().getTime()));
      pStatement.setTimestamp(11 + offset, new Timestamp(labReq.getTimeCreated().getTime()));
      pStatement.setTimestamp(12 + offset, new Timestamp(labReq.getLastEdited().getTime()));

      pStatement.addBatch();
      pStatement.executeBatch();
      labRequestMap.put(labReq.getRequestID(), labReq);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
