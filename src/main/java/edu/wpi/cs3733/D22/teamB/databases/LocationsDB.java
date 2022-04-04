package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class LocationsDB extends DatabaseSuperclass implements IDatabases<Location> {

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

  protected void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Location locOb =
            new Location(
                rs.getString(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8));
        locationMap.put(rs.getString(1), locOb);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Location> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Location> locList = new LinkedList<Location>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(locationMap.get(pkList.get(i)));
    }
    return locList;
  }

  public Location getByID(String id) {
    if (!locationMap.containsKey(id)) {
      return null;
    }
    return locationMap.get(id);
  }

  public LinkedList<Location> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Location> locList = new LinkedList<Location>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(locationMap.get(pkList.get(i)));
    }
    return locList;
  }

  public int update(Location locObj) {
    if (!locationMap.containsKey(locObj.getNodeID())) {
      return -1;
    }
    return transform(
        locObj,
        "UPDATE Locations SET xcoord = ?, ycoord = ?, floor = ?, building = ?, nodeType = ?, longName = ?, shortName = ? WHERE nodeID = ?",
        true);
  }

  public int add(Location locObj) {
    if (locationMap.containsKey(locObj.getNodeID())) {
      return -1;
    }
    return transform(locObj, "INSERT INTO Locations VALUES(?,?,?,?,?,?,?,?)", false);
  }

  public int delete(Location locObj) {
    if (!locationMap.containsKey(locObj.getNodeID())) {
      return -1;
    }
    locationMap.remove(locObj.getNodeID());
    return deleteFrom(locObj.getNodeID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(Location locObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(8, locObj.getNodeID());
        offset = -1;
      } else {
        pStatement.setString(1, locObj.getNodeID());
      }

      pStatement.setInt(2 + offset, locObj.getXCoord());
      pStatement.setInt(3 + offset, locObj.getYCoord());
      pStatement.setString(4 + offset, locObj.getFloor());
      pStatement.setString(5 + offset, locObj.getBuilding());
      pStatement.setString(6 + offset, locObj.getNodeType());
      pStatement.setString(7 + offset, locObj.getLongName());
      pStatement.setString(8 + offset, locObj.getShortName());

      pStatement.addBatch();
      pStatement.executeBatch();
      locationMap.put(locObj.getNodeID(), locObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
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

  public LinkedList<Location> getLocationsByFloor(int floor) {
    String floorName = "";
    switch (floor) {
      case 0:
        floorName = "L2";
        break;
      case 1:
        floorName = "L1";
        break;
      case 2:
        floorName = "1";
        break;
      case 3:
        floorName = "2";
        break;
      case 4:
        floorName = "3";
        break;
    }

    LinkedList<Location> locList = new LinkedList<Location>();
    for (String key : locationMap.keySet()) {
      if (locationMap.get(key).getFloor().equals(floorName)) {
        locList.add(locationMap.get(key));
      }
    }
    return locList;
  }
}
