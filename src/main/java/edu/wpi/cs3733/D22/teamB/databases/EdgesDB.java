package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class EdgesDB extends DatabaseSuperclass implements IDatabases<Edge> {
  // private final String url = "jdbc:derby:Databases;";

  private static EdgesDB edgesDBManager;
  private HashMap<String, Edge> edgeMap = new HashMap<String, Edge>();

  public HashMap<String, Edge> getEdgeMap() {
    return edgeMap;
  }

  public void setEdgeMap(HashMap<String, Edge> edgeMap) {
    this.edgeMap = edgeMap;
  }

  private EdgesDB() {
    super("Edges", "edgeID", Filepath.getInstance().getEdgesCSVFilePath());
    initDB();
  }

  public static EdgesDB getInstance() {
    if (edgesDBManager == null) {
      edgesDBManager = new EdgesDB();
    }
    return edgesDBManager;
  }

  protected void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Edge edgeOb = new Edge(rs.getString(1), rs.getString(2), rs.getString(3));
        edgeMap.put(rs.getString(1), edgeOb);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Edge> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Edge> edgeList = new LinkedList<Edge>();

    for (int i = 0; i < pkList.size(); i++) {
      edgeList.add(edgeMap.get(pkList.get(i)));
    }
    return edgeList;
  }

  public Edge getByID(String id) {
    if (!edgeMap.containsKey(id)) {
      return null;
    }
    return edgeMap.get(id);
  }

  public LinkedList<Edge> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Edge> edgeList = new LinkedList<Edge>();
    for (int i = 0; i < pkList.size(); i++) {
      edgeList.add(edgeMap.get(pkList.get(i)));
    }
    return edgeList;
  }

  public LinkedList<Edge> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Edge> list = new LinkedList<Edge>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(edgeMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Edge> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Edge> list = new LinkedList<Edge>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(edgeMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Edge> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Edge> list = new LinkedList<Edge>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(edgeMap.get(pkList.get(i)));
    }
    return list;
  }

  public int update(Edge edgeObj) {
    if (!edgeMap.containsKey(edgeObj.getEdgeID())) {
      return -1;
    }
    return transform(
        edgeObj,
        "UPDATE Patients SET lastName = ?, firstName = ?, nodeID = ? WHERE patientID = ?",
        true);
  }

  public int add(Edge edgeObj) {
    if (edgeMap.containsKey(edgeObj.getEdgeID())) {
      return -1;
    }
    return transform(edgeObj, "INSERT INTO Patients VALUES(?,?,?,?)", false);
  }

  public int delete(Edge edgeObj) {
    if (!edgeMap.containsKey(edgeObj.getEdgeID())) {
      return -1;
    }
    edgeMap.remove(edgeObj.getEdgeID());
    return deleteFrom(edgeObj.getEdgeID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(Edge edgeObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(4, edgeObj.getEdgeID());
        offset = -1;
      } else {
        pStatement.setString(1, edgeObj.getEdgeID());
      }

      pStatement.setString(2 + offset, edgeObj.nodeID1);
      pStatement.setString(3 + offset, edgeObj.nodeID2);

      pStatement.addBatch();
      pStatement.executeBatch();
      edgeMap.put(edgeObj.getEdgeID(), edgeObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
