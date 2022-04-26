package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.CSVReader;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.Edge;
import edu.wpi.cs3733.D22.teamB.databases.EdgesDB;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class EdgeGetter {

  private static EdgeGetter edgeGetter;

  public static EdgeGetter getInstance() {
    if (edgeGetter == null) {
      edgeGetter = new EdgeGetter();
    }
    return edgeGetter;
  }

  public LinkedList<String> getEdges(String startID) {
    DatabaseController DC = DatabaseController.getInstance();
    EdgesDB edgesDB = EdgesDB.getInstance();

    LinkedList<Edge> edgeList1 = edgesDB.listByAttribute("nodeID1", startID);
    LinkedList<Edge> edgeList2 = edgesDB.listByAttribute("nodeID2", startID);
    LinkedList<String> idList = new LinkedList<String>();

    for (int i = 0; i < edgeList1.size(); i++) {
      idList.add(edgeList1.get(i).getNodeID2());
      // System.out.println(edgeList1.get(i).getNodeID2());
    }

    for (int i = 0; i < edgeList2.size(); i++) {
      idList.add(edgeList2.get(i).getNodeID1());
      // System.out.println(edgeList2.get(i).getNodeID1());
    }

    return idList;
  }

  public HashMap<String, LinkedList<String>> getEdges() {
    HashMap<String, LinkedList<String>> edgeMap = new HashMap<>();

    CSVReader reader = new CSVReader();
    try {
      BufferedReader buff = reader.read("CSVs/Edges.csv");
      buff.readLine();

      String lineText = null;
      while ((lineText = buff.readLine()) != null) {
        String[] data = lineText.split(",");

        if (edgeMap.containsKey(data[1])) {
          LinkedList<String> newList = edgeMap.get(data[1]);
          newList.add(data[2]);

          edgeMap.put(data[1], newList);
        } else {
          LinkedList<String> newList = new LinkedList<String>();
          newList.add(data[2]);

          edgeMap.put(data[1], newList);
        }

        if (edgeMap.containsKey(data[2])) {
          LinkedList<String> newList = edgeMap.get(data[2]);
          newList.add(data[1]);

          edgeMap.put(data[2], newList);
        } else {
          LinkedList<String> newList = new LinkedList<String>();
          newList.add(data[1]);

          edgeMap.put(data[2], newList);
        }
      }
    } catch (IOException e) {
      System.out.println("Didn't work :(");
    }

    return edgeMap;
  }
}
