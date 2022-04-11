package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.HashMap;
import java.util.LinkedList;

public class AStarMain {

  public static void main(String[] args) {
    CSVTester();
  }

  private static void DatabaseTester() {}

  private static void CSVTester() {
    // Get edges from the the EdgeGetter
    // TODO get implement database calls to get edges
    EdgeGetter edgy = new EdgeGetter();
    HashMap<String, LinkedList<String>> edgeMap = edgy.getEdges();

    // Create an instance of the database controller and Locations DAO
    DatabaseController DC = DatabaseController.getInstance();
    LocationsDB locDB = LocationsDB.getInstance();

    // Set the start and target locations
    Location start = locDB.getByID("bDEPT00101");
    Location target = locDB.getByID("bINFO00101");

    // Call AStar
    AStar astar = new AStar(start, target);
    astar.getPath();
  }
}
