package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class InteractiveMapController {
  @FXML private AnchorPane anchorPane;
  @FXML private ImageView mapImage;
  @FXML private Pane locationInfo;
  @FXML private Pane mapPane;
  @FXML private Label roomName;
  @FXML private Label nodeType;
  @FXML private Pane equipInfo;
  @FXML private Label equipType;

  protected LocationsDB dao;
  protected MedicalEquipmentDB edao;
  protected ServiceRequestsDB rdao;

  private LinkedList<SVGPath> roomIcons;
  private LinkedList<SVGPath> equipIcons;
  private LinkedList<SVGPath> serviceIcons;
  // private Popup roomPopup;

  private Boolean locInfoVisible = false;
  private Boolean lockHover = false;
  private Boolean equipInfoVisible = false;

  private int floorLevel = 2;

  public void initialize() {
    roomIcons = new LinkedList<SVGPath>();
    equipIcons = new LinkedList<SVGPath>();
    serviceIcons = new LinkedList<SVGPath>();
    dao = LocationsDB.getInstance();
    edao = MedicalEquipmentDB.getInstance();
    rdao = ServiceRequestsDB.getInstance();

    // Set Locations
    setRoomIcons();
    // Set Equipment
    setEquipIcons();
    resetDisplayPanes();
    // Set Requests

  }

  public void setRoomIcons() {
    LinkedList<Location> floorLocations = dao.getLocationsByFloor(floorLevel);
    for (SVGPath marker : roomIcons) {
      removeIcon(marker);
    }
    roomIcons.clear();

    for (Location loc : floorLocations) {
      // Create Icon that you can hover over
      addLocIcon(loc);
    }
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

  public void setServiceIcons() {
    LinkedList<Request> allRequests = rdao.list();
    for (SVGPath icon : serviceIcons) {
      removeIcon(icon);
    }
    serviceIcons.clear();
    for (Request r : allRequests) {
      String floor = r.getLocation().getFloor();
      if (stringtoFloorLevel(floor) == floorLevel) addServiceIcon(r);
    }
  }

  public void removeIcon(SVGPath icon) {
    mapPane.getChildren().remove(icon);
  }

  public void addLocIcon(Location location) {
    int x = location.getXCoord();
    int y = location.getYCoord();

    int[] viewCoords = mapCoordsToViewCoords(x, y);

    SVGPath icon = new SVGPath();

    switch (location.getNodeType()) {
              case "BATH":
                break;
              case "DEPT":
                break;
              case "DIRT":
                break;
              case "ELEV":
                break;
              case "EXIT":
                break;
              case "HALL":
                break;
              case "INFO":
                break;
              case "LABS":
                break;
              case "PATI":
                break;
              case "REST":
                break;
              case "RETL":
                break;
              case "SERV":
                break;
              case "STAI":
                break;
              case "STOR":
                break;
      default:
        icon.setContent("M 1 1 H 9 V 9 H 1 Z");
        icon.setFill(Color.rgb(12, 38, 210));

        break;
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal && !lockHover) {
                getLocInfo(location);
              } else {
                if (!locInfoVisible && !lockHover) {
                  locationInfo.setVisible(false);
                }
              }
            });
    icon.setOnMouseClicked(
        event -> {
          if (!equipInfoVisible) toggleRoomInfo(location);
        });
    icon.setLayoutX(viewCoords[0] - 5);
    icon.setLayoutY(viewCoords[1] - 5);
    mapPane.getChildren().add(icon);
    roomIcons.add(icon);
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
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal && !lockHover) {
                getEquipInfo(eq);
              } else {
                if (!equipInfoVisible && !lockHover) {
                  equipInfo.setVisible(false);
                }
              }
            });
    icon.setOnMouseClicked(
        event -> {
          if (!locInfoVisible) toggleEquipInfo(eq);
        });
    if (eq.getIsRequested()) {
      icon.setFill(Color.rgb(250, 0, 200));
    } else {
      icon.setFill(Color.rgb(60, 173, 241));
    }
    icon.setLayoutX(viewCoords[0]);
    icon.setLayoutY(viewCoords[1]);
    mapPane.getChildren().add(icon);
    equipIcons.add(icon);
  }

  public void addServiceIcon(Request r) {
    SVGPath icon = new SVGPath();
    icon.setContent(
        "M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z");
    int x = r.getLocation().getXCoord();
    int y = r.getLocation().getYCoord();
    int[] viewCoords = mapCoordsToViewCoords(x, y);
    icon.setFill(Color.rgb(250, 0, 0));
    icon.setLayoutX(viewCoords[0]);
    icon.setLayoutY(viewCoords[1]);
    anchorPane.getChildren().add(icon);
  }

  public void getLocInfo(Location location) {
    roomName.setText(location.getLongName());
    nodeType.setText(location.getNodeType());
    int[] mapCoords = mapCoordsToViewCoords(location.getXCoord(), location.getYCoord());
    locationInfo.setLayoutX(mapCoords[0] - 50);
    locationInfo.setLayoutY(mapCoords[1] + 15);
    locationInfo.setVisible(true);
  }

  public void getEquipInfo(MedicalEquipment eq) {
    int[] mapCoords =
        mapCoordsToViewCoords(eq.getLocation().getXCoord(), eq.getLocation().getYCoord());
    equipType.setText(eq.getType());
    equipInfo.setLayoutX(mapCoords[0] - 50);
    equipInfo.setLayoutY(mapCoords[1] + 15);
    equipInfo.setVisible(true);
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

  public void goToFloorL2() {
    floorLevel = 0;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel2.png"));
    setRoomIcons();
    setEquipIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloorL1() {
    floorLevel = 1;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
    setRoomIcons();
    setEquipIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloor1() {
    floorLevel = 2;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
    setRoomIcons();
    setEquipIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloor2() {
    floorLevel = 3;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
    setRoomIcons();
    setEquipIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloor3() {
    floorLevel = 4;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/thirdFloor.png"));
    setRoomIcons();
    setEquipIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void resetDisplayPanes() {
    mapPane.getChildren().remove(locationInfo);
    mapPane.getChildren().remove(equipInfo);
    mapPane.getChildren().add(locationInfo);
    mapPane.getChildren().add(equipInfo);
  }

  public void toggleRoomInfo(Location location) {
    if (locInfoVisible) {
      locationInfo.setVisible(false);
      lockHover = false;
      locInfoVisible = false;
    } else {
      getLocInfo(location);
      lockHover = true;
      locInfoVisible = true;
    }
  }

  public void toggleEquipInfo(MedicalEquipment eq) {
    if (equipInfoVisible) {
      equipInfo.setVisible(false);
      lockHover = false;
      equipInfoVisible = false;
    } else {
      getEquipInfo(eq);
      lockHover = true;
      equipInfoVisible = true;
    }
  }

  public void floorReset() {
    locInfoVisible = false;
    equipInfoVisible = false;
    lockHover = false;
    locationInfo.setVisible(false);
    equipInfo.setVisible(false);
  }
}
