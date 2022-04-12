package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipmentDB;
import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  @FXML JFXComboBox<String> TypeDropdown;
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
    //        setEquipIcons();
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
}
