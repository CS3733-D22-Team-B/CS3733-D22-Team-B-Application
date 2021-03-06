package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class MedicalEquipmentDB extends DatabaseSuperclass implements IDatabases<MedicalEquipment> {

  private static MedicalEquipmentDB medicalEquipmentDBManager;
  private HashMap<String, MedicalEquipment> medicalEquipmentMap =
      new HashMap<String, MedicalEquipment>();

  private MedicalEquipmentDB() {
    super("MedicalEquipment", "equipmentID", Filepath.getInstance().getMedicalEQCSVFilePath());
    initDB();
  }

  public static MedicalEquipmentDB getInstance() {
    if (medicalEquipmentDBManager == null) {
      medicalEquipmentDBManager = new MedicalEquipmentDB();
    }
    return medicalEquipmentDBManager;
  }

  protected void initDB() {
    medicalEquipmentMap.clear(); // Remove residual objects in hashmap
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
                rs.getString(5),
                rs.getString(6));
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

  public MedicalEquipment getByID(String id) {
    if (!medicalEquipmentMap.containsKey(id)) {
      return null;
    }
    return medicalEquipmentMap.get(id);
  }

  public LinkedList<MedicalEquipment> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<MedicalEquipment> locList = new LinkedList<MedicalEquipment>();

    for (int i = 0; i < pkList.size(); i++) {
      locList.add(medicalEquipmentMap.get(pkList.get(i)));
    }
    return locList;
  }

  public LinkedList<MedicalEquipment> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<MedicalEquipment> list = new LinkedList<MedicalEquipment>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(medicalEquipmentMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<MedicalEquipment> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<MedicalEquipment> list = new LinkedList<MedicalEquipment>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(medicalEquipmentMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<MedicalEquipment> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<MedicalEquipment> list = new LinkedList<MedicalEquipment>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(medicalEquipmentMap.get(pkList.get(i)));
    }
    return list;
  }

  public int update(MedicalEquipment medObj) {
    if (!medicalEquipmentMap.containsKey(medObj.getEquipmentID())) {
      return -1;
    }
    return transform(
        medObj,
        "UPDATE MedicalEquipment SET nodeID = ?, type = ?, isClean = ?, availability = ?, name = ?  WHERE equipmentID = ?",
        true);
  }

  public int add(MedicalEquipment medObj) {
    if (medicalEquipmentMap.containsKey(medObj.getEquipmentID())) {
      return -1;
    }
    return transform(medObj, "INSERT INTO MedicalEquipment VALUES(?,?,?,?,?,?)", false);
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
        pStatement.setString(6, medObj.getEquipmentID());
        offset = -1;
      } else {
        pStatement.setString(1, medObj.getEquipmentID());
      }

      pStatement.setString(2 + offset, medObj.getNodeID());
      pStatement.setString(3 + offset, medObj.getType());
      pStatement.setBoolean(4 + offset, medObj.getIsClean());
      pStatement.setString(5 + offset, medObj.getAvailability());
      pStatement.setString(6 + offset, medObj.getName());

      pStatement.addBatch();
      pStatement.executeBatch();
      medicalEquipmentMap.put(medObj.getEquipmentID(), medObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public String getEquipmentID(String name) {
    Stream<String> keys =
        medicalEquipmentMap.entrySet().stream()
            .filter(entry -> name.equals(entry.getValue().getName()))
            .map(Map.Entry::getKey);
    return keys.findFirst().orElse(null);
  }
}
