package edu.wpi.cs3733.D22.teamB.path_planning;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

  public int compare(Node node1, Node node2) {
    if (node1.getPriority() > node2.getPriority()) {
      return 0;
    } else {
      return 1;
    }
  }
}
