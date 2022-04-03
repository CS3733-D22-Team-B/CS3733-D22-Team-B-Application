package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public abstract class DatabaseSuperclass {
  protected final String DBURL = "jdbc:derby:Databases;";
  protected String tableType;
  protected String pkName;
  protected String filePath;

  public DatabaseSuperclass(String initTableType, String initPkName, String initFilePath) {
    tableType = initTableType;
    pkName = initPkName;
    filePath = initFilePath;
  }

  protected void listDB() {
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      int Elements = rs.getMetaData().getColumnCount();

      while (rs.next()) {
        System.out.print(tableType + " { ");
        for (int i = 1; i < Elements + 1; i++) {
          System.out.print(rs.getMetaData().getColumnName(i) + ": ");
          System.out.print(rs.getString(i) + ", ");
        }
        System.out.println(" }");
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  // This can be generalized completely someday
  protected LinkedList<String> selectAll() {
    LinkedList<String> pkList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");

      while (rs.next()) {
        pkList.add(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return pkList;
  }

  protected int toCSV() {
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath));
      int Elements = rs.getMetaData().getColumnCount();
      String listOfColumns = "";

      for (int i = 1; i < Elements + 1; i++) {
        listOfColumns += rs.getMetaData().getColumnName(i);
        if (i < Elements) {
          listOfColumns += ",";
        }
      }

      fileWriter.write(listOfColumns);

      while (rs.next()) {
        String line = "";
        for (int i = 1; i < Elements + 1; i++) {
          line += rs.getString(i);
          if (i < Elements) {
            line += ",";
          }
        }
        fileWriter.newLine();
        fileWriter.write(line);
      }

      fileWriter.close();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return -1;
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  protected LinkedList<String> filteredSearch(String input) {
    LinkedList<String> filteredSearchList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      int Elements = rs.getMetaData().getColumnCount();
      String columnName = "";

      while (rs.next()) {
        for (int i = 1; i < Elements + 1; i++) {
          if (rs.getString(i).compareTo(input) == 0) {
            columnName = rs.getMetaData().getColumnName(i);
            break;
          }
        }
      }

      ResultSet rs2 = statement.getResultSet();

      int type = 0;
      try {
        int intInput = Integer.parseInt(input);
        type = 1;
      } catch (NumberFormatException e) {
        try {
          boolean boolInput = stringToBoolean(input);
          type = 2;
        } catch (NumberFormatException b) {
          type = 0;
        }
      }

      if (type == 1) {
        rs2 =
            statement.executeQuery(
                "SELECT * FROM "
                    + tableType
                    + " WHERE "
                    + columnName
                    + " = "
                    + Integer.parseInt(input)
                    + "");
      } else if (type == 2) {
        rs2 =
            statement.executeQuery(
                "SELECT * FROM "
                    + tableType
                    + " WHERE "
                    + columnName
                    + " = "
                    + stringToBoolean(input)
                    + "");
      } else if (type == 0) {
        rs2 =
            statement.executeQuery(
                "SELECT * FROM " + tableType + " WHERE " + columnName + " = '" + input + "'");
      }

      while (rs2.next()) {
        filteredSearchList.add(rs2.getString(1));
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return filteredSearchList;
    }
    return filteredSearchList;
  }

  public void quit() {
    toCSV();
    listDB();

    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE " + tableType + "");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  ////////////////////////////////////////////////////////// Helper Functions
  private boolean stringToBoolean(String input) {
    if (input.toLowerCase().compareTo("true") == 0 || input.toLowerCase().compareTo("false") == 0) {
      if (input.toLowerCase().compareTo("true") == 0) {
        return true;
      } else {
        return false;
      }
    } else {
      throw new NumberFormatException();
    }
  }
}

/*
protected void initDB() {
  try {
    Connection connection = DriverManager.getConnection(DBURL);
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
    int Elements = rs.getMetaData().getColumnCount();

    // HashMap<String, Location> LocMap = new HashMap<String, Location>();

    while (rs.next()) {
      String[] data = new String[Elements + 1];
      for (int i = 1; i < Elements + 1; i++) {
        data[i] = rs.getString(i);
      }

      if (tableType == "Locations") {
        Location locOb =
                new Location(
                        data[1],
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        data[4],
                        data[5],
                        data[6],
                        data[7],
                        data[8]);
        // LocMap.put(data[0], locOb);
        LocationsDB.getInstance().getMap().put(data[1], locOb);
      } else if (tableType == "Patients") {
        Patient patOb = new Patient(data[1], data[2], data[3], data[4]);
        // PatientsDB.getInstance().getMap().put(data[1], patOb);
      } else if (tableType == "MedicalEquipment") {
        MedicalEquipment medOb =
                new MedicalEquipment(
                        data[1], data[2], data[3], stringToBoolean(data[4]), stringToBoolean(data[5]));
        // MedicalEquipmentDB.getInstance().getMap().put(data[1], medOb);
      }
    }
    if (tableType == "Locations") {
      LocationsDB.getInstance().SetMap(LocMap);
    } else if (tableType == "Patients") {
    } else if (tableType == "MedicalEquipment") {
    }
  } catch (SQLException e) {
    System.out.println("Connection failed. Check output console.");
    e.printStackTrace();
  }
}
*/
