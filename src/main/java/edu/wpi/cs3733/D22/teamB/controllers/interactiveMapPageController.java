package edu.wpi.cs3733.D22.teamB.controllers;

import static java.lang.Math.round;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class interactiveMapPageController extends MenuBarController {
  @FXML JFXButton menuButton;
  @FXML JFXButton addButton;
  @FXML JFXButton editButton;
  @FXML JFXButton deleteButton;
  @FXML ImageView f5Button;
  @FXML ImageView f4Button;
  @FXML ImageView f3Button;
  @FXML ImageView f2Button;
  @FXML ImageView f1Button;
  @FXML ImageView l1Button;
  @FXML ImageView l2Button;
  @FXML Pane mapPane;
  @FXML ImageView mapImage;
  @FXML JFXButton backButton;
  @FXML ImageView floorBackground;
  @FXML ImageView topLeftBox;
  @FXML ImageView secondTopLeftBox;
  @FXML JFXComboBox<String> typeDropdown;
  @FXML JFXComboBox<String> locationDropdown;
  @FXML JFXTextArea locationName;
  @FXML JFXButton confirmButton;
  @FXML ImageView confirmImage;

  protected LocationsDB dao;
  protected MedicalEquipmentDB edao;
  protected ServiceRequestsDB rdao;

  private LinkedList<SVGPath> roomIcons;
  private LinkedList<SVGPath> equipIcons;
  private LinkedList<SVGPath> serviceIcons;
  private LinkedList<Location> floorLocations;

  private SVGPath marker = new SVGPath();

  private Boolean lockHover = false;
  private Boolean addEnabled = false;
  private Boolean editEnabled = false;
  private Boolean deleteEnabled = false;

  private int floorLevel = 2;
  private int[] coordinates;
  private String floorString = "1";

  public void initialize() {
    roomIcons = new LinkedList<SVGPath>();
    equipIcons = new LinkedList<SVGPath>();
    serviceIcons = new LinkedList<SVGPath>();
    dao = LocationsDB.getInstance();
    edao = MedicalEquipmentDB.getInstance();
    rdao = ServiceRequestsDB.getInstance();

    //        // Set Locations
    setRoomIcons();
    //        // Set Equipment
    setEquipIcons();
    //        // Set Requests
    //        setServiceIcons();
    //        resetDisplayPanes();
  }

  public void removeIcon(SVGPath icon) {
    mapPane.getChildren().remove(icon);
  }

  public void setRoomIcons() {
    floorLocations = dao.getLocationsByFloor(floorLevel);
    for (SVGPath marker : roomIcons) {
      removeIcon(marker);
    }
    roomIcons.clear();

    for (Location loc : floorLocations) {
      // Create Icon that you can hover over
      addLocIcon(loc);
    }
  }

  public void addLocIcon(Location location) {
    int x = location.getXCoord();
    int y = location.getYCoord();

    int[] viewCoords = mapCoordsToViewCoords(x, y);

    SVGPath icon = new SVGPath();
    icon.setContent("M 1 1 H 9 V 9 H 1 Z");

    switch (location.getNodeType()) {
      case "BATH":
        icon.setFill(Color.rgb(233, 28, 35));
        break;
      case "DEPT":
        icon.setFill(Color.rgb(237, 89, 41));
        break;
      case "DIRT":
        icon.setFill(Color.rgb(247, 148, 29));
        break;
      case "ELEV":
        icon.setFill(Color.rgb(247, 176, 62));
        break;
      case "EXIT":
        icon.setFill(Color.rgb(251, 240, 2));
        break;
      case "HALL":
        icon.setFill(Color.rgb(140, 199, 59));
        break;
      case "INFO":
        icon.setFill(Color.rgb(59, 180, 74));
        break;
      case "LABS":
        icon.setFill(Color.rgb(40, 167, 158));
        break;
      case "PATI":
        icon.setFill(Color.rgb(60, 173, 241));
        break;
      case "REST":
        icon.setFill(Color.rgb(43, 115, 191));
        break;
      case "RETL":
        icon.setFill(Color.rgb(101, 43, 145));
        break;
      case "SERV":
        icon.setFill(Color.rgb(146, 37, 144));
        break;
      case "STAI":
        icon.setFill(Color.rgb(246, 153, 205));
        break;
      case "STOR":
        icon.setFill(Color.rgb(209, 192, 168));
        break;
      default:
        icon.setFill(Color.rgb(12, 38, 210));
        break;
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              //                            if (newVal && !lockHover) {
              //                                locationPane.setFill(icon.getFill());
              //                                if (!location.getNodeType().equals("EXIT")) {
              //                                    roomName.setTextFill(Color.rgb(255, 255, 255));
              //                                    nodeType.setTextFill(Color.rgb(255, 255, 255));
              //                                } else {
              //                                    roomName.setTextFill(Color.rgb(0, 0, 0));
              //                                    nodeType.setTextFill(Color.rgb(0, 0, 0));
              //                                }
              //                                getLocInfo(location);
              //                            } else {
              //                                if (!locInfoVisible && !lockHover) {
              //                                    locationInfo.setVisible(false);
              //                                }
              //                            }
            });
    icon.setOnMouseClicked(
        event -> {
          //                    if (!equipInfoVisible && !requestInfoVisible) {
          //                        locationPane.setFill(icon.getFill());
          //                        if (!location.getNodeType().equals("EXIT")) {
          //                            roomName.setTextFill(Color.rgb(255, 255, 255));
          //                            nodeType.setTextFill(Color.rgb(255, 255, 255));
          //                        } else {
          //                            roomName.setTextFill(Color.rgb(0, 0, 0));
          //                            nodeType.setTextFill(Color.rgb(0, 0, 0));
          //                        }
          //                        toggleRoomInfo(location);
          //                    }
        });
    icon.setLayoutX(viewCoords[0] - 5);
    icon.setLayoutY(viewCoords[1] - 5);
    mapPane.getChildren().add(icon);
    roomIcons.add(icon);
  }

  public void setEquipIcons() {
    LinkedList<MedicalEquipment> allEquipment = edao.list();
    for (SVGPath icon : equipIcons) {
      removeIcon(icon);
    }
    equipIcons.clear();

    for (MedicalEquipment eq : allEquipment) {
      String floor = eq.getLocation().getFloor();
      if (stringtoFloorLevel(floor) == floorLevel) {
        addEquipIcon(eq);
      }
    }
  }

  public void addEquipIcon(MedicalEquipment eq) {
    int x = eq.getLocation().getXCoord();
    int y = eq.getLocation().getYCoord();

    int[] viewCoords = mapCoordsToViewCoords(x, y);

    SVGPath icon = new SVGPath();
    switch (eq.getType()) {
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
    if (eq.getAvailability().equals("Requested")) {
      icon.setFill(Color.rgb(250, 0, 200));
    } else {
      icon.setFill(Color.rgb(60, 173, 241));
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              //                      if (newVal && !lockHover) {
              //                        getEquipInfo(eq);
              //                        equipmentPane.setFill(icon.getFill());
              //                      } else {
              //                        if (!equipInfoVisible && !lockHover) {
              //                          equipInfo.setVisible(false);
              //                        }
              //                      }
            });
    icon.setOnMouseClicked(
        event -> {
          // if (!locInfoVisible && !requestInfoVisible) toggleEquipInfo(eq);
        });

    icon.setLayoutX(viewCoords[0]);
    icon.setLayoutY(viewCoords[1]);
    mapPane.getChildren().add(icon);
    equipIcons.add(icon);
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

  public void resetFloorSelectors() {
    l2Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
    l1Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
    f1Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
    f2Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
    f3Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
    f4Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
    f5Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/NotSelectedFloor.png"));
  }

  public void goToFloorL2() {
    floorLevel = 0;
    floorString = "L2";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/lowerLevel2.png"));
    l2Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setRoomIcons();
    //    setEquipIcons();
    //    setServiceIcons();
  }

  public void goToFloorL1() {
    floorLevel = 1;
    floorString = "L1";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/lowerLevel1.png"));
    l1Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setRoomIcons();
    //    setEquipIcons();
    //    setServiceIcons();
  }

  public void goToFloor1() {
    floorLevel = 2;
    floorString = "1";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/firstFloor.png"));
    f1Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setRoomIcons();
    //    setEquipIcons();
    //    setServiceIcons();
  }

  public void goToFloor2() {
    floorLevel = 3;
    floorString = "2";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/secondFloor.png"));
    f2Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setRoomIcons();
    //    setEquipIcons();
    //    setServiceIcons();
  }

  public void goToFloor3() {
    floorLevel = 4;
    floorString = "3";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/thirdFloor.png"));
    f3Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setRoomIcons();
    //    setEquipIcons();
    //    setServiceIcons();
  }

  // TODO: Floors 4 and 5

  public void startAdd() {
    lockHover = true;
    addEnabled = true;
    addButton.setVisible(false);
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    backButton.setVisible(true);
    floorBackground.setVisible(true);
    topLeftBox.setVisible(true);
    secondTopLeftBox.setVisible(true);
    typeDropdown.setVisible(true);
    locationName.setVisible(true);
    confirmButton.setVisible(true);
  }

  public void addLocation() {
    if (!typeDropdown.getValue().equals("")
        && mapPane.getChildren().contains(marker)
        && !locationName.getText().equals("")) {
      int markerCoordX = (int) marker.getLayoutX();
      int markerCoordY = (int) marker.getLayoutY();
      int[] newCoords = imageCoordsToCSVCoords((int) markerCoordX, (int) markerCoordY);
      String nodeType = typeDropdown.getValue();
      String nodeID = dao.getNextID(floorString, nodeType);
      String name = locationName.getText();
      String building = "Tower";
      Location newLoc =
          new Location(
              nodeID,
              newCoords[0] - 20,
              newCoords[1] - 50,
              floorString,
              building,
              nodeType,
              name,
              name);
      // Pass new location into database
      dao.add(newLoc);
      endAdd();
    }
  }

  public void endAdd() {
    lockHover = false;
    addEnabled = false;
    addButton.setVisible(true);
    editButton.setVisible(true);
    deleteButton.setVisible(true);
    backButton.setVisible(false);
    floorBackground.setVisible(false);
    topLeftBox.setVisible(false);
    secondTopLeftBox.setVisible(false);
    typeDropdown.setVisible(false);
    locationName.setVisible(false);

    confirmButton.setVisible(false);
    typeDropdown.setValue("");
    locationName.setText("");

    clearMarker();
    setRoomIcons();
  }

  public void getCoordinates(MouseEvent event) {
    if (addEnabled || editEnabled) {
      if (mapPane.getChildren().contains(marker)) {
        removeIcon(marker);
      }
      coordinates = new int[2];
      coordinates[0] = (int) round(event.getX());
      coordinates[1] = (int) round(event.getY());
      marker.setContent(
          "M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z");
      marker.setFill(Color.rgb(0, 0, 0));
      mapPane.getChildren().add(marker);
      marker.setLayoutX(coordinates[0] - 10);
      marker.setLayoutY(coordinates[1] - 10);
    }
  }

  public void clearMarker() {
    if (mapPane.getChildren().contains(marker)) {
      removeIcon(marker);
    }
  }

  public void populateTypeDropdown() {
    typeDropdown.getItems().add("DEPT");
    typeDropdown.getItems().add("ELEV");
    typeDropdown.getItems().add("EXIT");
    typeDropdown.getItems().add("HALL");
    typeDropdown.getItems().add("LABS");
    typeDropdown.getItems().add("INFO");
    typeDropdown.getItems().add("REST");
    typeDropdown.getItems().add("RETL");
    typeDropdown.getItems().add("LABS");
    typeDropdown.getItems().add("SERV");
    typeDropdown.getItems().add("STAI");
    typeDropdown.getItems().add("STOR");
    typeDropdown.getItems().add("BATH");
    typeDropdown.getItems().add("DIRT");
    typeDropdown.getItems().add("LABS");
    typeDropdown.getItems().add("PATI");
  }
}
