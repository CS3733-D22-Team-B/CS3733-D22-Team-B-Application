package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;

public class DatabaseSuperclass implements DatabaseSuperclassInterface {
  final String DBURL = "jdbc:derby:Databases;create=true";

  public void listDB(String databaseName, int Elements) {
    try {
      Connection connection = DriverManager.getConnection(DBURL);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.getResultSet();

      if (databaseName == "Locations") {
        rs = statement.executeQuery("SELECT * FROM Locations");
      } else if (databaseName == "MedicalEquipment") {
        rs = statement.executeQuery("SELECT * FROM MedicalEquipment");
      } else if (databaseName == "Patients") {
        rs = statement.executeQuery("SELECT * FROM Patients");
      }
      else if (databaseName == "Employees") {
      rs = statement.executeQuery("SELECT * FROM Employees");
      }

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
}
