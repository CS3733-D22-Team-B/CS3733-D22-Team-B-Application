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
  @FXML private JFXButton upButton;
  @FXML private JFXButton downButton;
  @FXML private ImageView mapImage;
  @FXML private Label floorDisplay;

  private int floorLevel = 2;

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
        addIcon(eq.getLocation().getXCoord(), eq.getLocation().getYCoord(), eq.getType());
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

  public void addIcon(int x, int y, String nodeType) {
    // set view coordinates
    int[] viewCoords = mapCoordsToViewCoords(x, y);

    // create a svg path
    SVGPath icon = new SVGPath();
    // set the color
    switch (nodeType) {
      case "BED":
        icon.setFill(Color.rgb(60, 173, 241));
        icon.setContent(
            "M8.921 4.285H2.787v-0.19c0 -0.461 -0.375 -0.836 -0.836 -0.836h-1.114c-0.045 0 -0.089 0.005 -0.132 0.011V2.699c0 -0.194 -0.158 -0.352 -0.352 -0.352C0.158 2.347 0 2.505 0 2.699v1.396v0.542v0.96v0.493c0 0.194 0.158 0.352 0.352 0.352H0.654v0.859c0 0.194 0.158 0.352 0.352 0.352c0.194 0 0.352 -0.158 0.352 -0.352v-0.859h1.077H8.258v0.859c0 0.194 0.158 0.352 0.352 0.352c0.194 0 0.352 -0.158 0.352 -0.352v-0.86c0.576 -0.021 1.038 -0.496 1.038 -1.078C10 4.769 9.516 4.285 8.921 4.285zM2.083 5.597c0 0.073 -0.059 0.132 -0.132 0.132h-1.114c-0.073 0 -0.132 -0.059 -0.132 -0.132v-0.608h1.379L2.083 5.597L2.083 5.597zM2.083 4.285H0.704v-0.19c0 -0.073 0.059 -0.132 0.132 -0.132h1.114c0.073 0 0.132 0.059 0.132 0.132L2.083 4.285L2.083 4.285zM8.921 5.738H2.787v-0.749h6.135c0.206 0 0.374 0.168 0.374 0.374C9.296 5.57 9.128 5.738 8.921 5.738z");
        break;
      case "XR":
        icon.setFill(Color.rgb(250, 20, 20));
        icon.setContent(
            "M12.288 4.02C12.223 3.183 11.575 2.538 10.732 2.41C10.409 2.41 10.085 2.346 9.761 2.346L9.437 1.766C9.241 1.443 8.853 1.249 8.464 1.249H6.584C6.195 1.249 5.871 1.443 5.612 1.766L5.223 2.346C4.899 2.346 4.575 2.41 4.25 2.41C3.408 2.474 2.76 3.183 2.694 4.02C2.565 5.245 2.501 7.048 2.501 8.337C2.501 9.627 2.629 11.045 2.694 12.011C2.76 12.848 3.408 13.492 4.25 13.557C5.157 13.621 6.519 13.75 7.491 13.75C8.464 13.75 9.825 13.621 10.732 13.557C11.575 13.492 12.223 12.848 12.288 12.011C12.352 11.045 12.483 9.563 12.483 8.337C12.547 7.048 12.418 5.245 12.288 4.02ZM5.935 2.99C6 2.99 6 2.99 5.935 2.99L6.39 2.217C6.39 2.152 6.454 2.152 6.519 2.152H8.399C8.464 2.152 8.464 2.152 8.529 2.217L8.982 2.99V3.054C9.046 3.119 8.982 3.248 8.982 3.312C8.982 3.377 8.918 3.377 8.853 3.377H6.13C6.066 3.377 6.066 3.377 6 3.312C5.935 3.183 5.935 3.119 5.935 2.99ZM8.788 8.659H6.195C5.935 8.659 5.676 8.466 5.676 8.145C5.676 7.822 5.871 7.629 6.195 7.629H8.788C9.046 7.629 9.306 7.822 9.306 8.145C9.306 8.466 9.046 8.659 8.788 8.659Z");
        break;
      case "RECL":
        icon.setFill(Color.rgb(247, 148, 29));
        icon.setContent(
            "M13.382 6.427H4.18v-0.284c0 -0.692 -0.563 -1.255 -1.255 -1.255h-1.671c-0.068 0 -0.134 0.007 -0.198 0.017V4.049c0 -0.292 -0.236 -0.528 -0.528 -0.528C0.236 3.521 0 3.757 0 4.049v2.093v0.813v1.44v0.74c0 0.292 0.236 0.528 0.528 0.528H0.981v1.288c0 0.292 0.236 0.528 0.528 0.528c0.292 0 0.528 -0.236 0.528 -0.528v-1.288h1.615H12.387v1.288c0 0.292 0.236 0.528 0.528 0.528c0.292 0 0.528 -0.236 0.528 -0.528v-1.29c0.864 -0.032 1.557 -0.745 1.557 -1.616C15 7.153 14.274 6.427 13.382 6.427zM3.124 8.395c0 0.109 -0.089 0.198 -0.198 0.198h-1.671c-0.109 0 -0.198 -0.089 -0.198 -0.198v-0.912h2.068L3.124 8.395L3.124 8.395zM3.124 6.427H1.056v-0.284c0 -0.109 0.089 -0.198 0.198 -0.198h1.671c0.109 0 0.198 0.089 0.198 0.198L3.124 6.427L3.124 6.427zM13.382 8.607H4.18v-1.123h9.202c0.31 0 0.562 0.252 0.562 0.562C13.944 8.355 13.692 8.607 13.382 8.607z");
        break;
      case "PUMP":
        icon.setFill(Color.rgb(59, 180, 74));
        icon.setContent(
            "M11.25 0H3.75c-0.58 0 -1.023 0.443 -1.023 1.023v8.523c0 0.955 0.75 1.705 1.705 1.705h4.432v0.682c0 0.205 -0.136 0.341 -0.341 0.341h-2.045c-0.205 0 -0.341 -0.136 -0.341 -0.341c0 -0.205 -0.136 -0.341 -0.341 -0.341s-0.341 0.136 -0.341 0.341c0 0.58 0.443 1.023 1.023 1.023h0.682v0.682c0 0.75 0.614 1.364 1.364 1.364c0.205 0 0.341 -0.136 0.341 -0.341s-0.136 -0.341 -0.341 -0.341c-0.375 0 -0.682 -0.307 -0.682 -0.682v-0.682h0.682c0.58 0 1.023 -0.443 1.023 -1.023v-1.023c0 -0.205 -0.136 -0.341 -0.341 -0.341H4.432c-0.58 0 -1.023 -0.443 -1.023 -1.023V1.023c0 -0.205 0.136 -0.341 0.341 -0.341h7.5c0.205 0 0.341 0.136 0.341 0.341v1.364h-0.682c-0.205 0 -0.341 0.136 -0.341 0.341c0 0.205 0.136 0.341 0.341 0.341h0.682v1.364h-1.023c-0.205 0 -0.341 0.136 -0.341 0.341c0 0.205 0.136 0.341 0.341 0.341h1.023v1.364h-0.682c-0.205 0 -0.341 0.136 -0.341 0.341s0.136 0.341 0.341 0.341h0.682v1.364h-1.023c-0.205 0 -0.341 0.136 -0.341 0.341s0.136 0.341 0.341 0.341h1.023v0.341c0 0.58 -0.443 1.023 -1.023 1.023c-0.205 0 -0.341 0.136 -0.341 0.341S10.364 11.25 10.568 11.25c0.955 0 1.705 -0.75 1.705 -1.705V1.023C12.273 0.443 11.83 0 11.25 0z");
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
}
