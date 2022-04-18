package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import javax.lang.model.util.Elements;

public abstract class DatabaseSuperclass {
  protected String embedded = "jdbc:derby:Databases;";
  protected String remote = "jdbc:derby://localhost:1527/Databases;";
  protected String url = embedded;

  protected String tableType;
  protected String pkName;
  protected String filePath;
  protected boolean isRemote = false;

  public DatabaseSuperclass(String initTableType, String initPkName, String initFilePath) {
    tableType = initTableType;
    pkName = initPkName;
    filePath = initFilePath;
  }

  protected abstract void initDB();

  protected void listDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
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
      Connection connection = DriverManager.getConnection(url);
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

  /**
   * Outputs data from the table into a CSV file. Saves the file in the entered file path (requires
   * full file path)
   *
   * @param newFilePath Name of the file to save the data as. Include full
   * @return
   */
  protected int toCSV(String newFilePath) {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      OutputStream os = new FileOutputStream(newFilePath);
      Writer writer = new OutputStreamWriter(os, "US-ASCII");
      BufferedWriter fileWriter = new BufferedWriter(writer);
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

  public void downloadCSV(String fileName) {
    String newFilePath = System.getProperty("user.home") + "/Downloads/" + fileName + ".csv";
    toCSV(newFilePath);
  }

  /**
   * Saves current table data to the running application CSV folder automatically. Run on
   * application close.
   *
   * @return
   */
  protected int toCSV() {
    String newFilePath = "src/main/resources/edu/wpi/cs3733/D22/teamB/databases/" + filePath;
    toCSV(newFilePath);
    return 0;
  }

  protected LinkedList<String> filteredSearch(String input) {
    LinkedList<String> filteredSearchList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(url);
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

  protected LinkedList<String> searchWhere(String attribute, String value) {
    LinkedList<String> pkList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs =
          statement.executeQuery(
              "SELECT * FROM " + tableType + " WHERE " + attribute + " = " + "'" + value + "'");

      while (rs.next()) {
        pkList.add(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return pkList;
  }

  protected LinkedList<String> searchWhere(String attribute, int value) {
    LinkedList<String> pkList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs =
          statement.executeQuery(
              "SELECT * FROM " + tableType + " WHERE " + attribute + " = " + value);

      while (rs.next()) {
        pkList.add(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return pkList;
  }

  protected LinkedList<String> searchWhere(String attribute, boolean value) {
    LinkedList<String> pkList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs =
          statement.executeQuery(
              "SELECT * FROM " + tableType + " WHERE " + attribute + " = " + value);

      while (rs.next()) {
        pkList.add(rs.getString(1));
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return pkList;
  }

  protected int deleteFrom(String pk) {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      String sql = "DELETE FROM " + tableType + " WHERE " + pkName + " = '" + pk + "'";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public void quit() {
    // toCSV();
    // listDB();

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE " + tableType + "");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  public void switchConnection(boolean isRemote) {
    this.isRemote = isRemote;
    if (this.isRemote) {
      url = remote;
    } else {
      url = embedded;
    }
  }

  public boolean stringToBoolean(String input) {
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
