package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.HashMap;
import java.util.LinkedList;

public class TesterMain {

  public static void main(String[] args) {

    EdgeGetter edgy = new EdgeGetter();
    HashMap<String, LinkedList<String>> edgeMap = edgy.getEdges();

    // edgeMap.get("bDEPT00101");
    System.out.println(edgeMap.get("bDEPT00101"));

    DatabaseController DC = DatabaseController.getInstance();

    LocationsDB locDB = LocationsDB.getInstance();
    Location start = locDB.getByID("bDEPT00101");
    Location target = locDB.getByID("bINFO00101");
    AStar astar = new AStar(start, target);

    astar.getPath();
  }
}
