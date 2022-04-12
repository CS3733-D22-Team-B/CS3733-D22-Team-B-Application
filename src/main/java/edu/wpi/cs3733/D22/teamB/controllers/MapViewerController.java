package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

public class MapViewerController extends MenuBarController {
  @FXML private AnchorPane anchorPane;
  @FXML protected JFXButton upButton;
  @FXML protected JFXButton downButton;
  @FXML private ImageView mapImage;
  @FXML protected Label floorDisplay;
  @FXML private ImageView legendImage;

  private int floorLevel = 2;
  private Boolean legend = false;

  private LinkedList<Circle> circles;
  private LinkedList<SVGPath> icons;
  private LinkedList<Location> floorLocations;
  protected LocationsDB dao;
  protected MedicalEquipmentDB edao;

  public void initialize() {
    circles = new LinkedList<Circle>();
    dao = LocationsDB.getInstance();
    edao = MedicalEquipmentDB.getInstance();
    icons = new LinkedList<SVGPath>();
    setFloorLocations();
    setIcons();
  }

  @FXML
  public void moveUp(ActionEvent event) {
    switch (floorLevel) {
      default:
      case 0:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
        floorDisplay.setText("L1");
        downButton.setDisable(false);
        break;
      case 1:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
        floorDisplay.setText("1");
        break;
      case 2:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
        floorDisplay.setText("2");
        break;
      case 3:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/thirdFloor.png"));
        floorDisplay.setText("3");
        upButton.setDisable(true);
        break;
    }
    floorLevel++;
    setFloorLocations();
    setIcons();
  }

  @FXML
  public void moveDown(ActionEvent event) {
    switch (floorLevel) {
      default:
      case 1:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel2.png"));
        floorDisplay.setText("L2");
        downButton.setDisable(true);
        break;
      case 2:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
        floorDisplay.setText("L1");
        break;
      case 3:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
        floorDisplay.setText("1");
        break;
      case 4:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
        floorDisplay.setText("2");
        upButton.setDisable(false);
        break;
    }
    floorLevel--;
    setFloorLocations();
    setIcons();
  }

  private void setFloorLocations() {
    String floorName = "";
    switch (floorLevel) {
      case 0:
        floorName = "L2";
        break;
      case 1:
        floorName = "L1";
        break;
      case 2:
        floorName = "1";
        break;
      case 3:
        floorName = "2";
        break;
      case 4:
        floorName = "3";
        break;
    }

    LinkedList<Location> floorLocations = dao.getLocationsByFloor(floorLevel);

    for (Circle circle : circles) {
      removeCircle(circle);
    }
    circles.clear();

    for (Location location : floorLocations) {
      addCircle(location.getXCoord(), location.getYCoord(), location.getNodeType());
    }
  }

  private void setIcons() {
    LinkedList<MedicalEquipment> list = edao.list();
    for (SVGPath icon : icons) {
      removeIcon(icon);
    }
    icons.clear();

    for (MedicalEquipment eq : list) {
      String floor = eq.getLocation().getFloor();
      if (stringtoFloorLevel(floor) == floorLevel) {
        addIcon(
            eq.getLocation().getXCoord(),
            eq.getLocation().getYCoord(),
            eq.getType(),
            eq.getAvailability());
      }
    }
  }

  private int stringtoFloorLevel(String floor) {
    switch (floor) {
      case "L2":
        return 0;
      case "L1":
        return 1;
      case "1":
        return 2;
      case "2":
        return 3;
      case "3":
        return 4;
      default:
        return -1;
    }
  }

  public void addCircle(int x, int y, String nodeType) {
    // set view coordinates
    int[] viewCoords = mapCoordsToViewCoords(x, y);

    // create a circle
    Circle circle = new Circle(viewCoords[0], viewCoords[1], 3.5);

    // set the color
    switch (nodeType) {
      case "BATH":
        circle.setFill(Color.rgb(233, 28, 35));
        break;
      case "DEPT":
        circle.setFill(Color.rgb(237, 89, 41));
        break;
      case "DIRT":
        circle.setFill(Color.rgb(247, 148, 29));
        break;
      case "ELEV":
        circle.setFill(Color.rgb(247, 176, 62));
        break;
      case "EXIT":
        circle.setFill(Color.rgb(251, 240, 2));
        break;
      case "HALL":
        circle.setFill(Color.rgb(140, 199, 59));
        break;
      case "INFO":
        circle.setFill(Color.rgb(59, 180, 74));
        break;
      case "LABS":
        circle.setFill(Color.rgb(40, 167, 158));
        break;
      case "PATI":
        circle.setFill(Color.rgb(60, 173, 241));
        break;
      case "REST":
        circle.setFill(Color.rgb(43, 115, 191));
        break;
      case "RETL":
        circle.setFill(Color.rgb(101, 43, 145));
        break;
      case "SERV":
        circle.setFill(Color.rgb(146, 37, 144));
        break;
      case "STAI":
        circle.setFill(Color.rgb(246, 153, 205));
        break;
      case "STOR":
        circle.setFill(Color.rgb(209, 192, 168));
        break;
    }

    // add the circle to the pane
    anchorPane.getChildren().add(circle);

    // add the circle to the list
    circles.add(circle);
  }

