package edu.wpi.cs3733.D22.teamB.databases;

public class TesterMain {

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

    DatabaseInitializer dB = new DatabaseInitializer();
    /*
    LocationsDB locDB = LocationsDB.getInstance();
    locDB.listDB();
    Location addMe = new Location("sdf", 4, 5, "dfdsf", "dsf", "ewr", "rrt", "rrg");
    locDB.add(addMe);
    Location deleteMe = new Location("bHALL007L2", 435, 425, "dfdsf", "dsf", "ewr", "rrt", "rrg");
    locDB.delete(deleteMe);
    Location updateMe =
        new Location("bHALL006L2", 10, 123, "sdfd", "fsdf", "rtret", "wettw", "top");
    locDB.update(updateMe);
    locDB.listDB();
     */

    /*
    MedicalEquipmentDB medDB = MedicalEquipmentDB.getInstance();
    medDB.listDB();
    MedicalEquipment addMe = new MedicalEquipment("sdf", "bPATI00303", "sdgf", true, true);
    medDB.add(addMe);
    MedicalEquipment deleteMe = new MedicalEquipment("BED8", "bPATI00303", "sdgf", true, true);
    medDB.delete(deleteMe);
    MedicalEquipment updateMe = new MedicalEquipment("PUMP6", "bELEV00ML2", "sdgf", false, false);
    medDB.update(updateMe);
    medDB.listDB();
     */


  }
}
