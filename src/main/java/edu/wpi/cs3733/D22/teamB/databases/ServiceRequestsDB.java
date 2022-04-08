package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.requests.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class ServiceRequestsDB extends DatabaseSuperclass implements IDatabases<Request> {

  private final String backupFile = "CSVs/BackupServiceRequest.csv";
  private static ServiceRequestsDB serviceRequestsDBManager;
  private HashMap<String, Request> requestMap = new HashMap<String, Request>();

  public HashMap<String, Request> getRequestMap() {
    return requestMap;
  }

  public void setRequestMap(HashMap<String, Request> requestMap) {
    this.requestMap = requestMap;
  }

  private ServiceRequestsDB() {
    super("ServiceRequests", "requestID", "CSVs/ApplicationServiceRequest.csv");
    initDB();
  }

  public static ServiceRequestsDB getInstance() {
    if (serviceRequestsDBManager == null) {
      serviceRequestsDBManager = new ServiceRequestsDB();
    }
    return serviceRequestsDBManager;
  }

  protected void initDB() {
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Request req;
        String type = rs.getString(5);
        switch (type) {
          default:
          case "MEL":
            req =
                new MealRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
            break;
          case "MED":
            req =
                new MedicineRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
            break;
          case "INT":
            req =
                new InterpreterRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
            break;
          case "IPT":
            req =
                new InternalPatientTransferRequest(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
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
        "UPDATE ServiceRequests SET employeeID = ?, locationID = ?, transferID = ?, type = ?, status = ?, information = ? WHERE requestID = ?",
        true);
  }

  public int add(Request reqObj) {
    if (requestMap.containsKey(reqObj.getRequestID())) {
      return -1;
    }
    return transform(reqObj, "INSERT INTO ServiceRequests VALUES(?,?,?,?,?,?,?)", false);
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
      Connection connection = DriverManager.getConnection(DBURL);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(7, reqObj.getRequestID());
        offset = -1;
      } else {
        pStatement.setString(1, reqObj.getRequestID());
      }

      pStatement.setString(2 + offset, reqObj.getEmployeeID());
      pStatement.setString(3 + offset, reqObj.getLocationID());
      if (reqObj.getType().equalsIgnoreCase("TRAN")) {
        InternalPatientTransferRequest iptrObj = (InternalPatientTransferRequest) reqObj;
        pStatement.setString(4 + offset, iptrObj.getDestinationID());
      }
      pStatement.setString(5 + offset, reqObj.getType());
      pStatement.setString(6 + offset, reqObj.getStatus());
      pStatement.setString(7 + offset, reqObj.getInformation());

      pStatement.addBatch();
      pStatement.executeBatch();
      requestMap.put(reqObj.getRequestID(), reqObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
