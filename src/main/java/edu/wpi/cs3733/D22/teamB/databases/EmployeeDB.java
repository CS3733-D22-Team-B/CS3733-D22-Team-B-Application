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
      ResultSet rs = statement.executeQuery("SELECT * FROM Employees");

      while (rs.next()) {
        String employeeID = rs.getString("employeeID");
        String lastName = rs.getString("lastName");
        String firstName = rs.getString("firstName");
        String department = rs.getString("department");
        String position = rs.getString("position");

        Employee empOb = new Employee(employeeID, lastName, firstName, department, position);
        employeeMap.put(employeeID, empOb);
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
    try {
      Connection connection = DriverManager.getConnection(url);

      // If the location does not exist in the database, return -1
      if (employeeMap.containsKey(empObj.getEmployeeID()) == false) {
        return -1;
      }

      String sql =
          "UPDATE Employees SET lastName = ?, firstName = ?, department = ?, position = ? WHERE employeeID = ?";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, empObj.getLastName());
      pStatement.setString(2, empObj.getFirstName());
      pStatement.setString(3, empObj.getDepartment());
      pStatement.setString(4, empObj.getPosition());
      pStatement.setString(5, empObj.getEmployeeID());

      pStatement.addBatch();
      pStatement.executeBatch();

      employeeMap.put(empObj.getEmployeeID(), empObj);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return -1;
    }
    return 0;
  }

  public int add(Employee empObj) {
    // nodeID has to be unique
    if (employeeMap.containsKey(empObj.getEmployeeID())) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);

      String sql = "INSERT INTO Employees VALUES(?,?,?,?,?)";
      PreparedStatement pStatement = connection.prepareStatement(sql);
      pStatement.setString(1, empObj.getEmployeeID());
      pStatement.setString(2, empObj.getLastName());
      pStatement.setString(3, empObj.getFirstName());
      pStatement.setString(4, empObj.getDepartment());
      pStatement.setString(5, empObj.getPosition());

      pStatement.addBatch();
      pStatement.executeBatch();

      employeeMap.put(empObj.getEmployeeID(), empObj);

    } catch (SQLException e) {
      System.out.println("Connection failed.");
      return -1;
    }
    return 0;
  }

  public int delete(Employee empObj) {
    // nodeID has to exist
    if (employeeMap.containsKey(empObj.getEmployeeID()) == false) {
      return -1;
    }

    try {
      Connection connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      String sql = "DELETE FROM Employees WHERE employeeID = '" + empObj.getEmployeeID() + "'";
      statement.executeUpdate(sql);

      employeeMap.remove(empObj.getEmployeeID());

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
      statement.execute("DROP TABLE Employees");
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }
}
