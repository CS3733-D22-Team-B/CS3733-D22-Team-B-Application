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

  protected abstract void initDB();

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
  LinkedList<String> filteredSearchList = new LinkedList<String>();
    try {
            Connection connection = DriverManager.getConnection(DBURL);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
            int Elements = rs.getMetaData().getColumnCount();
            String listOfColumns = "";

            // rs = statement.executeQuery("SELECT * FROM " + tableType + "");

      /*while (rs.next()) {
        for (int i = 1; i < Elements + 1; i++) {
          // if (rs.getString(i) == input) {
          //  columnName = rs.getMetaData().getColumnName(i);
          // }
        }
      }

      System.out.println("ColName: " + columnName + ", Input: " + input);
            // rs = statement.executeQuery("SELECT * FROM " + tableType + " WHERE " + columnName + " = " +
            // input + "");

            // while (rs.next()) {
            // filteredSearchList.add(rs.getString(1));
            //  System.out.println("RS.GetString(): " + rs.getString(1));
            // }

            } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            }

            return filteredSearchList;
*/
