package edu.wpi.cs3733.D22.teamB.controllers;

import static java.lang.Math.round;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.LinkedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class InteractiveMapPageController extends MenuBarController {
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

  @FXML Pane floorBackground;
  @FXML JFXComboBox<String> typeDropdown;
  @FXML JFXComboBox<String> locationDropdown;
  @FXML JFXTextArea locationName;
  @FXML JFXButton confirmButton;
  @FXML ImageView confirmImage;
  @FXML JFXButton resetButton;
  @FXML JFXButton markerButton;
  @FXML JFXButton undoMoveButton;
  @FXML Pane locInfoPane;
  @FXML Label nameLabel;
  @FXML Label nodeLabel;
  @FXML Pane equipInfoPane;
  @FXML Label equipTypeLabel;
  @FXML Label statusLabel;
  @FXML Pane reqInfoPane;
  @FXML Label reqTypeLabel;
  @FXML Label reqStatusLabel;
  @FXML JFXButton equipEditButton;
  @FXML JFXComboBox<String> availabilityDropdown;
  @FXML JFXComboBox<String> stateDropdown;
  @FXML JFXButton filterButton;
  @FXML Label floorLabel;

  @FXML Pane filterPane;
  @FXML JFXButton bath;
  @FXML JFXButton dept;
  @FXML JFXButton dirt;
  @FXML JFXButton elev;
  @FXML JFXButton exit;
  @FXML JFXButton hall;
  @FXML JFXButton info;
  @FXML JFXButton labs;
  @FXML JFXButton pati;
  @FXML JFXButton rest;
  @FXML JFXButton retl;
  @FXML JFXButton serv;
  @FXML JFXButton stai;
  @FXML JFXButton stor;

  @FXML JFXButton bed;
  @FXML JFXButton xr;
  @FXML JFXButton recl;
  @FXML JFXButton pump;

  @FXML JFXButton equip;
  @FXML JFXButton lab;
  @FXML JFXButton med;
  @FXML JFXButton meal;
  @FXML JFXButton interp;
  @FXML JFXButton ipt;
  @FXML JFXButton custom;
  @FXML JFXButton laund;
  @FXML JFXButton sec;
  @FXML JFXButton gift;
  @FXML JFXButton san;

  protected LocationsDB dao;
  protected MedicalEquipmentDB edao;
  protected ServiceRequestsDB rdao;

  private LinkedList<SVGPath> roomIcons;
  private LinkedList<SVGPath> equipIcons;
  private LinkedList<SVGPath> serviceIcons;
  private LinkedList<Location> floorLocations;
  private LinkedList<String> locFilterList = new LinkedList<String>();
  private LinkedList<String> equipFilterList = new LinkedList<String>();
  private LinkedList<String> requestFilterList = new LinkedList<String>();

  private SVGPath marker = new SVGPath();

  private String moveName = "";

  private Boolean lockHover = false;
  private Boolean addEnabled = false;
  private Boolean editEnabled = false;
  private Boolean deleteEnabled = false;
  private Boolean editEquipEnabled = false;
  private Boolean equipPaneVisible = false;
  private Boolean filterOpen = false;
  private Boolean firstMove = true;
  private Boolean equipMoved = false;

  private Boolean bathOn = true;
  private Boolean deptOn = true;
  private Boolean dirtOn = true;
  private Boolean elevOn = true;
  private Boolean exitOn = true;
  private Boolean hallOn = true;
  private Boolean infoOn = true;
  private Boolean labsOn = true;
  private Boolean patiOn = true;
  private Boolean restOn = true;
  private Boolean retlOn = true;
  private Boolean servOn = true;
  private Boolean staiOn = true;
  private Boolean storOn = true;

  private Boolean bedOn = true;
  private Boolean xrOn = true;
  private Boolean reclOn = true;
  private Boolean pumpOn = true;

  private Boolean eqReqOn = true;
  private Boolean labReqOn = true;
  private Boolean medOn = true;
  private Boolean mealOn = true;
  private Boolean interpOn = true;
  private Boolean iptOn = true;
  private Boolean customOn = true;
  private Boolean laundOn = true;
  private Boolean secOn = true;
  private Boolean giftOn = true;
  private Boolean sanOn = true;

  private MedicalEquipment toEdit;

  private int floorLevel = 2;
  private int[] coordinates;
  private int[] startingCoordinates = new int[2];
  private String floorString = "1";

  private double startDragX, startDragY;
  private double scaleX = 1.0, scaleY = 1.0;
  private double orgSceneX, orgSceneY;

  public void initialize() {
    roomIcons = new LinkedList<SVGPath>();
    equipIcons = new LinkedList<SVGPath>();
    serviceIcons = new LinkedList<SVGPath>();
    dao = LocationsDB.getInstance();
    edao = MedicalEquipmentDB.getInstance();
    rdao = ServiceRequestsDB.getInstance();
    populateTypeDropdown();
    populateAvailabilityDropdown();
    populateStateDropdown();
    setAll();

    mapPane.setOnMousePressed(
        e -> {
          startDragX = e.getSceneX();
          startDragY = e.getSceneY();
        });

    mapPane.setOnMouseDragged(
        e -> {
          if (!addEnabled && !editEnabled && !editEquipEnabled) {
            mapPane.setTranslateX(e.getSceneX() - startDragX);
            mapPane.setTranslateY(e.getSceneY() - startDragY);
          }
        });

    mapPane.setOnScroll(
        e -> {
          zoom(e);
        });

    mapPane.setOnDragOver(
        new EventHandler<DragEvent>() {
          public void handle(DragEvent event) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
        });

    mapPane.setOnDragDropped(
        new EventHandler<DragEvent>() {
          public void handle(DragEvent event) {
            if (editEnabled) {
              Dragboard db = event.getDragboard();
              int newX = (int) event.getX();
              int newY = (int) event.getY();

              int[] converted = imageCoordsToCSVCoords(newX, newY);

              String locName = db.getString();
              LinkedList<Location> correctLoc = dao.listByAttribute("longName", locName);
              Location toEdit = correctLoc.pop();
              toEdit.setXCoord(converted[0]);
              toEdit.setYCoord(converted[1]);
              dao.update(toEdit);
              setRoomIcons();
            }
          }
        });
  }

  public void setAll() {
    populateLocationDropdown();
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
  }

  public void removeIcon(SVGPath icon) {
    mapPane.getChildren().remove(icon);
  }

  public void setRoomIcons() {
    floorLocations = dao.getLocationsByFloor(floorLevel);
    LinkedList<Location> filtered = filterLocs(floorLocations);

    for (SVGPath marker : roomIcons) {
      removeIcon(marker);
    }
    roomIcons.clear();

    for (Location loc : filtered) {
      // Create Icon that you can hover over
      addLocIcon(loc);
    }
    resetPane();
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
        icon.setFill(Color.rgb(255, 99, 167));
        break;
      case "STOR":
        icon.setFill(Color.rgb(72, 43, 0));
        break;
      default:
        icon.setFill(Color.rgb(12, 38, 210));
        break;
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal) {
                locInfoPane.setVisible(true);
                locInfoPane.setLayoutX(viewCoords[0] - 78);
                locInfoPane.setLayoutY(viewCoords[1] + 8);
                nameLabel.setText(location.getLongName());
                nodeLabel.setText(location.getNodeType());

              } else {
                locInfoPane.setVisible(false);
              }
            });
    icon.setOnMouseClicked(
        event -> {
          if (deleteEnabled || editEnabled) {
            locationDropdown.setValue(location.getLongName());
            moveName = location.getLongName();
            if (firstMove) {
              startingCoordinates[0] = location.getXCoord();
              startingCoordinates[1] = location.getYCoord();
            }
          }
        });
    icon.setOnDragDetected(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            if (editEnabled) {
              locationDropdown.setValue(location.getLongName());
              if (firstMove || !moveName.equals(location.getLongName())) {
                startingCoordinates[0] = location.getXCoord();
                startingCoordinates[1] = location.getYCoord();
                moveName = location.getLongName();
                firstMove = false;
              }
              Dragboard db = icon.startDragAndDrop(TransferMode.MOVE);
              ClipboardContent content = new ClipboardContent();
              content.putString(location.getLongName());
              db.setContent(content);
              event.consume();
            }
          }
        });
    //    icon.setLayoutX(viewCoords[0]);
    //    icon.setLayoutY(viewCoords[1]);
    mapPane.getChildren().add(icon);
    roomIcons.add(icon);
    icon.relocate(viewCoords[0], viewCoords[1]);
  }

  public void setEquipIcons() {
    LinkedList<MedicalEquipment> allEquipment = edao.list();
    LinkedList<MedicalEquipment> filtered = filterEquip(allEquipment);

    for (SVGPath icon : equipIcons) {
      removeIcon(icon);
    }
    equipIcons.clear();

    for (MedicalEquipment eq : filtered) {
      String floor = eq.getLocation().getFloor();
      if (stringtoFloorLevel(floor) == floorLevel) {
        addEquipIcon(eq);
      }
    }
    resetPane();
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
    } else if (eq.getAvailability().equals("Unavailable")) {
      icon.setFill(Color.rgb(100, 100, 100));
    } else {
      icon.setFill(Color.rgb(60, 173, 241));
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal && !editEquipEnabled) {
                equipInfoPane.setVisible(true);
                equipInfoPane.setLayoutX(viewCoords[0] - 78);
                equipInfoPane.setLayoutY(viewCoords[1] + 15);
                equipTypeLabel.setText(eq.getType());
                statusLabel.setText(eq.getAvailability());
              } else {
                if (equipPaneVisible) {

                } else equipInfoPane.setVisible(false);
              }
            });
    icon.setOnMouseClicked(
        event -> {
          toggleEquipPane(eq);
          toEdit = eq;
          orgSceneX = event.getSceneX();
          orgSceneY = event.getSceneY();
        });
    icon.setOnMouseDragged(
        event -> {
          if (editEquipEnabled) {
            double offsetX = event.getX();
            double offsetY = event.getY();
            icon.relocate(icon.getLayoutX() + offsetX, icon.getLayoutY() + offsetY);
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            equipMoved = true;
          }
        });
    icon.setOnMouseReleased(
        event -> {
          if (editEquipEnabled) {
            Location loc = snapToClosestLocation(icon);
            if (loc != null) {
              eq.setLocation(loc);
            }
          }
        });

    mapPane.getChildren().add(icon);
    icon.setLayoutY(viewCoords[1]);
    icon.setLayoutX(viewCoords[0]);
    equipIcons.add(icon);
  }

  public void setServiceIcons() {
    String floor = null;
    LinkedList<Request> allRequests = rdao.list();
    LinkedList<Request> filtered = filterRequests(allRequests);
    for (SVGPath icon : serviceIcons) {
      removeIcon(icon);
    }
    serviceIcons.clear();
    for (Request r : filtered) {
      if ((r.getLocation() != null || r.getPatient() != null)
          && !r.getStatus().equals("Completed")) {
        if (r.getLocation() != null) {
          floor = r.getLocation().getFloor();
        }
        if (r.getPatient() != null) {
          floor = r.getPatient().getLocation().getFloor();
        }
        if (stringtoFloorLevel(floor) == floorLevel) addServiceIcon(r);
      }
    }
    resetPane();
  }

  public void addServiceIcon(Request r) {
    int x = 0;
    int y = 0;
    SVGPath icon = new SVGPath();
    icon.setContent(
        "M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z");
    if (r.getLocation() == null) {
      x = r.getPatient().getLocation().getXCoord();
      y = r.getPatient().getLocation().getYCoord();
    } else {
      x = r.getLocation().getXCoord();
      y = r.getLocation().getYCoord();
    }
    int[] viewCoords = mapCoordsToViewCoords(x, y);
    switch (r.getStatus()) {
      case "Pending":
        icon.setFill(Color.rgb(250, 0, 0));
        break;
      case "In Progress":
      case "In-Progress":
        icon.setFill(Color.rgb(100, 0, 100));
        break;
      default:
        icon.setFill(Color.rgb(100, 100, 100));
        break;
    }
    icon.hoverProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (newVal) {
                reqInfoPane.setVisible(true);
                reqInfoPane.setLayoutX(viewCoords[0] - 76);
                reqInfoPane.setLayoutY(viewCoords[1] + 15);
                reqTypeLabel.setText(r.getType());
                reqStatusLabel.setText(r.getStatus());
              } else {
                reqInfoPane.setVisible(false);
              }
            });
    icon.setLayoutX(viewCoords[0]);
    icon.setLayoutY(viewCoords[1]);
    mapPane.getChildren().add(icon);
    serviceIcons.add(icon);
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
      case "4":
        return 5;
      case "5":
        return 6;
      default:
        return -1;
    }
  }

  public int[] mapCoordsToViewCoords(int x, int y) {
    double mapWidth = 1060;
    double mapHeight = 930;
    int fitWidth = (int) mapImage.getFitWidth();
    int fitHeight = (int) mapImage.getFitHeight();
    double xView = ((x / mapWidth) * fitWidth);
    double yView = ((y / mapHeight) * fitHeight);
    return new int[] {(int) xView, (int) yView};
  }

  public int[] imageCoordsToCSVCoords(int x, int y) {
    int mapWidth = 1060;
    int mapHeight = 930;
    double fitWidth = mapImage.getFitWidth();
    double fitHeight = mapImage.getFitHeight();
    double xCSV = ((x / fitWidth) * mapWidth);
    double yCSV = ((y / fitHeight) * mapHeight);

    return new int[] {(int) xCSV, (int) yCSV};
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
    floorLabel.setText(floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL2.png"));
    l2Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void goToFloorL1() {
    floorLevel = 1;
    floorString = "L1";
    resetFloorSelectors();
    floorLabel.setText(floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL1.png"));
    l1Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void goToFloor1() {
    floorLevel = 2;
    floorString = "1";
    resetFloorSelectors();
    floorLabel.setText("F" + floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor1.png"));
    f1Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void goToFloor2() {
    floorLevel = 3;
    floorString = "2";
    resetFloorSelectors();
    floorLabel.setText("F" + floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor2.png"));
    f2Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void goToFloor3() {
    floorLevel = 4;
    floorString = "3";
    resetFloorSelectors();
    floorLabel.setText("F" + floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor3.png"));
    f3Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void goToFloor4() {
    floorLevel = 5;
    floorString = "4";
    resetFloorSelectors();
    floorLabel.setText("F" + floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor4.png"));
    f4Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void goToFloor5() {
    floorLevel = 6;
    floorString = "5";
    resetFloorSelectors();
    floorLabel.setText("F" + floorString);
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor5.png"));
    f5Button.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/SelectedFloor.png"));
    setAll();
  }

  public void startAdd() {
    confirmImage.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/Add.png"));
    lockHover = true;
    addEnabled = true;
    addButton.setVisible(false);
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    resetButton.setVisible(false);

    backButton.setVisible(true);
    floorBackground.setVisible(true);
    typeDropdown.setVisible(true);
    locationName.setVisible(true);
    confirmButton.setVisible(true);
    markerButton.setVisible(true);
    filterButton.setDisable(true);
  }

  public void addLocation() {
    if (typeDropdown.getValue() != null
        && mapPane.getChildren().contains(marker)
        && !locationName.getText().equals("")) {
      int markerCoordX = (int) marker.getLayoutX() + 2;
      int markerCoordY = (int) marker.getLayoutY() + 2;
      int[] newCoords = imageCoordsToCSVCoords(markerCoordX, markerCoordY);
      String nodeType = typeDropdown.getValue();
      String nodeID = dao.getNextID(floorString, nodeType);
      String name = locationName.getText();
      String building = "Tower";
      Location newLoc =
          new Location(
              nodeID,
              newCoords[0],
              newCoords[1],
              floorString,
              building,
              nodeType,
              name,
              name,
              true);
      // Pass new location into database
      dao.add(newLoc);
      endAdd();
    } else {
      System.out.println("Missing required field!");
    }
  }

  public void endAdd() {
    lockHover = false;
    addEnabled = false;
    addButton.setVisible(true);
    editButton.setVisible(true);
    deleteButton.setVisible(true);
    resetButton.setVisible(true);
    backButton.setVisible(false);

    floorBackground.setVisible(false);
    typeDropdown.setVisible(false);
    locationName.setVisible(false);

    markerButton.setVisible(false);

    confirmButton.setVisible(false);
    typeDropdown.setValue("");
    locationName.setText("");
    filterButton.setDisable(false);
    clearMarker();
    setRoomIcons();
  }

  public void startDelete() {
    confirmImage.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/Delete.png"));
    lockHover = true;
    deleteEnabled = true;
    addButton.setVisible(false);
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    resetButton.setVisible(false);
    backButton.setVisible(true);
    floorBackground.setVisible(true);
    locationDropdown.setVisible(true);
    confirmButton.setVisible(true);
    filterButton.setDisable(true);
  }

  public void deleteLoc() {
    if (locationDropdown.getValue() != null) {
      String name = locationDropdown.getValue();
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
        // errorLabel.setText("Delete Failed! Check that no equipment is tied to the room!");
      } else {
        dao.delete(target);
        endDelete();
        setRoomIcons();
        // resetDisplayPanes();
      }
    } else {
      System.out.println("No location selected!");
    }
  }

  public void endDelete() {
    lockHover = false;
    deleteEnabled = false;
    addButton.setVisible(true);
    editButton.setVisible(true);
    deleteButton.setVisible(true);
    resetButton.setVisible(true);
    backButton.setVisible(false);
    floorBackground.setVisible(false);
    locationDropdown.setVisible(false);
    confirmButton.setVisible(false);
    filterButton.setDisable(false);

    locationDropdown.setValue("");
    clearMarker();
  }

  public void startEdit() {
    confirmImage.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/Edit.png"));
    lockHover = true;
    editEnabled = true;
    firstMove = true;
    addButton.setVisible(false);
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    resetButton.setVisible(false);
    backButton.setVisible(true);
    filterButton.setDisable(true);

    floorBackground.setVisible(true);
    locationDropdown.setVisible(true);
    locationName.setVisible(true);
    confirmButton.setVisible(true);
    undoMoveButton.setVisible(true);
  }

  public void editLocation() {
    if (locationDropdown.getValue() != null) {
      String oldName = locationDropdown.getValue();
      LinkedList<Location> findLoc = dao.listByAttribute("longName", oldName);
      Location toChange = findLoc.pop();
      String name = toChange.getLongName();
      if (!locationName.getText().equals("") && !locationName.getText().equals(name)) {
        toChange.setShortName(locationName.getText());
        toChange.setLongName(locationName.getText());
      }
      dao.update(toChange);
      endEdit();
    }
  }

  public void endEdit() {
    lockHover = false;
    editEnabled = false;
    addButton.setVisible(true);
    editButton.setVisible(true);
    deleteButton.setVisible(true);
    resetButton.setVisible(true);
    backButton.setVisible(false);
    floorBackground.setVisible(false);
    locationDropdown.setVisible(false);
    locationName.setVisible(false);
    locationName.setText("");
    confirmButton.setVisible(false);
    undoMoveButton.setVisible(false);
    filterButton.setDisable(false);
    startingCoordinates[0] = -1;
    startingCoordinates[1] = -1;
    clearMarker();
  }

  public void getCoordinates(MouseEvent event) {
    if (addEnabled) {
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
      marker.setLayoutX(coordinates[0] - 8);
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

  public void populateAvailabilityDropdown() {
    availabilityDropdown.getItems().add("Unavailable");
    availabilityDropdown.getItems().add("Requested");
    availabilityDropdown.getItems().add("Available");
  }

  public void populateLocationDropdown() {
    locationDropdown.getItems().clear();
    floorLocations = dao.getLocationsByFloor(floorLevel);
    for (Location loc : floorLocations) {
      String name = loc.getLongName();
      locationDropdown.getItems().add(name);
    }
  }

  public void populateStateDropdown() {
    stateDropdown.getItems().add("Clean");
    stateDropdown.getItems().add("Dirty");
  }

  public void confirmChanges() {
    if (addEnabled) {
      addLocation();
    }
    if (deleteEnabled) {
      deleteLoc();
    }
    if (editEnabled) {
      editLocation();
    }
    if (editEquipEnabled) {
      editEquip();
    }
    setAll();
  }

  public void cancel() {
    if (addEnabled) {
      endAdd();
    }
    if (deleteEnabled) {
      endDelete();
    }
    if (editEnabled) {
      undoMove();
      endEdit();
    }
    if (editEquipEnabled) {
      endEquipEdit();
    }
    setAll();
  }

  public void zoom(ScrollEvent event) {
    // get scroll delta
    double delta = event.getDeltaY();
    double pixelAdjust = 0;

    // zoom in/out
    if (delta > 0) {
      scaleX *= 1.1;
      scaleY *= 1.1;
    } else {
      scaleX *= 0.9;
      scaleY *= 0.9;
    }

    // set new scale
    mapPane.setScaleX(scaleX);
    mapPane.setScaleY(scaleY);
  }

  @FXML
  public void resetLocations() {
    DatabaseController.getInstance().resetAllDBs();
    setAll();
  }

  public void resetPane() {
    mapPane.getChildren().remove(locInfoPane);
    mapPane.getChildren().add(locInfoPane);
    mapPane.getChildren().remove(equipInfoPane);
    mapPane.getChildren().add(equipInfoPane);
    mapPane.getChildren().remove(reqInfoPane);
    mapPane.getChildren().add(reqInfoPane);
  }

  public void toggleEquipPane(MedicalEquipment eq) {
    if (equipPaneVisible) {
      equipInfoPane.setVisible(false);
      equipEditButton.setVisible(false);
      equipPaneVisible = false;
      availabilityDropdown.setValue("");
      locationDropdown.setValue("");
      toEdit = null;
    } else {
      equipInfoPane.setVisible(true);
      equipEditButton.setVisible(true);
      equipPaneVisible = true;
      availabilityDropdown.setValue(eq.getAvailability());
      locationDropdown.setValue(eq.getLocation().getLongName());
      if (eq.getIsClean()) {
        stateDropdown.setValue("Clean");
      } else stateDropdown.setValue("Dirty");
      toEdit = eq;
    }
  }

  public void startEquipEdit() {
    confirmImage.setImage(new Image("edu/wpi/cs3733/D22/teamB/assets/mapAssets/Edit.png"));
    lockHover = true;
    editEquipEnabled = true;
    addButton.setVisible(false);
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    resetButton.setVisible(false);
    backButton.setVisible(true);

    floorBackground.setVisible(true);
    locationDropdown.setVisible(true);
    availabilityDropdown.setVisible(true);
    stateDropdown.setVisible(true);
    confirmButton.setVisible(true);
    filterButton.setDisable(true);
    equipInfoPane.setVisible(false);

    for (SVGPath icon : equipIcons) {
      removeIcon(icon);
    }
    equipIcons.clear();

    addEquipIcon(toEdit);
  }

  public void editEquip() {
    LinkedList<Location> equipLoc = dao.listByAttribute("longName", locationDropdown.getValue());
    Location loc = equipLoc.pop();
    if (toEdit != null) {
      toEdit.setAvailability(availabilityDropdown.getValue());
      if (!equipMoved) {
        toEdit.setLocation(loc);
      }
      if (stateDropdown.getValue().equals("Clean")) {
        toEdit.setIsClean(true);
      } else toEdit.setIsClean(false);
      edao.update(toEdit);
    }
    endEquipEdit();
  }

  public void endEquipEdit() {
    lockHover = false;
    editEquipEnabled = false;
    addButton.setVisible(true);
    editButton.setVisible(true);
    deleteButton.setVisible(true);
    resetButton.setVisible(true);
    backButton.setVisible(false);
    filterButton.setDisable(false);
    floorBackground.setVisible(false);
    locationDropdown.setVisible(false);
    availabilityDropdown.setVisible(false);
    confirmButton.setVisible(false);
    equipInfoPane.setVisible(false);
    equipEditButton.setVisible(false);
    equipPaneVisible = false;
    stateDropdown.setVisible(false);
    availabilityDropdown.setValue("");
    locationDropdown.setValue("");
    stateDropdown.setValue("");
    setEquipIcons();
    toEdit = null;
    equipMoved = false;
  }

  public LinkedList<Location> filterLocs(LinkedList<Location> locList) {
    LinkedList<Location> toRemove = new LinkedList<Location>();
    for (Location loc : locList) {
      for (String type : locFilterList) {
        if (loc.getNodeType().equals(type)) {
          toRemove.add(loc);
        }
      }
    }
    locList.removeAll(toRemove);
    return locList;
  }

  public void toggleFilter() {
    if (filterOpen) {
      equipEditButton.setDisable(false);
      addButton.setVisible(true);
      editButton.setVisible(true);
      deleteButton.setVisible(true);
      floorBackground.setVisible(false);
      filterPane.setVisible(false);
      filterOpen = false;
    } else {
      equipEditButton.setDisable(true);
      addButton.setVisible(false);
      editButton.setVisible(false);
      deleteButton.setVisible(false);
      floorBackground.setVisible(true);
      filterPane.setVisible(true);
      filterOpen = true;
    }
  }

  public void toggleBath() {
    if (bathOn) {
      if (!locFilterList.contains("BATH")) locFilterList.add("BATH");
      bath.setTextFill(Color.DARKGREY);
      bathOn = false;
    } else {
      locFilterList.remove("BATH");
      bath.setTextFill(Color.WHITE);
      bathOn = true;
    }
    setRoomIcons();
  }

  public void toggleDept() {
    if (deptOn) {
      if (!locFilterList.contains("DEPT")) locFilterList.add("DEPT");
      dept.setTextFill(Color.DARKGREY);
      deptOn = false;
    } else {
      locFilterList.remove("DEPT");
      dept.setTextFill(Color.WHITE);
      deptOn = true;
    }
    setRoomIcons();
  }

  public void toggleDirt() {
    if (dirtOn) {
      if (!locFilterList.contains("DIRT")) locFilterList.add("DIRT");
      dirt.setTextFill(Color.DARKGREY);
      dirtOn = false;
    } else {
      locFilterList.remove("DIRT");
      dirt.setTextFill(Color.WHITE);
      dirtOn = true;
    }
    setRoomIcons();
  }

  public void toggleElev() {
    if (elevOn) {
      if (!locFilterList.contains("ELEV")) locFilterList.add("ELEV");
      elev.setTextFill(Color.DARKGREY);
      elevOn = false;
    } else {
      locFilterList.remove("ELEV");
      elev.setTextFill(Color.WHITE);
      elevOn = true;
    }
    setRoomIcons();
  }

  public void toggleExit() {
    if (exitOn) {
      if (!locFilterList.contains("EXIT")) locFilterList.add("EXIT");
      exit.setTextFill(Color.DARKGREY);
      exitOn = false;
    } else {
      locFilterList.remove("EXIT");
      exit.setTextFill(Color.WHITE);
      exitOn = true;
    }
    setRoomIcons();
  }

  public void toggleHall() {
    if (hallOn) {
      if (!locFilterList.contains("HALL")) locFilterList.add("HALL");
      hall.setTextFill(Color.DARKGREY);
      hallOn = false;
    } else {
      locFilterList.remove("HALL");
      hall.setTextFill(Color.WHITE);
      hallOn = true;
    }
    setRoomIcons();
  }

  public void toggleInfo() {
    if (infoOn) {
      if (!locFilterList.contains("INFO")) locFilterList.add("INFO");
      info.setTextFill(Color.DARKGREY);
      infoOn = false;
    } else {
      locFilterList.remove("INFO");
      info.setTextFill(Color.WHITE);
      infoOn = true;
    }
    setRoomIcons();
  }

  public void toggleLabs() {
    if (labsOn) {
      if (!locFilterList.contains("LABS")) locFilterList.add("LABS");
      labs.setTextFill(Color.DARKGREY);
      labsOn = false;
    } else {
      locFilterList.remove("LABS");
      labs.setTextFill(Color.WHITE);
      labsOn = true;
    }
    setRoomIcons();
  }

  public void togglePati() {
    if (patiOn) {
      if (!locFilterList.contains("PATI")) locFilterList.add("PATI");
      pati.setTextFill(Color.DARKGREY);
      patiOn = false;
    } else {
      locFilterList.remove("PATI");
      pati.setTextFill(Color.WHITE);
      patiOn = true;
    }
    setRoomIcons();
  }

  public void toggleRest() {
    if (restOn) {
      if (!locFilterList.contains("REST")) locFilterList.add("REST");
      rest.setTextFill(Color.DARKGREY);
      restOn = false;
    } else {
      locFilterList.remove("REST");
      rest.setTextFill(Color.WHITE);
      restOn = true;
    }
    setRoomIcons();
  }

  public void toggleRetl() {
    if (retlOn) {
      if (!locFilterList.contains("RETL")) locFilterList.add("RETL");
      retl.setTextFill(Color.DARKGREY);
      retlOn = false;
    } else {
      locFilterList.remove("RETL");
      retl.setTextFill(Color.WHITE);
      retlOn = true;
    }
    setRoomIcons();
  }

  public void toggleServ() {
    if (servOn) {
      if (!locFilterList.contains("SERV")) locFilterList.add("SERV");
      serv.setTextFill(Color.DARKGREY);
      servOn = false;
    } else {
      locFilterList.remove("SERV");
      serv.setTextFill(Color.WHITE);
      servOn = true;
    }
    setRoomIcons();
  }

  public void toggleStai() {
    if (staiOn) {
      if (!locFilterList.contains("STAI")) locFilterList.add("STAI");
      stai.setTextFill(Color.DARKGREY);
      staiOn = false;
    } else {
      locFilterList.remove("STAI");
      stai.setTextFill(Color.WHITE);
      staiOn = true;
    }
    setRoomIcons();
  }

  public void toggleStor() {
    if (storOn) {
      if (!locFilterList.contains("STOR")) locFilterList.add("STOR");
      stor.setTextFill(Color.DARKGREY);
      storOn = false;
    } else {
      locFilterList.remove("STOR");
      stor.setTextFill(Color.WHITE);
      storOn = true;
    }
    setRoomIcons();
  }

  public void showAllLocs() {
    bathOn = false;
    deptOn = false;
    dirtOn = false;
    elevOn = false;
    exitOn = false;
    hallOn = false;
    infoOn = false;
    labsOn = false;
    patiOn = false;
    restOn = false;
    retlOn = false;
    servOn = false;
    staiOn = false;
    storOn = false;
    toggleBath();
    toggleDept();
    toggleDirt();
    toggleElev();
    toggleExit();
    toggleHall();
    toggleInfo();
    toggleLabs();
    togglePati();
    toggleRest();
    toggleRetl();
    toggleServ();
    toggleStai();
    toggleStor();
  }

  public void hideAllLocs() {
    bathOn = true;
    deptOn = true;
    dirtOn = true;
    elevOn = true;
    exitOn = true;
    hallOn = true;
    infoOn = true;
    labsOn = true;
    patiOn = true;
    restOn = true;
    retlOn = true;
    servOn = true;
    staiOn = true;
    storOn = true;
    toggleBath();
    toggleDept();
    toggleDirt();
    toggleElev();
    toggleExit();
    toggleHall();
    toggleInfo();
    toggleLabs();
    togglePati();
    toggleRest();
    toggleRetl();
    toggleServ();
    toggleStai();
    toggleStor();
  }

  public LinkedList<MedicalEquipment> filterEquip(LinkedList<MedicalEquipment> equipList) {
    LinkedList<MedicalEquipment> toRemove = new LinkedList<MedicalEquipment>();
    for (MedicalEquipment eq : equipList) {
      for (String type : equipFilterList) {
        if (eq.getType().equals(type)) {
          toRemove.add(eq);
        }
      }
    }
    equipList.removeAll(toRemove);
    return equipList;
  }

  public void toggleBed() {
    if (bedOn) {
      if (!equipFilterList.contains("BED")) equipFilterList.add("BED");
      bed.setTextFill(Color.DARKGREY);
      bedOn = false;
    } else {
      equipFilterList.remove("BED");
      bed.setTextFill(Color.WHITE);
      bedOn = true;
    }
    setEquipIcons();
  }

  public void toggleXR() {
    if (xrOn) {
      if (!equipFilterList.contains("XR")) equipFilterList.add("XR");
      xr.setTextFill(Color.DARKGREY);
      xrOn = false;
    } else {
      equipFilterList.remove("XR");
      xr.setTextFill(Color.WHITE);
      xrOn = true;
    }
    setEquipIcons();
  }

  public void toggleRecl() {
    if (reclOn) {
      if (!equipFilterList.contains("RECL")) equipFilterList.add("RECL");
      recl.setTextFill(Color.DARKGREY);
      reclOn = false;
    } else {
      equipFilterList.remove("RECL");
      recl.setTextFill(Color.WHITE);
      reclOn = true;
    }
    setEquipIcons();
  }

  public void togglePump() {
    if (pumpOn) {
      if (!equipFilterList.contains("PUMP")) equipFilterList.add("PUMP");
      pump.setTextFill(Color.DARKGREY);
      pumpOn = false;
    } else {
      equipFilterList.remove("PUMP");
      pump.setTextFill(Color.WHITE);
      pumpOn = true;
    }
    setEquipIcons();
  }

  public void showAllEquip() {
    bedOn = false;
    xrOn = false;
    reclOn = false;
    pumpOn = false;
    toggleBed();
    toggleXR();
    toggleRecl();
    togglePump();
  }

  public void hideAllEquip() {
    bedOn = true;
    xrOn = true;
    reclOn = true;
    pumpOn = true;
    toggleBed();
    toggleXR();
    toggleRecl();
    togglePump();
  }

  public LinkedList<Request> filterRequests(LinkedList<Request> reqList) {
    LinkedList<Request> toRemove = new LinkedList<Request>();
    for (Request r : reqList) {
      for (String type : requestFilterList) {
        if (r.getType().equals(type)) {
          toRemove.add(r);
        }
      }
      if (!customOn && r.getRequestID().startsWith("CUS")) toRemove.add(r);
    }
    reqList.removeAll(toRemove);
    return reqList;
  }

  public void toggleEquipReq() {
    if (eqReqOn) {
      if (!requestFilterList.contains("Equipment Delivery"))
        requestFilterList.add("Equipment Delivery");
      equip.setTextFill(Color.DARKGREY);
      eqReqOn = false;
    } else {
      equip.setTextFill(Color.WHITE);
      requestFilterList.remove("Equipment Delivery");
      eqReqOn = true;
    }
    setServiceIcons();
  }

  public void toggleLabReq() {
    if (labReqOn) {
      if (!requestFilterList.contains("Lab Test")) requestFilterList.add("Lab Test");
      lab.setTextFill(Color.DARKGREY);
      labReqOn = false;
    } else {
      lab.setTextFill(Color.WHITE);
      requestFilterList.remove("Lab Test");
      labReqOn = true;
    }
    setServiceIcons();
  }

  public void toggleMedReq() {
    if (medOn) {
      if (!requestFilterList.contains("Medicine")) requestFilterList.add("Medicine");
      med.setTextFill(Color.DARKGREY);
      medOn = false;
    } else {
      med.setTextFill(Color.WHITE);
      requestFilterList.remove("Medicine");
      medOn = true;
    }
    setServiceIcons();
  }

  public void toggleMealReq() {
    if (mealOn) {
      if (!requestFilterList.contains("Meal")) requestFilterList.add("Meal");
      meal.setTextFill(Color.DARKGREY);
      mealOn = false;
    } else {
      meal.setTextFill(Color.WHITE);
      requestFilterList.remove("Meal");
      mealOn = true;
    }
    setServiceIcons();
  }

  public void toggleInterpReq() {
    if (interpOn) {
      if (!requestFilterList.contains("Interpreter")) requestFilterList.add("Interpreter");
      interp.setTextFill(Color.DARKGREY);
      interpOn = false;
    } else {
      interp.setTextFill(Color.WHITE);
      requestFilterList.remove("Interpreter");
      interpOn = true;
    }
    setServiceIcons();
  }

  public void toggleIptReq() {
    if (iptOn) {
      if (!requestFilterList.contains("Transfer")) requestFilterList.add("Transfer");
      ipt.setTextFill(Color.DARKGREY);
      iptOn = false;
    } else {
      ipt.setTextFill(Color.WHITE);
      requestFilterList.remove("Transfer");
      iptOn = true;
    }
    setServiceIcons();
  }

  public void toggleLaundReq() {
    if (laundOn) {
      if (!requestFilterList.contains("Laundry")) requestFilterList.add("Laundry");
      laund.setTextFill(Color.DARKGREY);
      laundOn = false;
    } else {
      laund.setTextFill(Color.WHITE);
      requestFilterList.remove("Laundry");
      laundOn = true;
    }
    setServiceIcons();
  }

  public void toggleSecReq() {
    if (secOn) {
      if (!requestFilterList.contains("Security")) requestFilterList.add("Security");
      sec.setTextFill(Color.DARKGREY);
      secOn = false;
    } else {
      sec.setTextFill(Color.WHITE);
      requestFilterList.remove("Security");
      secOn = true;
    }
    setServiceIcons();
  }

  public void toggleGiftReq() {
    if (giftOn) {
      if (!requestFilterList.contains("Gift")) requestFilterList.add("Gift");
      gift.setTextFill(Color.DARKGREY);
      giftOn = false;
    } else {
      gift.setTextFill(Color.WHITE);
      requestFilterList.remove("Gift");
      giftOn = true;
    }
    setServiceIcons();
  }

  public void toggleSanReq() {
    if (sanOn) {
      if (!requestFilterList.contains("Sanitation")) requestFilterList.add("Sanitation");
      san.setTextFill(Color.DARKGREY);
      sanOn = false;
    } else {
      san.setTextFill(Color.WHITE);
      requestFilterList.remove("Sanitation");
      sanOn = true;
    }
    setServiceIcons();
  }

  public void toggleCustom() {
    if (customOn) {
      custom.setTextFill(Color.DARKGREY);
      customOn = false;
    } else {
      custom.setTextFill(Color.WHITE);
      customOn = true;
    }
    setServiceIcons();
  }

  public void showAllReqs() {
    eqReqOn = false;
    labReqOn = false;
    medOn = false;
    mealOn = false;
    interpOn = false;
    iptOn = false;
    customOn = false;
    laundOn = false;
    secOn = false;
    giftOn = false;
    sanOn = false;

    toggleEquipReq();
    toggleLabReq();
    toggleMedReq();
    toggleMealReq();
    toggleInterpReq();
    toggleIptReq();
    toggleCustom();
    toggleLaundReq();
    toggleSecReq();
    toggleGiftReq();
    toggleSanReq();
  }

  public void hideAllReqs() {
    eqReqOn = true;
    labReqOn = true;
    medOn = true;
    mealOn = true;
    interpOn = true;
    iptOn = true;
    customOn = true;
    laundOn = true;
    secOn = true;
    giftOn = true;
    sanOn = true;

    toggleEquipReq();
    toggleLabReq();
    toggleMedReq();
    toggleMealReq();
    toggleInterpReq();
    toggleIptReq();
    toggleCustom();
    toggleLaundReq();
    toggleSecReq();
    toggleGiftReq();
    toggleSanReq();
  }

  public void allIconsOn() {
    showAllLocs();
    showAllEquip();
    showAllReqs();
  }

  public void allIconsOff() {
    hideAllLocs();
    hideAllEquip();
    hideAllReqs();
  }

  public void undoMove() {
    if (editEnabled && locationDropdown.getValue() != null) {
      Location currentLoc = dao.listByAttribute("longName", locationDropdown.getValue()).pop();
      currentLoc.setXCoord(startingCoordinates[0]);
      currentLoc.setYCoord(startingCoordinates[1]);
      dao.update(currentLoc);
      setRoomIcons();
      firstMove = true;
    }
  }

  private Location snapToClosestLocation(SVGPath icon) {
    Location end = null;
    double minDistance = Double.MAX_VALUE;

    floorLocations = dao.getLocationsByFloor(floorLevel);
    LinkedList<Location> filtered = filterLocs(floorLocations);

    for (Location loc : filtered) {
      int[] mapCoords = mapCoordsToViewCoords(loc.getXCoord(), loc.getYCoord());
      double distance =
          Math.sqrt(
              Math.pow(mapCoords[0] - icon.getLayoutX(), 2)
                  + Math.pow(mapCoords[1] - icon.getLayoutY(), 2));
      if (distance < minDistance) {
        minDistance = distance;
        end = loc;
      }
    }

    return end;
  }
}
