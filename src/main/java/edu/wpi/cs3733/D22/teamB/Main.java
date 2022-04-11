package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import edu.wpi.cs3733.D22.teamB.requests.MealRequest;
import edu.wpi.cs3733.D22.teamB.requests.MedicineRequest;
import java.util.Date;

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
    // App.launch(App.class, args);
  }
}
