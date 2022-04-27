package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import eu.hansolo.fx.charts.heatmap.*;
import eu.hansolo.fx.charts.tools.ColorMapping;
import eu.hansolo.fx.charts.tools.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class HeatMapController {
  @FXML private StackPane pane;
  private HeatMap heatMap;

  private int floorNumber = 2;
  private String floor = "1";

  private List<Point> locationPoints;

  public void initialize() {
    heatMap =
        HeatMapBuilder.create()
            .prefSize(540, 960)
            .colorMapping(ColorMapping.BLUE_CYAN_GREEN_YELLOW_RED)
            .spotRadius(25)
            .opacityDistribution(OpacityDistribution.CUSTOM)
            .fadeColors(true)
            .build();

    pane.getChildren().add(heatMap);
  }

  @FXML
  public void increaseFloor(ActionEvent event) {
    if (floorNumber < 6) {
      floorNumber++;
    }

    switch (floorNumber) {
      case 1:
        floor = "L1";
        break;
      case 2:
        floor = "1";
        break;
      case 3:
        floor = "2";
        break;
      case 4:
        floor = "3";
        break;
      case 5:
        floor = "4";
        break;
      case 6:
        floor = "5";
        break;
    }

    drawHeatCircle();
  }

  @FXML
  public void decreaseFloor(ActionEvent event) {
    if (floorNumber > 0) {
      floorNumber--;
    }

    switch (floorNumber) {
      case 0:
        floor = "L2";
        break;
      case 1:
        floor = "L1";
        break;
      case 2:
        floor = "1";
        break;
      case 3:
        floor = "2";
        break;
      case 4:
        floor = "3";
        break;
      case 5:
        floor = "4";
        break;
    }

    drawHeatCircle();
  }

  private void drawHeatCircle() {
    //    ArrayList<Point> points = new ArrayList<Point>();
    //
    //    for (int i = 0; i < 1000; i++) {
    //      double rand = Math.random() * 2 * Math.PI;
    //
    //      double x1 = Math.cos(rand) * 100 + 300;
    //      double y1 = Math.sin(rand) * 100 + 150;
    //
    //      points.add(new Point(x1, y1));
    //    }

    locationPoints = new ArrayList<Point>();
    clear();

    ActivityDB db = ActivityDB.getInstance();

    for (Activity activity : db.list()) {
      if (activity.getType().equals("Location")) {
        Location location = LocationsDB.getInstance().getLocation(activity.getTypeID());

        if (location != null && location.getFloor().equals(floor)) {
          locationPoints.add(new Point(location.getXCoord() * 0.5, location.getYCoord() * 0.5));
        }
      } else if (activity.getType().equals("Request")) {
        Request request = ServiceRequestsDB.getInstance().getByID(activity.getTypeID());

        if (request != null) {
          Location location = LocationsDB.getInstance().getLocation(request.getLocationID());

          if (location != null && location.getFloor().equals(floor)) {
            locationPoints.add(new Point(location.getXCoord() * 0.5, location.getYCoord() * 0.5));
          }
        }
      }
    }

    heatMap.addSpots(locationPoints);
  }

  private void clear() {
    heatMap.clearHeatMap();
  }
}
