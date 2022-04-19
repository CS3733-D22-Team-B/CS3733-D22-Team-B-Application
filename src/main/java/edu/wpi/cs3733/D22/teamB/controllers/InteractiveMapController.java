package edu.wpi.cs3733.D22.teamB.controllers;

import static java.lang.Math.round;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
  @FXML private Pane requestInfo;
  @FXML private Label requestType;
  @FXML private Rectangle locationPane;
  @FXML private Rectangle equipmentPane;
  @FXML private Rectangle requestPane;
  @FXML private Button addButton;
  @FXML private Button deleteButton;
  @FXML private Button editButton;
  @FXML private Label errorLabel;
  @FXML private TextField addLocationName;
  @FXML private ComboBox<String> typeDropdown;
  @FXML private Button confirmButton;
  @FXML private Button cancelButton;
  @FXML private Button markerButton;

  protected LocationsDB dao;
  protected MedicalEquipmentDB edao;
  protected ServiceRequestsDB rdao;

  private LinkedList<SVGPath> roomIcons;
  private LinkedList<SVGPath> equipIcons;
  private LinkedList<SVGPath> serviceIcons;
  private LinkedList<Location> floorLocations;
  // private Popup roomPopup;

  private SVGPath marker = new SVGPath();

  private Boolean locInfoVisible = false;
  private Boolean lockHover = false;
  private Boolean equipInfoVisible = false;
  private Boolean requestInfoVisible = false;
  private Boolean addEnabled = false;
  private Boolean editEnabled = false;

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

    // Set Locations
    setRoomIcons();
    // Set Equipment
    setEquipIcons();
    // Set Requests
    setServiceIcons();
    resetDisplayPanes();
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
      if (r.getLocation() != null && !r.getStatus().equals("Completed")) {
        String floor = r.getLocation().getFloor();
        if (stringtoFloorLevel(floor) == floorLevel) addServiceIcon(r);
      }
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
              if (newVal && !lockHover) {
                locationPane.setFill(icon.getFill());
                if (!location.getNodeType().equals("EXIT")) {
                  roomName.setTextFill(Color.rgb(255, 255, 255));
                  nodeType.setTextFill(Color.rgb(255, 255, 255));
                } else {
                  roomName.setTextFill(Color.rgb(0, 0, 0));
                  nodeType.setTextFill(Color.rgb(0, 0, 0));
                }
                getLocInfo(location);
              } else {
                if (!locInfoVisible && !lockHover) {
                  locationInfo.setVisible(false);
                }
              }
            });
    icon.setOnMouseClicked(
        event -> {
          if (!equipInfoVisible && !requestInfoVisible) {
            locationPane.setFill(icon.getFill());
            if (!location.getNodeType().equals("EXIT")) {
              roomName.setTextFill(Color.rgb(255, 255, 255));
              nodeType.setTextFill(Color.rgb(255, 255, 255));
            } else {
              roomName.setTextFill(Color.rgb(0, 0, 0));
              nodeType.setTextFill(Color.rgb(0, 0, 0));
            }
            toggleRoomInfo(location);
          }
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
    if (eq.getAvailability().equals("Requested")) {
      icon.setFill(Color.rgb(250, 0, 200));
    } else {
      icon.setFill(Color.rgb(60, 173, 241));
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal && !lockHover) {
                getEquipInfo(eq);
                equipmentPane.setFill(icon.getFill());
              } else {
                if (!equipInfoVisible && !lockHover) {
                  equipInfo.setVisible(false);
                }
              }
            });
    icon.setOnMouseClicked(
        event -> {
          if (!locInfoVisible && !requestInfoVisible) toggleEquipInfo(eq);
        });

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
    switch (r.getStatus()) {
      case "Pending":
        icon.setFill(Color.rgb(250, 0, 0));
        break;
      case "In Progress":
        icon.setFill(Color.rgb(100, 0, 100));
        break;
      default:
        icon.setFill(Color.rgb(100, 100, 100));
        break;
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal && !lockHover) {
                getRequestInfo(r);
                requestPane.setFill(icon.getFill());
              } else {
                if (!requestInfoVisible && !lockHover) {
                  requestInfo.setVisible(false);
                }
              }
            });
    icon.setOnMouseClicked(
        event -> {
          if (!locInfoVisible && !equipInfoVisible) toggleRequestInfo(r);
        });
    icon.setLayoutX(viewCoords[0]);
    icon.setLayoutY(viewCoords[1]);
    mapPane.getChildren().add(icon);
    serviceIcons.add(icon);
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

  public void getRequestInfo(Request r) {
    int[] mapCoords =
        mapCoordsToViewCoords(r.getLocation().getXCoord(), r.getLocation().getYCoord());
    requestType.setText(r.getType());
    requestInfo.setLayoutX(mapCoords[0] - 50);
    requestInfo.setLayoutY(mapCoords[1] + 15);
    requestInfo.setVisible(true);
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
    floorString = "L2";
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/lowerLevel2.png"));
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloorL1() {
    floorLevel = 1;
    floorString = "L1";
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/lowerLevel1.png"));
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloor1() {
    floorLevel = 2;
    floorString = "1";
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/firstFloor.png"));
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloor2() {
    floorLevel = 3;
    floorString = "2";
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/secondFloor.png"));
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
    resetDisplayPanes();
    floorReset();
  }

  public void goToFloor3() {
    floorLevel = 4;
    floorString = "3";
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/thirdFloor.png"));
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
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
      deleteButton.setVisible(false);
      editButton.setVisible(false);
    } else {
      getLocInfo(location);
      lockHover = true;
      locInfoVisible = true;
      deleteButton.setVisible(true);
      editButton.setVisible(true);
    }
  }

  public void toggleRequestInfo(Request r) {
    if (requestInfoVisible) {
      requestInfo.setVisible(false);
      lockHover = false;
      requestInfoVisible = false;
    } else {
      getRequestInfo(r);
      lockHover = true;
      requestInfoVisible = true;
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
    requestInfoVisible = false;
    lockHover = false;
    locationInfo.setVisible(false);
    equipInfo.setVisible(false);
    requestInfo.setVisible(false);
  }

  public void deleteLoc() {
    String name = roomName.getText();
    Location target = null;
    Boolean hasEquip = false;
    LinkedList<Location> listChange = dao.listByAttribute("longName", name);
    for (Location loc : listChange) {
      if (loc.getLongName().equals(name)) target = loc;
    }
    LinkedList<MedicalEquipment> allEquip = edao.list();
    for (MedicalEquipment eq : allEquip) {
      if (eq.getLocation() == target) {
        hasEquip = true;
      }
    }
    if (hasEquip) {
      errorLabel.setText("Delete Failed! Check that no equipment is tied to the room!");
    } else {
      dao.delete(target);
      errorLabel.setText("");
      deleteButton.setVisible(false);
      floorReset();
      setRoomIcons();
      resetDisplayPanes();
    }
  }

  public void startAdd() {
    lockHover = true;
    addEnabled = true;
    markerButton.setVisible(true);
    typeDropdown.setVisible(true);
    addLocationName.setVisible(true);
    confirmButton.setVisible(true);
    cancelButton.setVisible(true);
  }

  public void endAdd() {
    lockHover = false;
    addEnabled = false;
    markerButton.setVisible(false);
    typeDropdown.setVisible(false);
    addLocationName.setVisible(false);
    confirmButton.setVisible(false);
    typeDropdown.setValue("");
    addLocationName.setText("");
    cancelButton.setVisible(false);
    clearMarker();
    floorReset();
    setRoomIcons();
    resetDisplayPanes();
  }

  public void addLocation() {
    if (!typeDropdown.getValue().equals("")
        && mapPane.getChildren().contains(marker)
        && !addLocationName.getText().equals("")) {
      int markerCoordX = (int) marker.getLayoutX();
      int markerCoordY = (int) marker.getLayoutY();
      int[] newCoords = imageCoordsToCSVCoords((int) markerCoordX, (int) markerCoordY);
      String nodeType = typeDropdown.getValue();
      String nodeID = dao.getNextID(floorString, nodeType);
      String name = addLocationName.getText();
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
              name,
              true);
      // Pass new location into database
      dao.add(newLoc);
      endAdd();
    }
  }

  public void confirmChanges() {
    if (editEnabled) editLocation();
    if (addEnabled) addLocation();
  }

  public void startEdit() {
    editEnabled = true;
    lockHover = true;
    markerButton.setVisible(true);
    typeDropdown.setVisible(true);
    addLocationName.setVisible(true);
    confirmButton.setVisible(true);
    deleteButton.setDisable(true);
    addButton.setDisable(true);
    cancelButton.setVisible(true);
  }

  public void endEdit() {
    editEnabled = false;
    lockHover = false;
    markerButton.setVisible(false);
    typeDropdown.setVisible(false);
    addLocationName.setVisible(false);
    confirmButton.setVisible(false);
    deleteButton.setDisable(false);
    addButton.setDisable(false);
    cancelButton.setVisible(false);
    deleteButton.setVisible(false);
    editButton.setVisible(false);
    removeIcon(marker);
    floorReset();
    setRoomIcons();
    resetDisplayPanes();
  }

  public void editLocation() {
    String oldName = roomName.getText();
    LinkedList<Location> findLoc = dao.listByAttribute("longName", oldName);
    Location toChange = findLoc.pop();
    String name = toChange.getLongName();
    String nodeType = toChange.getNodeType();
    if (!addLocationName.getText().equals("") && !addLocationName.getText().equals(name)) {
      toChange.setShortName(addLocationName.getText());
      toChange.setLongName(addLocationName.getText());
    }
    if (typeDropdown.getValue() != null && !typeDropdown.getValue().equals(nodeType)) {
      toChange.setNodeType(typeDropdown.getValue());
    }
    if (mapPane.getChildren().contains(marker)) {
      int markerX = (int) marker.getLayoutX();
      int markerY = (int) marker.getLayoutY();
      int[] newCoords = imageCoordsToCSVCoords(markerX, markerY);
      toChange.setXCoord(newCoords[0]);
      toChange.setYCoord(newCoords[1]);
    }
    dao.update(toChange);
    endEdit();
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

  public void cancel() {
    endEdit();
    endAdd();
  }
}
