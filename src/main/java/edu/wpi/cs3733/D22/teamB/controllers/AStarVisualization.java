package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import edu.wpi.cs3733.D22.teamB.path_planning.AStar;
import edu.wpi.cs3733.D22.teamB.path_planning.EdgeGetter;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class AStarVisualization extends MenuBarController {
  @FXML Pane mapPane;
  @FXML ImageView mapImage;

  ArrayList<Line> lineList = new ArrayList<Line>();
  ArrayList<Location> path = new ArrayList<Location>();

  public void calculatePath(Location start, Location end) {
    AStar pathplanner = new AStar(start, end);
    this.path = pathplanner.getPath();
  }

  public void drawPathFloor(String floor) {
    if (!path.isEmpty()) {
      for (int i = 0; i < path.size() - 1; i++) {
        if (path.get(i).getFloor().equals(floor)) {
          drawLine(path.get(i), path.get(i + 1));
          // System.out.println(path.get(i).getNodeID());
        }
      }
    }
  }

  public void clearLines() {
    if (!lineList.isEmpty()) {
      for (Line l : lineList) {
        mapPane.getChildren().remove(l);
      }
    }
  }

  // This is used for debugging to make sure the edges are all correct for each floor
  public void drawEdgesPerFloor(int floor) {
    LocationsDB dao = LocationsDB.getInstance();

    LinkedList<Location> floorList = dao.getLocationsByFloor(floor);
    EdgeGetter edgeGetter = new EdgeGetter();

    for (Location l : floorList) {
      LinkedList<String> edges = edgeGetter.getEdges(l.getNodeID());
      for (String s : edges) {
        drawLine(dao.getByID(s), l);
      }
    }
  }

  public void drawLine(Location start, Location end) {
    final Line line = new Line();

    int xStartMap = start.getXCoord() + 6;
    int yStartMap = start.getYCoord() + 6;
    int xEndMap = end.getXCoord() + 6;
    int yEndMap = end.getYCoord() + 6;

    int[] startViewCoords = this.mapCoordsToViewCoords(xStartMap, yStartMap);
    int[] endViewCoords = this.mapCoordsToViewCoords(xEndMap, yEndMap);

    line.setStartX(startViewCoords[0]);
    line.setStartY(startViewCoords[1]);
    line.setEndX(endViewCoords[0]);
    line.setEndY(endViewCoords[1]);
    line.setStrokeWidth(2);
    line.setStroke(Color.rgb(250, 250, 250));

    mapPane.getChildren().add(line);
    lineList.add(line);
  }

  public int[] mapCoordsToViewCoords(int x, int y) {
    double mapWidth = 1060;
    double mapHeight = 930;
    double ratio = (mapHeight / mapWidth) * 1.05;
    int fitWidth = (int) mapImage.getFitWidth();
    int fitHeight = (int) mapImage.getFitHeight();
    double xView = ((x / mapWidth) * fitWidth);
    double yView = ((y / mapHeight) * fitHeight);
    return new int[] {(int) xView, (int) yView};
  }

  public int[] imageCoordsToCSVCoords(int x, int y) {
    int mapWidth = 1060;
    int mapHeight = 930;
    double ratio = mapHeight / mapWidth;
    double fitWidth = mapImage.getFitWidth();
    double fitHeight = mapImage.getFitHeight();
    double xCSV = ((x / fitWidth) * mapWidth);
    double yCSV = ((y / fitHeight) * mapHeight);

    return new int[] {(int) xCSV, (int) yCSV};
  }
}
