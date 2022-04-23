package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import edu.wpi.cs3733.D22.teamB.path_planning.EdgeGetter;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class aStarVisualization extends MenuBarController {
  @FXML Pane mapPane;
  @FXML ImageView mapImage;

  public void drawPath(ArrayList<Location> path) {
    for (int i = 0; i < path.size() - 1; i++) {
      drawLine(path.get(i), path.get(i + 1));
      // System.out.println(path.get(i).getNodeID());
    }
  }

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

    int xStartMap = start.getXCoord();
    int yStartMap = start.getYCoord();
    int xEndMap = end.getXCoord();
    int yEndMap = end.getYCoord();

    int[] startViewCoords = this.mapCoordsToViewCoords(xStartMap, yStartMap);
    int[] endViewCoords = this.mapCoordsToViewCoords(xEndMap, yEndMap);

    line.setStartX(startViewCoords[0]);
    line.setStartY(startViewCoords[1]);
    line.setEndX(endViewCoords[0]);
    line.setEndY(endViewCoords[1]);
    line.setStrokeWidth(4);
    line.setStroke(Color.rgb(250, 214, 27));

    mapPane.getChildren().add(line);
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

//  public void drawLine(Location start, Location end) {
//    int xStart = start.getXCoord();
//    int yStart = start.getYCoord();
//
//    double lineLength = this.getDistance(start, end);
//    double lineThickness = 3;
//
//    Rectangle rectangle = new Rectangle(xStart, yStart, lineLength, lineThickness);
//    rectangle.rotateProperty();
//
//    mapPane.getChildren().add(rectangle);
//  }
//
//  private double getDistance(Location start, Location end) {
//    int xStart = start.getXCoord();
//    int yStart = start.getYCoord();
//    int xEnd = end.getXCoord();
//    int yEnd = end.getYCoord();
//
//    double distance = Math.sqrt(Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2));
//
//    return distance;
//  }
//
//  private double getAngle(Location start, Location end){
//    int xStart = start.getXCoord();
//    int yStart = start.getYCoord();
//    int xEnd = end.getXCoord();
//    int yEnd = end.getYCoord();
//
//    double angle = Math.atan2(yEnd - yStart, xEnd - xStart);
//
//    return angle;
//  }

  //    private void drawEquipmentCard(int x, int y, MedicalEquipment equipment) {
  //        Rectangle rectangle = new Rectangle(x, y, 160, 210);
  //        rectangle.setFill(Color.WHITE);
  //        rectangle.setStroke(Color.BLACK);
  //        rectangle.setStrokeWidth(1);
  //        rectangle.setArcHeight(25);
  //        rectangle.setArcWidth(25);
  //        rectangle.setStrokeType(StrokeType.INSIDE);
  //        rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
  //        rectangle.setStrokeLineJoin(StrokeLineJoin.ROUND);
  //        rectangle.setStrokeMiterLimit(10);
  //
  //        Label equipmentName = new Label(equipment.getName());
  //        equipmentName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
  //        equipmentName.setTextAlignment(TextAlignment.CENTER);
  //        equipmentName.setAlignment(Pos.CENTER);
  //        equipmentName.setLayoutX(x);
  //        equipmentName.setLayoutY(y + 10);
  //        equipmentName.setPrefWidth(160);
  //        equipmentName.setPrefHeight(25);
  //
  //        Label equipmentID = new Label("(" + equipment.getEquipmentID() + ")");
  //        equipmentID.setFont(Font.font("Arial", FontWeight.BOLD, 14));
  //        equipmentID.setTextAlignment(TextAlignment.CENTER);
  //        equipmentID.setAlignment(Pos.CENTER);
  //        equipmentID.setLayoutX(x);
  //        equipmentID.setLayoutY(y + 35);
  //        equipmentID.setPrefWidth(160);
  //        equipmentID.setPrefHeight(25);
  //
  //        Text locationText = new Text(x + 50, y + 80, "Location:");
  //        locationText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
  //        locationText.setTextAlignment(TextAlignment.CENTER);
  //
  //        Text equipmentLocation = new Text(x + 10, y + 100,
  // equipment.getLocation().getLongName());
  //        equipmentLocation.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
  //        equipmentLocation.setTextAlignment(TextAlignment.CENTER);
  //        equipmentLocation.setWrappingWidth(140);
  //
  //        Text statusText = new Text(x + 60, y + 150, "Status:");
  //        statusText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
  //        statusText.setTextAlignment(TextAlignment.CENTER);
  //
  //        Text equipmentStatus = new Text(x + 10, y + 170, equipment.getStatus());
  //        equipmentStatus.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
  //        equipmentStatus.setTextAlignment(TextAlignment.CENTER);
  //        equipmentStatus.setWrappingWidth(140);
  //
  //        equipmentCardsPane.getChildren().add(rectangle);
  //        equipmentCardsPane.getChildren().add(equipmentName);
  //        equipmentCardsPane.getChildren().add(equipmentID);
  //        equipmentCardsPane.getChildren().add(locationText);
  //        equipmentCardsPane.getChildren().add(equipmentLocation);
  //        equipmentCardsPane.getChildren().add(statusText);
  //        equipmentCardsPane.getChildren().add(equipmentStatus);
  //    }
