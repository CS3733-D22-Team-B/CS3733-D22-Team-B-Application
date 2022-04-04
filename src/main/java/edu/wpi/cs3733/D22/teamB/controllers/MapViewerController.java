package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapViewerController extends MenuBarController {
  @FXML private AnchorPane anchorPane;
  @FXML private JFXButton upButton;
  @FXML private JFXButton downButton;
  @FXML private ImageView mapImage;
  @FXML private Label floorDisplay;

  private int floorLevel = 2;

  private LinkedList<Circle> circles;
  private LinkedList<Location> floorLocations;
  protected LocationsDB dao;

  public void initialize() {
    circles = new LinkedList<Circle>();
    dao = LocationsDB.getInstance();

    setFloorLocations();
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
  }

  private void setFloorLocations() {
    LinkedList<Location> floorLocations = dao.getLocationsByFloor(floorLevel);

    for (Circle circle : circles) {
      removeCircle(circle);
    }
    circles.clear();

    for (Location location : floorLocations) {
      addCircle(location.getXCoord(), location.getYCoord(), location.getNodeType());
    }
  }

  public void addCircle(int x, int y, String nodeType) {
    // set view coordinates
    int[] viewCoords = mapCoordsToViewCoords(x, y);

    // create a circle
    Circle circle = new Circle(viewCoords[0], viewCoords[1], 2.5);

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

  public void removeCircle(Circle circle) {
    anchorPane.getChildren().remove(circle);
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
}
