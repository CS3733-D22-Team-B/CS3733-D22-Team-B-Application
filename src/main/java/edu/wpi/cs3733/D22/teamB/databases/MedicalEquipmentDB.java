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

  protected void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        MedicalEquipment medOb =
            new MedicalEquipment(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getBoolean(4),
                rs.getBoolean(5));
        medicalEquipmentMap.put(rs.getString(1), medOb);
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

  public LinkedList<MedicalEquipment> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<MedicalEquipment> locList = new LinkedList<MedicalEquipment>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(medicalEquipmentMap.get(pkList.get(i)));
    }
    return locList;
  }

  public int update(MedicalEquipment medObj) {
    if (!medicalEquipmentMap.containsKey(medObj.getEquipmentID())) {
      return -1;
    }
    return transform(
        medObj,
        "UPDATE MedicalEquipment SET nodeID = ?, type = ?, isClean = ?, isRequested = ? WHERE equipmentID = ?",
        true);
  }

  public int add(MedicalEquipment medObj) {
    if (medicalEquipmentMap.containsKey(medObj.getEquipmentID())) {
      return -1;
    }
    return transform(medObj, "INSERT INTO MedicalEquipment VALUES(?,?,?,?,?)", false);
  }

  public int delete(MedicalEquipment medObj) {
    if (!medicalEquipmentMap.containsKey(medObj.getEquipmentID())) {
      return -1;
    }
    medicalEquipmentMap.remove(medObj.getEquipmentID());
    return deleteFrom(medObj.getEquipmentID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(MedicalEquipment medObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(5, medObj.getEquipmentID());
        offset = -1;
      } else {
        pStatement.setString(1, medObj.getEquipmentID());
      }

      pStatement.setString(2 + offset, medObj.getNodeID());
      pStatement.setString(3 + offset, medObj.getType());
      pStatement.setBoolean(4 + offset, medObj.getIsClean());
      pStatement.setBoolean(5 + offset, medObj.getIsRequested());

      pStatement.addBatch();
      pStatement.executeBatch();
      medicalEquipmentMap.put(medObj.getEquipmentID(), medObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
