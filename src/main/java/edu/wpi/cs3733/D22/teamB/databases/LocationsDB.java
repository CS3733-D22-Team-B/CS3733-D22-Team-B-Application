package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class LocationsDB extends DatabaseSuperclass implements ILocationsDB {

  private final String url = "jdbc:derby:Databases";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupLocations.csv";
  private static LocationsDB locationsDBManager;

  private HashMap<String, Location> locationMap = new HashMap<String, Location>();

  private LocationsDB() {
    super(
        "Locations",
        "nodeID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationLocations.csv");
    initDB();
  }

  public static LocationsDB getInstance() {
    if (locationsDBManager == null) {
      locationsDBManager = new LocationsDB();
    }
    return locationsDBManager;
  }

  public void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM Locations");

      while (rs.next()) {
        String nodeID = rs.getString("nodeID");
        int xCoord = rs.getInt("xcoord");
        int yCoord = rs.getInt("ycoord");
        String floor = rs.getString("floor");
        String building = rs.getString("building");
        String nodeType = rs.getString("nodeType");
        String longName = rs.getString("longName");
        String shortName = rs.getString("shortName");

        Location locOb =
            new Location(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
        locationMap.put(nodeID, locOb);
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Location> listLocations() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Location> locList = new LinkedList<Location>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(locationMap.get(pkList.get(i)));
    }
    return locList;
  }

  ////////////////////////////////////////////////////////////// To Fix
  public Location getLocation(String nodeID) {
    return locationMap.get(nodeID);
  }

  public String getLocationID(String longName) {
    Stream<String> keys =
        locationMap.entrySet().stream()
            .filter(entry -> longName.equals(entry.getValue().getLongName()))
            .map(Map.Entry::getKey);
    return keys.findFirst().orElse(null);
  }

  public int updateLocation(Location locObj) {
    try {
      Connection connection = DriverManager.getConnection(url);

      // If the location does not exist in the database, return -1
      if (locationMap.containsKey(locObj.getNodeID()) == false) {
        return -1;
      }

      String sql =
          "UPDATE Locations SET xcoord = ?, ycoord = ?, floor = ?, building = ?, nodeType = ?, longName = ?, shortName = ? WHERE nodeID = ?";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setInt(1, locObj.getXCoord());
      pStatement.setInt(2, locObj.getYCoord());
      pStatement.setString(3, locObj.getFloor());
      pStatement.setString(4, locObj.getBuilding());
      pStatement.setString(5, locObj.getNodeType());
      pStatement.setString(6, locObj.getLongName());
      pStatement.setString(7, locObj.getShortName());
      pStatement.setString(8, locObj.getNodeID());

      pStatement.addBatch();
      pStatement.executeBatch();

      locationMap.put(locObj.getNodeID(), locObj);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  public int addLocation(Location locObj) {
    // nodeID has to be unique
    if (locationMap.containsKey(locObj.getNodeID())) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);

      String sql = "INSERT INTO Locations VALUES(?,?,?,?,?,?,?,?)";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, locObj.getNodeID());
      pStatement.setInt(2, locObj.getXCoord());
      pStatement.setInt(3, locObj.getYCoord());
      pStatement.setString(4, locObj.getFloor());
      pStatement.setString(5, locObj.getBuilding());
      pStatement.setString(6, locObj.getNodeType());
      pStatement.setString(7, locObj.getLongName());
      pStatement.setString(8, locObj.getShortName());

      pStatement.addBatch();
      pStatement.executeBatch();

      locationMap.put(locObj.getNodeID(), locObj);

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public int deleteLocation(Location locObj) {
    // nodeID has to exist
    if (locationMap.containsKey(locObj.getNodeID()) == false) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      String sql = "DELETE FROM Locations WHERE nodeID = '" + locObj.getNodeID() + "'";
      statement.executeUpdate(sql);

      locationMap.remove(locObj.getNodeID());

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
      statement.execute("DROP TABLE Locations");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }
}