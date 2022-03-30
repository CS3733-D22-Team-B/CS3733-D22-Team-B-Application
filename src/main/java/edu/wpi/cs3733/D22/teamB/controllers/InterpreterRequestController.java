package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InterpreterRequestController extends MenuBarController {
  @FXML ComboBox<String> floorNumber;
  @FXML ComboBox<String> roomNumber;
  @FXML Label outputLabel;
  @FXML ComboBox<String> languageField;
  @FXML TextField nameInput;

  private String room = "";
  private String floor = "";
  private String language = "";
  private String name = "";
  private LinkedList<String> roomsF3 = new LinkedList<>();
  private LinkedList<String> roomsF2 = new LinkedList<>();
  private LinkedList<String> roomsF1 = new LinkedList<>();
  private LinkedList<String> roomsFL1 = new LinkedList<>();
  private LinkedList<String> roomsFL2 = new LinkedList<>();

  private LocationsDAO dao;

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
  void setFloor() {
    floor = floorNumber.getValue().toString();
    resetRoomBox();
  }

  @FXML
  void resetRoomBox() {
    roomNumber.getItems().clear();
    switch (floor) {
      case "F3":
        for (String room : roomsF3) {
          roomNumber.getItems().add(room);
        }
        break;
      case "F2":
        for (String room : roomsF2) {
          roomNumber.getItems().add(room);
        }
        break;
      case "F1":
        for (String room : roomsF1) {
          roomNumber.getItems().add(room);
        }
        break;
      case "L1":
        for (String room : roomsFL1) {
          roomNumber.getItems().add(room);
        }
        break;
      case "L2":
        for (String room : roomsFL2) {
          roomNumber.getItems().add(room);
        }
        break;
    }
  }

  @FXML
  void setRoom() {
    room = roomNumber.getValue().toString();
  }

  @FXML
  void setLanguage() {
    language = languageField.getValue().toString();
  }

  @FXML
  void setName() {
    name = nameInput.getText();
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setName();
    if (room.equals("") && language.equals("") && name.equals("")) {
      outputLabel.setText("Fields are empty, please specify room, language, and name of requester");
    } else if (room.equals("")) {
      outputLabel.setText("Please specify a room");
    } else if (name.equals("")) {
      outputLabel.setText("Please input name of requester");
    } else if (language.equals("")) {
      outputLabel.setText("Please select language to request interpreter for");
    } else {
      String locationID = dao.getLocationID(room);
      InterpreterRequest request = new InterpreterRequest(name, language, locationID);

      outputLabel.setText("Request sent: " + language + "interpreter to " + room + " by " + name);
    }
    // TODO: request list
    reset();
  }

  @FXML
  public void reset() {
    floorNumber.setValue("");
    roomNumber.setValue("");
    languageField.setValue("");
    nameInput.setText("");
    outputLabel.setText("");
    room = "";
    floor = "";
    language = "";
    name = "";
  }
}
