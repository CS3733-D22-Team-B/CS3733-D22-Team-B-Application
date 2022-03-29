package edu.wpi.cs3733.D22.teamB.databases;

public class Main {

  public static void main(String[] args) {
    System.out.println("-------Embedded Apache Derby Connection Testing-------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
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
    System.out.println("Apache Derby driver registered!");

    //////////////////////////////////////////////////////////// Code
    DatabaseInitializer di = new DatabaseInitializer();
    LocationsDAO locationsDB = new LocationsDAO();
    MedicalEquipmentDAO medEquipDB = new MedicalEquipmentDAO();

    di.listDB("Locations", 8);
    di.listDB("MedicalEquipment", 5);
    di.listDB("Employees", 5);
    di.listDB("Patients", 4);

    // tests
    /*
    Location loc = new Location("potato", 2, 2, "t", "t", "t", "t", "t");
    locationsDB.addLocation(loc);
    locationsDB.deleteLocation(loc);
    di.listDB("Locations", 8);
     */

    // locationsDB.locationsToCSV();
    // medEquipDB.medicalEquipmentToCSV();
    //////////////////////////////////////////////////////////// Code
  }
}

////////////////////////////////////////// Basement
/*
 public static void promptUser() {
   System.out.println("1 - Location Information");
   System.out.println("2 - Change Floor and Type");
   System.out.println("3 - Enter Location");
   System.out.println("4 - Delete Location");
   System.out.println("5 - Save Locations to CSV file");
   System.out.println("6 - Exit Program");
 }
*/
