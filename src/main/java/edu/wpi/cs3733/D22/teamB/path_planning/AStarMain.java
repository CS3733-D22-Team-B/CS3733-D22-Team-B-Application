package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.EdgesDB;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.HashMap;
import java.util.LinkedList;

public class AStarMain {

  public static void main(String[] args) {
    // CSVTester();

    DatabaseTester();
  }

  private static void DatabaseTester() {
    DatabaseController DC = DatabaseController.getInstance();
    LocationsDB locDB = LocationsDB.getInstance();

    EdgesDB edges = EdgesDB.getInstance();

    // Set the start and target locations
    Location start = locDB.getByID("bDEPT00101");
    Location target = locDB.getByID("bINFO00101");

    EdgeGetter edgeGetter = new EdgeGetter();
    LinkedList<String> firstNeighbors = edgeGetter.getEdges("bDEPT00101");

    System.out.println(firstNeighbors);
    // Call AStar
    //    AStar astar = new AStar(start, target);
    //    astar.getPath();
  }

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

  public void fullAstarTester(){

  }
}
