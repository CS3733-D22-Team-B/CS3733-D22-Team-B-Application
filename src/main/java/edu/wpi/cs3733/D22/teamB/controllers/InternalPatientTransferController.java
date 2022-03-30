package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.requests.InternalPatientTransferRequest;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InternalPatientTransferController extends MenuBarController {

  @FXML private ComboBox<String> floorBoxL;
  @FXML private ComboBox<String> roomBoxL;
  @FXML private ComboBox<String> floorBoxR;
  @FXML private ComboBox<String> roomBoxR;
  @FXML private TextField nameInput;
  @FXML private Label reqLabel;

  private String floorL = "";
  private String roomL = "";
  private String floorR = "";
  private String roomR = "";
  private String name = "";

  private LocationsDAO dao;
  private LinkedList<String> roomsF3 = new LinkedList<>();
  private LinkedList<String> roomsF2 = new LinkedList<>();
  private LinkedList<String> roomsF1 = new LinkedList<>();
  private LinkedList<String> roomsFL1 = new LinkedList<>();
  private LinkedList<String> roomsFL2 = new LinkedList<>();

  @FXML
  public void initialize() {
    dao = new LocationsDAO();
    LinkedList<Location> locations = dao.listLocations();

    for (Location location : locations) {
      switch (location.getFloor()) {
        case "3":
          roomsF3.add(location.getLongName());
          break;
        case "2":
          roomsF2.add(location.getLongName());
          break;
        case "1":
          roomsF1.add(location.getLongName());
          break;
        case "L1":
          roomsFL1.add(location.getLongName());
          break;
        case "L2":
          roomsFL2.add(location.getLongName());
          break;
      }
    }
  }

  @FXML
  void setFloorL() {
    floorL = floorBoxL.getValue();
    resetRoomL();
  }

  @FXML
  void resetRoomL() {
    roomBoxL.getItems().clear();
    switch (floorL) {
      case "F3":
        for (String room : roomsF3) {
          roomBoxL.getItems().add(room);
        }
        break;
      case "F2":
        for (String room : roomsF2) {
          roomBoxL.getItems().add(room);
        }
        break;
      case "F1":
        for (String room : roomsF1) {
          roomBoxL.getItems().add(room);
        }
        break;
      case "L1":
        for (String room : roomsFL1) {
          roomBoxL.getItems().add(room);
        }
        break;
      case "L2":
        for (String room : roomsFL2) {
          roomBoxL.getItems().add(room);
        }
        break;
    }
  }

  @FXML
  void setRoomL() {
    roomL = roomBoxL.getValue();
  }

  @FXML
  void setFloorR() {
    floorR = floorBoxR.getValue();
    resetRoomR();
  }

  @FXML
  void resetRoomR() {
    roomBoxR.getItems().clear();
    switch (floorR) {
      case "F3":
        for (String room : roomsF3) {
          roomBoxR.getItems().add(room);
        }
        break;
      case "F2":
        for (String room : roomsF2) {
          roomBoxR.getItems().add(room);
        }
        break;
      case "F1":
        for (String room : roomsF1) {
          roomBoxR.getItems().add(room);
        }
        break;
      case "L1":
        for (String room : roomsFL1) {
          roomBoxR.getItems().add(room);
        }
        break;
      case "L2":
        for (String room : roomsFL2) {
          roomBoxR.getItems().add(room);
        }
        break;
    }
  }

  @FXML
  void setRoomR() {
    roomR = roomBoxR.getValue();
  }

  @FXML
  void setName() {
    name = nameInput.getText();
  }

  @FXML
  void sendRequest() {
    String startID = dao.getLocationID(roomL);
    String endID = dao.getLocationID(roomR);
    InternalPatientTransferRequest request =
        new InternalPatientTransferRequest(name, startID, endID);
    reqLabel.setText("Request Sent!");
  }
}
