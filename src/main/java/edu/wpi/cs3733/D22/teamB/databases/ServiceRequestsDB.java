package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class ServiceRequestsDB extends DatabaseSuperclass implements IDatabases<Request> {

  private static ServiceRequestsDB serviceRequestsDBManager;
  private HashMap<String, Request> requestMap = new HashMap<String, Request>();

  private ServiceRequestsDB() {
    super("ServiceRequests", "requestID", Filepath.getInstance().getServiceRequestCSVFilePath());
    initDB();
  }

  public static ServiceRequestsDB getInstance() {
    if (serviceRequestsDBManager == null) {
      serviceRequestsDBManager = new ServiceRequestsDB();
    }
    return serviceRequestsDBManager;
  }

  protected void initDB() {
    requestMap.clear(); // Remove residual objects in hashmap
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Request req;
        String type = rs.getString(8).toUpperCase();
        switch (type) {
          case "LAB TEST":
            req =
                new LabRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    new java.util.Date(rs.getTimestamp(7).getTime()),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "EQUIPMENT DELIVERY":
            req =
                new EquipmentRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "MEAL":
            req =
                new MealRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "MEDICINE":
            req =
                new MedicineRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "INTERPRETER":
            req =
                new InterpreterRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "PATIENT TRANSFER":
            req =
                new InternalPatientTransferRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "LAUNDRY":
            req =
                new LaundryRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "GIFT":
            req =
                new GiftRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "SECURITY":
            req =
                new SecurityRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
          case "SANITATION":
            req =
                new SanitationRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
          default:
            req =
                new CustomRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    null,
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    new java.util.Date(rs.getTimestamp(12).getTime()),
                    new java.util.Date(rs.getTimestamp(13).getTime()));
            break;
        }
        requestMap.put(rs.getString(1), req);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Request> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Request> reqList = new LinkedList<Request>();

    for (int i = 0; i < pkList.size(); i++) {
      reqList.add(requestMap.get(pkList.get(i)));
    }
    return reqList;
  }

  public LinkedList<Request> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Request> reqList = new LinkedList<Request>();
    for (int i = 0; i < pkList.size(); i++) {
      reqList.add(requestMap.get(pkList.get(i)));
    }
    return reqList;
  }

  public LinkedList<Request> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Request> list = new LinkedList<Request>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(requestMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Request> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Request> list = new LinkedList<Request>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(requestMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Request> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Request> list = new LinkedList<Request>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(requestMap.get(pkList.get(i)));
    }
    return list;
  }

  public Request getByID(String id) {
    if (!requestMap.containsKey(id)) {
      return null;
    }
    return requestMap.get(id);
  }

  public int update(Request reqObj) {
    if (!requestMap.containsKey(reqObj.getRequestID())) {
      return -1;
    }
    return transform(
        reqObj,
        "UPDATE ServiceRequests SET employeeID = ?, locationID = ?, patientID = ?, equipmentID = ?, testType = ?, testDate = ?, type = ?, status = ?, priority = ?, information = ?, timeCreated = ?, lastEdited = ? WHERE requestID = ?",
        true);
  }

  public int add(Request reqObj) {
    if (requestMap.containsKey(reqObj.getRequestID())) {
      return -1;
    }
    return transform(
        reqObj, "INSERT INTO ServiceRequests VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", false);
  }

  public int delete(Request reqObj) {
    if (!requestMap.containsKey(reqObj.getRequestID())) {
      return -1;
    }
    requestMap.remove(reqObj.getRequestID());
    return deleteFrom(reqObj.getRequestID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(Request reqObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(13, reqObj.getRequestID());
        offset = -1;
      } else {
        pStatement.setString(1, reqObj.getRequestID());
      }

      pStatement.setString(2 + offset, reqObj.getEmployeeID());
      pStatement.setString(3 + offset, reqObj.getLocationID());
      pStatement.setString(4 + offset, reqObj.getPatientID());
      pStatement.setString(5 + offset, reqObj.getEquipmentID());
      pStatement.setString(6 + offset, reqObj.getTestType());
      if (reqObj.getTestDate() != null) {
        pStatement.setTimestamp(7 + offset, new Timestamp(reqObj.getTestDate().getTime()));
      }
      pStatement.setString(8 + offset, reqObj.getType());
      pStatement.setString(9 + offset, reqObj.getStatus());
      pStatement.setInt(10 + offset, reqObj.getPriority());
      pStatement.setString(11 + offset, reqObj.getInformation());
      pStatement.setTimestamp(12 + offset, new Timestamp(reqObj.getTimeCreated().getTime()));
      pStatement.setTimestamp(13 + offset, new Timestamp(reqObj.getLastEdited().getTime()));

      pStatement.addBatch();
      pStatement.executeBatch();
      requestMap.put(reqObj.getRequestID(), reqObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }
}
