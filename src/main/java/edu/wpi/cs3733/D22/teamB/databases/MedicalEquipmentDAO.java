package edu.wpi.cs3733.D22.teamB.databases;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class MedicalEquipmentDAO implements MedicalEquipmentImpl {
  private final String url = "jdbc:derby:Databases;";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/MedicalEquipment.csv";

  private HashMap<String, MedicalEquipment> medicalEquipmentMap =
      new HashMap<String, MedicalEquipment>();;

  public MedicalEquipmentDAO() {
    medicalEquipmentMap = MedicalEquipmentInit();
  }

  public HashMap<String, MedicalEquipment> MedicalEquipmentInit() {
    HashMap<String, MedicalEquipment> medHM = new HashMap<String, MedicalEquipment>();
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
        medHM.put(equipmentID, medOb);
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return medHM;
  }

  public LinkedList<MedicalEquipment> listMedicalEquipment() {
    LinkedList<MedicalEquipment> medEqList = new LinkedList<MedicalEquipment>();

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM MedicalEquipment");

      while (rs.next()) {
        String equipmentID = rs.getString("equipmentID");

        medEqList.add(medicalEquipmentMap.get(equipmentID));
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    return medEqList;
  }

  public void medicalEquipmentToCSV() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM MedicalEquipment");

      // Create file writer and create header row
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(backupFile));
      fileWriter.write("equipmentID, nodeID, type, isClean, isRequested");

      while (rs.next()) {
        String equipmentID = rs.getString("equipmentID");
        String nodeID = rs.getString("nodeID");
        String type = rs.getString("type");
        boolean isClean = rs.getBoolean("isClean");
        boolean isRequested = rs.getBoolean("isRequested");

        String line =
            String.format("%s,%s,%s,%b,%b", equipmentID, nodeID, type, isClean, isRequested);

        fileWriter.newLine();
        fileWriter.write(line);
      }
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
      return;
    }
  }

  public int updateMedicalEquipment(MedicalEquipment meObj) {
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

  public int addMedicalEquipment(MedicalEquipment meObj) {
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

  public int deleteMedicalEquipment(MedicalEquipment meObj) {
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
}

/////////////////////////////////////////// Basement
/*  public void connect(int feature) {
  switch (feature) {
    case 1:
      System.out.println("User entered 1");
      listMedicalEquipment();
      break;
    case 2:
      System.out.println("User entered 2");
      break;
    case 3:
      System.out.println("User entered 3");
      addMedicalEquipment();
      break;
    case 4:
      System.out.println("User entered 4");
      deleteMedicalEquipment();
      break;
    case 5:
      System.out.println("User entered 5");

      break;
    case 6:
      System.out.println("User entered 6");
      backupMedicalEquipmentToCSV();
      break;
    default:
      break;
  }
}












public void addMedicalEquipment() {
  // Initialize scanner
  Scanner scanner = new Scanner(System.in);

  // Input nodeID
  System.out.print("Enter new medical equipment object ID here: ");
  String inputMedID = scanner.nextLine();

  // nodeID has to be unique
  while (MedicalEquipmentMap.containsKey(inputMedID)) {
    System.out.print("Invalid medical equipment object ID, try again: ");
    inputMedID = scanner.nextLine();
  }
  System.out.println();

  System.out.println("Enter a valid location that this object exists in: ");

  // Establish database connection
  try {
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    String sql =
            "INSERT INTO MedicalEquipment VALUES ('" + inputMedID + "', null, null, false, false)";
    statement.executeUpdate(sql);
  } catch (SQLException e) {
    System.out.println("Connection failed.");
    return;
  }

  // Create location object
  MedicalEquipment medObj = new MedicalEquipment(inputMedID, null, null, true, true);
  MedicalEquipmentMap.put(inputMedID, medObj);
}

public void deleteMedicalEquipment() {
  // Initialize scanner
  Scanner scanner = new Scanner(System.in);

  // Input nodeID
  System.out.print("Enter medical equipment object ID to delete: ");
  String inputID = scanner.nextLine();

  // nodeID has to be unique
  while (!MedicalEquipmentMap.containsKey(inputID)) {
    System.out.print("Invalid medical equipment object ID, try again: ");
    inputID = scanner.nextLine();
  }
  System.out.println();

  // Establish database connection
  try {
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    String sql = "DELETE FROM MedicalEquipment WHERE equipmentID = '" + inputID + "'";
    statement.executeUpdate(sql);
  } catch (SQLException e) {
    System.out.println("Connection failed.");
    return;
  }

  // Remove location object
  MedicalEquipmentMap.remove(inputID);
}

public void backupMedicalEquipmentToCSV() {
  try {
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery("SELECT * FROM MedicalEquipment");

    // Create file writer and create header row
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(backupFile));
    fileWriter.write("equipmentID, nodeID, type, isClean, isRequested");

    while (rs.next()) {
      String equipmentID = rs.getString("equipmentID");
      String nodeID = rs.getString("nodeID");
      String type = rs.getString("type");
      boolean isClean = rs.getBoolean("isClean");
      boolean isRequested = rs.getBoolean("isRequested");

      String line =
              String.format("%s,%s,%s,%b,%b", equipmentID, nodeID, type, isClean, isRequested);

      fileWriter.newLine();
      fileWriter.write(line);
    }
    fileWriter.close();

  } catch (SQLException e) {
    System.out.println("Connection failed. Check output console.");
    e.printStackTrace();
    return;
  } catch (IOException e) {
    System.out.println("File IO error:");
    e.printStackTrace();
    return;
  }
}
*/

/*
public void listMedicalEquipment() {
  try {
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery("SELECT * FROM MedicalEquipment");
    while (rs.next()) { // While rows exist in the result set, parse and print each one
      // Grab each attribute from the row
      String equipmentID = rs.getString("equipmentID");
      String nodeID = rs.getString("nodeID");
      String type = rs.getString("type");
      boolean isClean = rs.getBoolean("isClean");
      boolean isRequested = rs.getBoolean("isRequested");

      System.out.println(
              "Location { "
                      + "EquipmentID: "
                      + equipmentID
                      + ", NodeID: "
                      + nodeID
                      + ", Type: "
                      + type
                      + ", IsClean: "
                      + isClean
                      + ", IsRequested: "
                      + isRequested
                      + " }");
    }
  } catch (SQLException e) {
    System.out.println("Connection failed. Check output console.");
    e.printStackTrace();
    return;
  }
}
*/
