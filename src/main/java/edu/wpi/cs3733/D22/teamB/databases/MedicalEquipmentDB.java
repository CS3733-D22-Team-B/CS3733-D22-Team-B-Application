package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class MedicalEquipmentDB extends DatabaseSuperclass implements IDatabases<MedicalEquipment> {
  private final String url = "jdbc:derby:Databases;";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupMedicalEquipment.csv";
  private static MedicalEquipmentDB medicalEquipmentDBManager;

  private HashMap<String, MedicalEquipment> medicalEquipmentMap =
      new HashMap<String, MedicalEquipment>();;

  private MedicalEquipmentDB() {
    super(
        "MedicalEquipment",
        "equipmentID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationMedicalEquipment.csv");
    initDB();
  }

  public static MedicalEquipmentDB getInstance() {
    if (medicalEquipmentDBManager == null) {
      medicalEquipmentDBManager = new MedicalEquipmentDB();
    }
    return medicalEquipmentDBManager;
  }

  public void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM MedicalEquipment");

      while (rs.next()) {
        String equipmentID = rs.getString("equipmentID");
        String nodeID = rs.getString("nodeID");
        String type = rs.getString("type");
        boolean isClean = rs.getBoolean("isClean");
        boolean isRequested = rs.getBoolean("isRequested");

        MedicalEquipment medOb =
            new MedicalEquipment(equipmentID, nodeID, type, isClean, isRequested);
        medicalEquipmentMap.put(equipmentID, medOb);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<MedicalEquipment> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<MedicalEquipment> medEqList = new LinkedList<MedicalEquipment>();

    for (int i = 0; i < pkList.size(); i++) {
      medEqList.add(medicalEquipmentMap.get(pkList.get(i)));
    }
    return medEqList;
  }

  public MedicalEquipment getByID(String id) {
    if (!medicalEquipmentMap.containsKey(id)) {
      return null;
    }
    return medicalEquipmentMap.get(id);
  }

  ////////////////////////////////////////////////////////////// To Fix
  public int update(MedicalEquipment meObj) {
    try {
      Connection connection = DriverManager.getConnection(url);

      // If the medical equipment does not exist in the database, return -1
      if (medicalEquipmentMap.containsKey(meObj.getEquipmentID()) == false) {
        return -1;
      }

      String sql =
          "UPDATE MedicalEquipment SET nodeID = ?, type = ?, isClean = ?, isRequested = ? WHERE equipmentID = ?";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, meObj.getNodeID());
      pStatement.setString(2, meObj.getType());
      pStatement.setBoolean(3, meObj.getIsClean());
      pStatement.setBoolean(4, meObj.getIsRequested());
      pStatement.setString(5, meObj.getEquipmentID());

      pStatement.addBatch();
      pStatement.executeBatch();

      medicalEquipmentMap.put(meObj.getEquipmentID(), meObj);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  public int add(MedicalEquipment meObj) {
    // equipmentID has to be unique
    if (medicalEquipmentMap.containsKey(meObj.getEquipmentID())) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);

      String sql = "INSERT INTO MedicalEquipment VALUES(?,?,?,?,?)";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, meObj.getEquipmentID());
      pStatement.setString(2, meObj.getNodeID());
      pStatement.setString(3, meObj.getType());
      pStatement.setBoolean(4, meObj.getIsClean());
      pStatement.setBoolean(5, meObj.getIsRequested());

      pStatement.addBatch();
      pStatement.executeBatch();

      medicalEquipmentMap.put(meObj.getEquipmentID(), meObj);

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public int delete(MedicalEquipment meObj) {
    // equipmentID has to exist
    if (medicalEquipmentMap.containsKey(meObj.getEquipmentID()) == false) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      String sql =
          "DELETE FROM MedicalEquipment WHERE equipmentID = '" + meObj.getEquipmentID() + "'";
      statement.executeUpdate(sql);

      medicalEquipmentMap.remove(meObj.getEquipmentID());

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
      statement.execute("DROP TABLE MedicalEquipment");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }
}