  public void addIcon(int x, int y, String nodeType, String availability) {
    // set view coordinates
    int[] viewCoords = mapCoordsToViewCoords(x, y);

    // create a svg path
    SVGPath icon = new SVGPath();
    // set the color
    switch (nodeType) {
      case "BED":
        icon.setContent(
            "M10.5 5.39V4c0 -0.825 -0.675 -1.5 -1.5 -1.5h-2c-0.385 0 -0.735 0.15 -1 0.39C5.735 2.65 5.385 2.5 5 2.5H3C2.175 2.5 1.5 3.175 1.5 4v1.39C1.195 5.665 1 6.06 1 6.5v3h1v-1h8v1h1v-3C11 6.06 10.805 5.665 10.5 5.39zM7 3.5h2c0.275 0 0.5 0.225 0.5 0.5v1h-3V4C6.5 3.725 6.725 3.5 7 3.5zM2.5 4c0 -0.275 0.225 -0.5 0.5 -0.5h2c0.275 0 0.5 0.225 0.5 0.5v1H2.5V4zM2 7.5v-1c0 -0.275 0.225 -0.5 0.5 -0.5h7c0.275 0 0.5 0.225 0.5 0.5v1H2z");
        break;
      case "XR":
        icon.setContent(
            "M9.5 1h-2.09C7.2 0.42 6.65 0 6 0S4.8 0.42 4.59 1H2.5c-0.55 0 -1 0.45 -1 1v8c0 0.55 0.45 1 1 1h7c0.55 0 1 -0.45 1 -1V2c0 -0.55 -0.45 -1 -1 -1zm-3.5 0c0.275 0 0.5 0.226 0.5 0.5s-0.226 0.5 -0.5 0.5 -0.5 -0.226 -0.5 -0.5 0.226 -0.5 0.5 -0.5zm3.5 9H2.5V2h1v1.5h5V2h1v8z");
        break;
      case "RECL":
        icon.setContent(
            "M7.5 2.5v3.5H4.5V2.5h3m0 -1H4.5c-0.55 0 -1 0.45 -1 1v4.5h5V2.5c0 -0.55 -0.45 -1 -1 -1zm3.5 3.5h-1.5v1.5h1.5v-1.5zM2.5 5H1v1.5h1.5v-1.5zm7.5 2.5H2v3h1v-2h6v2h1v-3z");
        break;
      case "PUMP":
        icon.setContent(
            "M6 1c-2.665 2.275 -4 4.24 -4 5.9c0 2.49 1.9 4.1 4 4.1s4 -1.61 4 -4.1C10 5.24 8.665 3.275 6 1zM6 10c-1.675 0 -3 -1.285 -3 -3.1c0 -1.17 0.975 -2.72 3 -4.57c2.025 1.85 3 3.395 3 4.57C9 8.715 7.675 10 6 10z");
        break;
    }
    switch (availability) {
      case "Unavailable":
        icon.setFill(Color.rgb(250, 0, 200));
        break;
      case "Requested":
        icon.setFill(Color.rgb(178, 180, 0));
        break;
      case "Available":
        icon.setFill(Color.rgb(60, 173, 241));
        break;
    }
    icon.setLayoutX(viewCoords[0]);
    icon.setLayoutY(viewCoords[1]);
    // add the circle to the pane
    anchorPane.getChildren().add(icon);

    // add the circle to the list
    icons.add(icon);
  }

  public void removeCircle(Circle circle) {
    anchorPane.getChildren().remove(circle);
  }

  public void removeIcon(SVGPath icon) {
    anchorPane.getChildren().remove(icon);
  }

  public int[] mapCoordsToViewCoords(int x, int y) {
    int mapWidth = 1060;
    int mapHeight = 930;
    int fitWidth = (int) mapImage.getFitWidth();
    int fitHeight = (int) mapImage.getFitHeight();
    int xOffset = (int) mapImage.getLayoutX() + (mapWidth / fitWidth);
    int yOffset = (int) mapImage.getLayoutY() + (mapHeight / fitHeight);

    int xView = (fitWidth * x) / mapWidth + xOffset;
    int yView = (fitHeight * y) / mapHeight + yOffset;

    return new int[] {xView, yView};
  }

  public int[] imageCoordsToCSVCoords(int x, int y) {
    int mapWidth = 1060;
    int mapHeight = 930;
    int fitWidth = (int) mapImage.getFitWidth();
    int fitHeight = (int) mapImage.getFitHeight();
    int xOffset = (int) mapImage.getLayoutX() + (mapWidth / fitWidth);
    int yOffset = (int) mapImage.getLayoutY() + (mapHeight / fitHeight);

    int xCSV = mapWidth * (x - xOffset) / fitWidth + 40;
    int yCSV = mapHeight * (y - yOffset) / fitHeight + 100;

    return new int[] {xCSV, yCSV};
  }

  @FXML
  public void disableFloorChange() {
    upButton.setDisable(true);
    downButton.setDisable(true);
  }

  @FXML
  public void enableFloorChange() {
    upButton.setDisable(false);
    downButton.setDisable(false);
  }

  public String getFloorLevel() {
    return floorDisplay.getText();
  }

  public void showHideLegend() {
    if (legend) {
      legendImage.setVisible(false);
      upButton.setVisible(true);
      legend = false;
    } else {
      legendImage.setVisible(true);
      upButton.setVisible(false);
      legend = true;
    }
  }
}
