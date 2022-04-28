package edu.wpi.cs3733.D22.teamB.controllers;

import static edu.wpi.cs3733.D22.teamB.App.currentUser;
import static java.lang.Math.round;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class InteractiveMapPageController extends AStarVisualization {
  @FXML JFXButton menuButton;
  @FXML JFXButton addButton;
  @FXML JFXButton editButton;
  @FXML JFXButton deleteButton;
  @FXML JFXButton f5Button;
  @FXML JFXButton f4Button;
  @FXML JFXButton f3Button;
  @FXML JFXButton f2Button;
  @FXML JFXButton f1Button;
  @FXML JFXButton l1Button;
  @FXML JFXButton l2Button;
  @FXML JFXButton backButton;
  JFXButton currentButton;

  @FXML GridPane floorBackground;
  @FXML JFXComboBox<String> typeDropdown;
  @FXML JFXComboBox<String> locationDropdown;
  @FXML JFXComboBox<String> locationDropdown2;
  @FXML JFXTextArea locationName;
  @FXML JFXButton confirmButton;
  @FXML JFXButton clearPathButton;
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
  private Boolean aStarEnabled = false;
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

  public static int floorLevel = 2;
  private int[] coordinates;
  private int[] startingCoordinates = new int[2];
  private String floorString = "1";

  private double startDragX, startDragY;
  private double scaleX = 1.0, scaleY = 1.0;
  private double orgSceneX, orgSceneY;

  public void initialize() {
    dao = LocationsDB.getInstance();
    edao = MedicalEquipmentDB.getInstance();
    rdao = ServiceRequestsDB.getInstance();

    roomIcons = new LinkedList<SVGPath>();
    equipIcons = new LinkedList<SVGPath>();
    serviceIcons = new LinkedList<SVGPath>();

    currentButton = f1Button;
    view();

    showAllLocs();
    populateTypeDropdown();
    populateAvailabilityDropdown();
    populateStateDropdown();

    // drawEdgesPerFloor(4);
    // calculatePath(dao.getByID("bHALL006L2"), dao.getByID("bHALL00705"));

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
    if (!currentUser.getPosition().equals("ADMIN")) {
      addButton.setDisable(true);
      editButton.setDisable(true);
      deleteButton.setDisable(true);
      equipEditButton.setDisable(true);
      resetButton.setDisable(true);
    }
  }

  public void setAll() {
    clearLines();
    populateLocationDropdown();
    setRoomIcons();
    setEquipIcons();
    setServiceIcons();
    drawPathFloor(floorString);
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
    // icon.setContent("M 1 1 H 9 V 9 H 1 Z");
    icon.setContent(
        "M5 0C3.086 0 1.53 1.557 1.53 3.47c0 1.842 3.149 6.145 3.283 6.328l0.125 0.17c0.015 0.02 0.038 0.032 0.062 0.032c0.025 0 0.048 -0.012 0.063 -0.032l0.125 -0.17c0.134 -0.182 3.283 -4.486 3.283 -6.328C8.47 1.557 6.913 0 5 0zM5 2.227c0.686 0 1.243 0.558 1.243 1.243c0 0.685 -0.558 1.243 -1.243 1.243c-0.685 0 -1.243 -0.558 -1.243 -1.243C3.757 2.785 4.315 2.227 5 2.227z");
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
        icon.setFill(Color.rgb(169, 100, 0));
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
            "M8.2 2.353c-0.206 -0.189 -0.48 -0.304 -0.781 -0.304h-1.271c-0.832 0 -1.396 0.859 -1.059 1.622H1.77c-0.001 0 -0.002 0 -0.002 0C0.793 3.672 0 4.465 0 5.441v2.165c0 0.191 0.155 0.346 0.346 0.346h1.424c0.191 0 0.346 -0.155 0.346 -0.346v-0.624h5.767v0.624c0 0.191 0.155 0.346 0.346 0.346h1.424c0.191 0 0.346 -0.155 0.346 -0.346v-4.499C10 2.169 8.866 1.697 8.2 2.353zM7.884 6.29h-5.767v-0.452h5.767V6.29zM7.42 3.671h-1.274c-0.256 -0.001 -0.464 -0.209 -0.464 -0.465c0 -0.256 0.209 -0.465 0.465 -0.465h1.271c0.256 0 0.465 0.209 0.465 0.465C7.884 3.462 7.676 3.67 7.42 3.671z");
        break;
      case "XR":
        icon.setContent(
            "M9.236 0H0.764C0.381 0 0.069 0.312 0.069 0.695v8.609c0 0.383 0.312 0.695 0.695 0.695h8.472c0.383 0 0.695 -0.312 0.695 -0.695V0.695C9.931 0.312 9.619 0 9.236 0zM9.64 9.305c0 0.223 -0.181 0.404 -0.404 0.404H0.764c-0.223 0 -0.404 -0.181 -0.404 -0.404V0.695C0.36 0.473 0.541 0.292 0.764 0.292h8.472c0.223 0 0.404 0.181 0.404 0.404V9.305zM8.539 0.944h-7.079c-0.253 0 -0.459 0.207 -0.459 0.459v7.194c0 0.253 0.207 0.459 0.459 0.459h7.079c0.253 0 0.459 -0.207 0.459 -0.459V1.403C8.998 1.151 8.792 0.944 8.539 0.944zM4.413 6.926c-0.023 0.526 -0.043 1.461 -1.88 0.953c-1.882 -0.521 1.754 -4.338 1.88 -2.983C4.487 5.685 4.436 6.418 4.413 6.926zM5.396 5.952c-0.13 -0.052 -0.285 -0.083 -0.449 -0.083c-0.15 0 -0.291 0.028 -0.415 0.072c0.001 -0.105 0.001 -0.212 0 -0.322c0.083 -0.022 0.169 -0.041 0.26 -0.049V1.81c0 -0.086 0.07 -0.155 0.155 -0.155c0.086 0 0.155 0.07 0.155 0.155V5.57c0.104 0.01 0.202 0.032 0.295 0.06C5.395 5.74 5.394 5.847 5.396 5.952zM7.45 7.98c-1.895 0.524 -1.915 -0.44 -1.939 -0.982c-0.023 -0.523 -0.076 -1.279 0 -2.094C5.641 3.507 9.391 7.444 7.45 7.98z");
        break;
      case "RECL":
        icon.setContent(
            "M2.624 6.25H6.758L7.199 1.851C7.256 1.262 7.172 0.806 6.948 0.484C6.724 0.161 6.365 0 5.872 0H3.523C3.03 0 2.669 0.161 2.44 0.484C2.212 0.806 2.126 1.262 2.183 1.851L2.624 6.25ZM6.25 8.125H3.125V9.591C3.125 9.704 3.087 9.8 3.012 9.88C2.936 9.96 2.844 10 2.734 10L1.957 10C1.847 10 1.754 9.964 1.679 9.892C1.603 9.82 1.566 9.728 1.566 9.615L1.56 7.987C1.417 7.927 1.292 7.85 1.183 7.755C1.075 7.661 0.988 7.546 0.92 7.41C0.853 7.274 0.798 7.138 0.755 7.004C0.713 6.87 0.681 6.704 0.661 6.505C0.64 6.307 0.627 6.128 0.621 5.968C0.615 5.807 0.612 5.607 0.612 5.367C0.432 5.331 0.285 5.245 0.171 5.111C0.057 4.977 0 4.812 0 4.615C0 4.395 0.078 4.212 0.235 4.066C0.392 3.919 0.636 3.846 0.966 3.846C1.109 3.846 1.23 3.866 1.33 3.906C1.43 3.946 1.513 4.009 1.578 4.096C1.643 4.182 1.692 4.258 1.725 4.324C1.757 4.39 1.794 4.483 1.835 4.603L2.066 6.544C2.099 6.708 2.132 6.802 2.164 6.826C2.205 6.858 2.295 6.874 2.433 6.874H6.935C7.086 6.874 7.176 6.858 7.207 6.826C7.238 6.794 7.269 6.7 7.302 6.544L7.547 4.603C7.588 4.483 7.625 4.39 7.658 4.324C7.69 4.258 7.739 4.182 7.804 4.096C7.87 4.009 7.952 3.946 8.052 3.906C8.152 3.866 8.273 3.846 8.416 3.846C8.746 3.846 8.992 3.919 9.153 4.066C9.314 4.212 9.395 4.395 9.395 4.615C9.395 4.808 9.336 4.972 9.22 5.108C9.104 5.244 8.954 5.331 8.771 5.367C8.771 5.611 8.768 5.812 8.762 5.971C8.755 6.129 8.742 6.309 8.722 6.511C8.701 6.714 8.671 6.883 8.63 7.019C8.589 7.155 8.535 7.293 8.468 7.431C8.401 7.569 8.313 7.685 8.205 7.779C8.097 7.874 7.971 7.951 7.829 8.011V9.615C7.829 9.728 7.791 9.82 7.716 9.892C7.64 9.964 7.547 10 7.437 10H6.641C6.531 10 6.439 9.964 6.363 9.892C6.288 9.82 6.25 9.728 6.25 9.615V8.125Z");
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
        if (r.getPatient() != null && r.getLocation() == null) {
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
    if (r.getType().equals("Equipment Delivery")) {
      icon.setOnMouseClicked(
          event -> {
            Location reqLoc = r.getLocation();
            MedicalEquipment reqEq = edao.getByID(r.getEquipmentID());
            Location medEqLoc = reqEq.getLocation();
            calculatePath(medEqLoc, reqLoc);
            drawPathFloor(floorString);
          });
    }
    if (r.getType().equals("Patient Transfer")) {
      icon.setOnMouseClicked(
          event -> {
            Location reqLoc = r.getLocation();
            Location patientLoc = r.getPatient().getLocation();
            calculatePath(patientLoc, reqLoc);
            drawPathFloor(floorString);
          });
    }
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

  public void resetFloorSelectors() {
    currentButton.getStyleClass().remove("request-button-selected");
  }

  public void goToFloorL2() {
    floorLevel = 0;
    floorString = "L2";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL2.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    l2Button.getStyleClass().add("request-button-selected");
    currentButton = l2Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void goToFloorL1() {
    floorLevel = 1;
    floorString = "L1";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL1.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    l1Button.getStyleClass().add("request-button-selected");
    currentButton = l1Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void goToFloor1() {
    floorLevel = 2;
    floorString = "1";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor1.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    f1Button.getStyleClass().add("request-button-selected");
    currentButton = f1Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void goToFloor2() {
    floorLevel = 3;
    floorString = "2";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor2.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    f2Button.getStyleClass().add("request-button-selected");
    currentButton = f2Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void goToFloor3() {
    floorLevel = 4;
    floorString = "3";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor3.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    f3Button.getStyleClass().add("request-button-selected");
    currentButton = f3Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void goToFloor4() {
    floorLevel = 5;
    floorString = "4";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor4.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    f4Button.getStyleClass().add("request-button-selected");
    currentButton = f4Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void goToFloor5() {
    floorLevel = 6;
    floorString = "5";
    resetFloorSelectors();
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor5.png"));
    currentButton.getStyleClass().remove("request-button-selected");
    f5Button.getStyleClass().add("request-button-selected");
    currentButton = f5Button;
    setAll();
    drawPathFloor(floorString);
  }

  public void startAdd() {
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
        DatabaseController.getInstance()
            .add(
                new Activity(
                    new Date(),
                    App.currentUser.getEmployeeID(),
                    target.getNodeID(),
                    null,
                    "Location",
                    "removed"));

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
    if (locationDropdown.getValue() != null || !locationDropdown.getValue().equals("")) {
      String oldName = locationDropdown.getValue();
      LinkedList<Location> findLoc = dao.listByAttribute("longName", oldName);
      if (!findLoc.isEmpty()) {
        Location toChange = findLoc.pop();
        String name = toChange.getLongName();
        if (!locationName.getText().equals("") && !locationName.getText().equals(name)) {
          toChange.setShortName(locationName.getText());
          toChange.setLongName(locationName.getText());
        }
        DatabaseController.getInstance()
            .add(
                new Activity(
                    new Date(),
                    App.currentUser.getEmployeeID(),
                    toChange.getNodeID(),
                    "clean",
                    "Location",
                    "edited"));
        dao.update(toChange);
        endEdit();
      }
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
      if (!currentUser.getLightOn()) {
        marker.setFill(Color.WHITE);
      }
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

  public void populateAllLocationDropdown() {
    locationDropdown.getItems().clear();
    locationDropdown2.getItems().clear();
    floorLocations = dao.list();
    for (Location loc : floorLocations) {
      String name = loc.getLongName();
      locationDropdown.getItems().add(name);
      locationDropdown2.getItems().add(name);
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
    if (aStarEnabled) {
      showAStar();
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
    if (aStarEnabled) {
      endAStar();
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
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("No Equipment Available");
    alert.setHeaderText(
        "Are you sure you want to reset all map Locations to their defaults? This cannot be undone. ");
    alert.setContentText("If you would like to reset all Locations, press OK. ");
    Optional<ButtonType> result = alert.showAndWait();
    ButtonType button = result.orElse(ButtonType.CANCEL);

    if (button == ButtonType.OK) {
      DatabaseController.getInstance().resetAllDBs();
      setAll();
    } else {
      System.out.println("Cancelled");
    }
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
      DatabaseController.getInstance()
          .add(
              new Activity(
                  new Date(),
                  App.currentUser.getEmployeeID(),
                  toEdit.getEquipmentID(),
                  availabilityDropdown.getValue().toLowerCase(),
                  "Medical Equipment",
                  "marked as"));
      if (!equipMoved) {
        toEdit.setLocation(loc);
        DatabaseController.getInstance()
            .add(
                new Activity(
                    new Date(),
                    App.currentUser.getEmployeeID(),
                    toEdit.getEquipmentID(),
                    loc.getNodeID(),
                    "Medical Equipment",
                    "moved to"));
      }
      if (stateDropdown.getValue().equals("Clean")) {
        toEdit.setIsClean(true);
        DatabaseController.getInstance()
            .add(
                new Activity(
                    new Date(),
                    App.currentUser.getEmployeeID(),
                    toEdit.getEquipmentID(),
                    "clean",
                    "Medical Equipment",
                    "marked as"));
      } else {
        toEdit.setIsClean(false);
        DatabaseController.getInstance()
            .add(
                new Activity(
                    new Date(),
                    App.currentUser.getEmployeeID(),
                    toEdit.getEquipmentID(),
                    "dirty",
                    "Medical Equipment",
                    "marked as"));
      }
      edao.update(toEdit);
      AlertController.getInstance().checkForAlerts();
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

  public void startAStar() {
    aStarEnabled = true;
    addButton.setVisible(false);
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    resetButton.setVisible(false);
    backButton.setVisible(true);
    filterButton.setDisable(true);
    clearPathButton.setVisible(true);
    floorBackground.setVisible(true);
    locationDropdown.setVisible(true);
    locationDropdown2.setVisible(true);
    confirmButton.setVisible(true);
    populateAllLocationDropdown();
  }

  public void showAStar() {
    if (locationDropdown.getValue() != null && locationDropdown2.getValue() != null) {
      String startName = locationDropdown.getValue();
      String endName = locationDropdown2.getValue();

      LinkedList<Location> startLoc = dao.listByAttribute("longName", startName);
      LinkedList<Location> endLoc = dao.listByAttribute("longName", endName);

      if (!startLoc.isEmpty() && !endLoc.isEmpty()) {
        calculatePath(startLoc.pop(), endLoc.pop());
      }
      drawPathFloor(floorString);
      endAStar();
    }
  }

  public void endAStar() {
    aStarEnabled = false;
    addButton.setVisible(true);
    editButton.setVisible(true);
    deleteButton.setVisible(true);
    resetButton.setVisible(true);
    backButton.setVisible(false);
    filterButton.setDisable(false);
    clearPathButton.setVisible(false);
    floorBackground.setVisible(false);
    locationDropdown.setVisible(false);
    locationDropdown2.setVisible(false);
    confirmButton.setVisible(false);
  }

  public void clearPaths() {
    path = new ArrayList<Location>();
    cancel();
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
      floorBackground.setVisible(false);
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
      bath.setTextFill(Color.rgb(233, 28, 35));
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
      dept.setTextFill(Color.rgb(237, 89, 41));
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
      dirt.setTextFill(Color.rgb(247, 148, 29));
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
      elev.setTextFill(Color.rgb(247, 176, 62));
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
      exit.setTextFill(Color.rgb(251, 240, 2));
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
      hall.setTextFill(Color.rgb(140, 199, 59));
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
      info.setTextFill(Color.rgb(59, 180, 74));
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
      labs.setTextFill(Color.rgb(40, 167, 158));
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
      pati.setTextFill(Color.rgb(60, 173, 241));
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
      rest.setTextFill(Color.rgb(43, 115, 191));
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
      retl.setTextFill(Color.rgb(101, 43, 145));
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
      serv.setTextFill(Color.rgb(146, 37, 144));
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
      stai.setTextFill(Color.rgb(255, 99, 167));
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
      stor.setTextFill(Color.rgb(169, 100, 0));
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
      if (!requestFilterList.contains("Patient Transfer"))
        requestFilterList.add("Patient Transfer");
      ipt.setTextFill(Color.DARKGREY);
      iptOn = false;
    } else {
      ipt.setTextFill(Color.WHITE);
      requestFilterList.remove("Patient Transfer");
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
      LinkedList<Location> findLoc = dao.listByAttribute("longName", locationDropdown.getValue());
      if (!findLoc.isEmpty()) {
        Location currentLoc = findLoc.pop();
        currentLoc.setXCoord(startingCoordinates[0]);
        currentLoc.setYCoord(startingCoordinates[1]);
        dao.update(currentLoc);
        setRoomIcons();
        firstMove = true;
      }
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

  public void view() {
    switch (floorLevel) {
      case 0:
        goToFloorL2();
        break;
      case 1:
        goToFloorL1();
        break;
      case 2:
        goToFloor1();
        break;
      case 3:
        goToFloor2();
        break;
      case 4:
        goToFloor3();
        break;
      case 5:
        goToFloor4();
        break;
      case 6:
        goToFloor5();
        break;
    }
  }
}
