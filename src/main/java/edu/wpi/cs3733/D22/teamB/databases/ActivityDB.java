package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class ActivityDB extends DatabaseSuperclass implements IDatabases<Activity> {

  private static ActivityDB activityDBManager;
  private HashMap<String, Activity> activityMap = new HashMap<String, Activity>();

  private ActivityDB() {
    super("Activity", "time", Filepath.getInstance().getActivityCSVFilePath());
    initDB();
  }

  public static ActivityDB getInstance() {
    if (activityDBManager == null) {
      activityDBManager = new ActivityDB();
    }
    return activityDBManager;
  }

  protected void initDB() {
    activityMap.clear(); // Remove residual objects in hashmap
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Activity actOb =
            new Activity(
                new java.util.Date(rs.getTimestamp(1).getTime()),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6));
        activityMap.put(new java.util.Date(rs.getTimestamp(1).getTime()).toString(), actOb);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Activity> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Activity> actList = new LinkedList<Activity>();

    for (int i = 0; i < pkList.size(); i++) {
      actList.add(activityMap.get(pkList.get(i)));
    }
    return actList;
  }

  public Activity getByID(String id) {
    if (!activityMap.containsKey(id)) {
      return null;
    }
    return activityMap.get(id);
  }

  public LinkedList<Activity> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Activity> actList = new LinkedList<Activity>();
    for (int i = 0; i < pkList.size(); i++) {
      actList.add(activityMap.get(pkList.get(i)));
    }
    return actList;
  }

  public LinkedList<Activity> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Activity> list = new LinkedList<Activity>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(activityMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Activity> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Activity> list = new LinkedList<Activity>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(activityMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Activity> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Activity> list = new LinkedList<Activity>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(activityMap.get(pkList.get(i)));
    }
    return list;
  }

  public int update(Activity actObj) {
    if (!activityMap.containsKey(actObj.getTime().toString())) {
      return -1;
    }
    return transform(
        actObj,
        "UPDATE Activity SET employeeID = ?, typeID = ?, information = ?, type = ?, action = ? WHERE time = ?",
        true);
  }

  public int add(Activity actObj) {
    if (activityMap.containsKey(actObj.getTime().toString())) {
      return -1;
    }
    return transform(actObj, "INSERT INTO Patients VALUES(?,?,?,?,?,?)", false);
  }

  public int delete(Activity actObj) {
    if (!activityMap.containsKey(actObj.getTime().toString())) {
      return -1;
    }
    activityMap.remove(actObj.getTime().toString());
    return deleteFrom(actObj.getTime().toString());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(Activity actObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setTimestamp(6, new Timestamp(actObj.getTime().getTime()));
        offset = -1;
      } else {
        pStatement.setTimestamp(1, new Timestamp(actObj.getTime().getTime()));
      }

      pStatement.setString(2 + offset, actObj.getEmployeeID());
      pStatement.setString(3 + offset, actObj.getTypeID());
      pStatement.setString(4 + offset, actObj.getInformation());
      pStatement.setString(5 + offset, actObj.getType());
      pStatement.setString(6 + offset, actObj.getAction());

      pStatement.addBatch();
      pStatement.executeBatch();
      activityMap.put(actObj.getTime().toString(), actObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
