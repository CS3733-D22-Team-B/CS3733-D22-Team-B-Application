package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import javax.lang.model.util.Elements;

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

  protected void listDB(String databaseName, int Elements) {
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.getResultSet();
      rs = statement.executeQuery("SELECT * FROM " + databaseName + "");

      while (rs.next()) {
        System.out.print(databaseName + " { ");
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

  protected LinkedList<String> selectAll() {
    LinkedList<String> pkList = new LinkedList<String>();
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.getResultSet();
      rs = statement.executeQuery("SELECT * FROM " + tableType + "");

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
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath));List
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
}
