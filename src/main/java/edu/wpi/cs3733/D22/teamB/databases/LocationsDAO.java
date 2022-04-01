package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class LocationsDAO extends DatabaseSuperclass implements LocationDAOImpl {

  private final String url = "jdbc:derby:Databases";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/LocationsBackup.csv";

  private HashMap<String, Location> locationMap = new HashMap<String, Location>();;

  public LocationsDAO() {
    locationMap = LocationsInit();
  }

  private HashMap<String, Location> LocationsInit() {
    HashMap<String, Location> locHM = new HashMap<String, Location>();
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
        locHM.put(nodeID, locOb);
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return locHM;
  }

  public LinkedList<Location> listLocations() {
    LinkedList<Location> locationList = new LinkedList<Location>();

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM Locations");

      while (rs.next()) {
        String nodeID = rs.getString("nodeID");

        locationList.add(locationMap.get(nodeID));
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return locationList;
  }

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

  public void locationsToCSV() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM Locations");

      // Create file writer and create header row
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(backupFile));
      fileWriter.write("nodeId, xCoord, yCoord, floor, building, nodeType, longName, shortName");

      while (rs.next()) {
        String nodeID = rs.getString("nodeID");
        int xCoord = rs.getInt("xcoord");
        int yCoord = rs.getInt("ycoord");
        String floor = rs.getString("floor");
        String building = rs.getString("building");
        String nodeType = rs.getString("nodeType");
        String longName = rs.getString("longName");
        String shortName = rs.getString("shortName");

        String line =
            String.format(
                "%s,%d,%d,%s,%s,%s,%s,%s",
                nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);

        fileWriter.newLine();
        fileWriter.write(line);
      }
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
    }
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
    this.locationsToCSV();
    listDB("Locations", 8);

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
