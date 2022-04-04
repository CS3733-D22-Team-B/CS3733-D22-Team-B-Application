package edu.wpi.cs3733.D22.teamB.databases;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class EmployeeDB extends DatabaseSuperclass implements IDatabases<Employee> {
  private final String url = "jdbc:derby:Databases";
  private final String backupFile =
      "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/BackupEmployees.csv";
  private static EmployeeDB employeeDBManager;

  private HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();

  private EmployeeDB() {
    super(
        "Employees",
        "employeeID",
        "src/main/resources/edu/wpi/cs3733/D22/teamB/CSVs/ApplicationEmployees.csv");
    initDB();
  }

  public static EmployeeDB getInstance() {
    if (employeeDBManager == null) {
      employeeDBManager = new EmployeeDB();
    }
    return employeeDBManager;
  }

  public void initDB() {
    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM " + tableType + "");
      while (rs.next()) {
        Employee empOb =
            new Employee(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5));
        employeeMap.put(rs.getString(1), empOb);
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public LinkedList<Employee> list() {
    LinkedList<String> pkList = selectAll();
    LinkedList<Employee> employeeList = new LinkedList<Employee>();

    for (int i = 0; i < pkList.size(); i++) {
      employeeList.add(employeeMap.get(pkList.get(i)));
    }
    return employeeList;
  }

  public LinkedList<Employee> searchFor(String input) {
    LinkedList<String> pkList = filteredSearch(input);
    LinkedList<Employee> empList = new LinkedList<Employee>();

    for (int i = 0; i < pkList.size(); i++) {
      empList.add(employeeMap.get(pkList.get(i)));
    }
    return empList;
  }

  public Employee getByID(String id) {
    if (!employeeMap.containsKey(id)) {
      return null;
    }
    return employeeMap.get(id);
  }

  ////////////////////////////////////////////////////////////// To Fix
  public Employee getEmployee(String employeeId) {
    return employeeMap.get(employeeId);
  }

  public int update(Employee empObj) {
    if (!employeeMap.containsKey(empObj.getEmployeeID())) {
      return -1;
    }
    return transform(
        empObj,
        "UPDATE Employees SET lastName = ?, firstName = ?, department = ?, position = ? WHERE employeeID = ?",
        true);
  }

  public int add(Employee empObj) {
    if (employeeMap.containsKey(empObj.getEmployeeID())) {
      return -1;
    }
    return transform(empObj, "INSERT INTO Employees VALUES(?,?,?,?,?)", false);
  }

  public int delete(Employee empObj) {
    if (!employeeMap.containsKey(empObj.getEmployeeID())) {
      return -1;
    }
    employeeMap.remove(empObj.getEmployeeID());
    return deleteFrom(empObj.getEmployeeID());
  }

  /////////////////////////////////////////////////////////////////////// Helper
  private int transform(Employee empObj, String sql, boolean isUpdate) {
    try {
      Connection connection = DriverManager.getConnection(url);
      PreparedStatement pStatement = connection.prepareStatement(sql);

      int offset = 0;

      if (isUpdate) {
        pStatement.setString(5, empObj.getEmployeeID());
        offset = -1;
      } else {
        pStatement.setString(1, empObj.getEmployeeID());
      }

      pStatement.setString(2 + offset, empObj.getLastName());
      pStatement.setString(3 + offset, empObj.getFirstName());
      pStatement.setString(4 + offset, empObj.getDepartment());
      pStatement.setString(5 + offset, empObj.getPosition());

      pStatement.addBatch();
      pStatement.executeBatch();
      employeeMap.put(empObj.getEmployeeID(), empObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }
}
