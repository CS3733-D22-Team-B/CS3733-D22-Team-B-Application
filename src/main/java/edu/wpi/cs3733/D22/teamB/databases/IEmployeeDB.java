package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface IEmployeeDB {
  public LinkedList<Employee> listEmployees();

  public int updateEmployee(Employee empObj);

  public int addEmployee(Employee empObj);

  public int deleteEmployee(Employee empObj);

  public void quit();
}
