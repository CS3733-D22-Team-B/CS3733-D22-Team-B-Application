package edu.wpi.cs3733.D22.teamB.path_planning;

import edu.wpi.cs3733.D22.teamB.databases.CSVReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class EdgeGetter {

  public HashMap<String, LinkedList<String>> getEdges() {
    HashMap<String, LinkedList<String>> edgeMap = new HashMap<String, LinkedList<String>>();

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

          System.out.println(newList);
        } else {
          LinkedList<String> newList = new LinkedList<String>();
          newList.add(data[2]);

          edgeMap.put(data[1], newList);

          System.out.println(newList);
        }
      }
    } catch (IOException e) {
      System.out.println("Didn't work :(");
    }

    return edgeMap;
  }
}
