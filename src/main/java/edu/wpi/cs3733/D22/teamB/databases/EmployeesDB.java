package edu.wpi.cs3733.D22.teamB.databases;

import edu.wpi.cs3733.D22.teamB.App;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class EmployeesDB extends DatabaseSuperclass implements IDatabases<Employee> {

  private static EmployeesDB employeesDBManager;
  private HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();

  private EmployeesDB() {
    super("Employees", "employeeID", Filepath.getInstance().getEmployeesCSVFilePath());
    initDB();
  }

  public static EmployeesDB getInstance() {
    if (employeesDBManager == null) {
      employeesDBManager = new EmployeesDB();
    }
    return employeesDBManager;
  }

  public void initDB() {
    employeeMap.clear(); // Remove residual objects in hashmap
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
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getBoolean(8),
                rs.getString(9));
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

  public LinkedList<Employee> listByAttribute(String attribute, String value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Employee> list = new LinkedList<Employee>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(employeeMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Employee> listByAttribute(String attribute, int value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Employee> list = new LinkedList<Employee>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(employeeMap.get(pkList.get(i)));
    }
    return list;
  }

  public LinkedList<Employee> listByAttribute(String attribute, boolean value) {
    LinkedList<String> pkList = searchWhere(attribute, value);
    LinkedList<Employee> list = new LinkedList<Employee>();
    for (int i = 0; i < pkList.size(); i++) {
      list.add(employeeMap.get(pkList.get(i)));
    }
    return list;
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

  public Employee getEmployee(String employeeId) {
    return employeeMap.get(employeeId);
  }

  public int update(Employee empObj) {
    if (!employeeMap.containsKey(empObj.getEmployeeID())) {
      return -1;
    }
    return transform(
        empObj,
        "UPDATE Employees SET lastName = ?, firstName = ?, department = ?, position = ?, username = ?, password = ?, lightOn = ?, color = ? WHERE employeeID = ?",
        true);
  }

  public int add(Employee empObj) {
    if (employeeMap.containsKey(empObj.getEmployeeID())) {
      return -1;
    }
    DatabaseController.getInstance()
        .add(
            new Activity(
                new java.util.Date(),
                App.currentUser.getEmployeeID(),
                empObj.getEmployeeID(),
                null,
                "Employee",
                "added to system"));
    return transform(empObj, "INSERT INTO Employees VALUES(?,?,?,?,?,?,?,?,?)", false);
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
        pStatement.setString(9, empObj.getEmployeeID());
        offset = -1;
      } else {
        pStatement.setString(1, empObj.getEmployeeID());
      }

      pStatement.setString(2 + offset, empObj.getLastName());
      pStatement.setString(3 + offset, empObj.getFirstName());
      pStatement.setString(4 + offset, empObj.getDepartment());
      pStatement.setString(5 + offset, empObj.getPosition());
      pStatement.setString(6 + offset, empObj.getUsername());
      pStatement.setString(7 + offset, empObj.getPassword());
      pStatement.setBoolean(8 + offset, empObj.getLightOn());
      pStatement.setString(9 + offset, empObj.getColor());

      pStatement.addBatch();
      pStatement.executeBatch();
      employeeMap.put(empObj.getEmployeeID(), empObj);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public String getEmployeeID(String employeeName) {
    Stream<String> keys =
        employeeMap.entrySet().stream()
            .filter(entry -> employeeName.equals(entry.getValue().getOverview()))
            .map(Map.Entry::getKey);
    return keys.findFirst().orElse(null);
  }
}
