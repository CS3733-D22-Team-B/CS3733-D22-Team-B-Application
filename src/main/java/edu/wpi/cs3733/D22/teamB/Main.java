package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.LinkedList;

public class Main {

  public static void main(String[] args) {
    System.out.println("-------Embedded Apache Derby Connection Testing-------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      return;
    }

    DatabaseController DC = DatabaseController.getInstance();
    App.launch(App.class, args);
    /*
    LinkedList<Employee> emp = EmployeesDB.getInstance().list();
    for (int i = 0; i < emp.size(); i++) {
      System.out.println(emp.get(i));
    }
    /*
    LinkedList<String> ids = EmployeesDB.getInstance().selectAll();
    for (int i = 0; i < ids.size(); i++) {
      System.out.println(ids.get(i));
    }

     */
    DC.switchConnection();
    System.out.println("Remote");
    LinkedList<Employee> emp1 = EmployeesDB.getInstance().list();
    for (int i = 0; i < emp1.size(); i++) {
      System.out.println(emp1.get(i));
    }
    /*
    LinkedList<String> ids1 = EmployeesDB.getInstance().selectAll();
    for (int i = 0; i < ids1.size(); i++) {
      System.out.println(ids1.get(i));
    }
     */
  }
}
