package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.requests.EquipmentRequest;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EquipmentRequestController extends MenuBarController {
  @FXML private Label resultLabel;
  @FXML private ComboBox<String> roomComboBox;
  @FXML private ComboBox<String> equipmentComboBox;
  @FXML private TextField nameInput;
  @FXML private ComboBox<String> floorComboBox;
  @FXML private TextArea additionalNotes;

  private String room = "";
  private String floor = "";
  private String equipment = "";
  private String name = "";
  private String notes = "";

  private LinkedList<String> roomsF3 = new LinkedList<>();
  private LinkedList<String> roomsF2 = new LinkedList<>();
  private LinkedList<String> roomsF1 = new LinkedList<>();
  private LinkedList<String> roomsFL1 = new LinkedList<>();
  private LinkedList<String> roomsFL2 = new LinkedList<>();

  private LocationsDAO dao;

  @FXML
  void setFloor() {
    floor = floorComboBox.getValue();
    resetRoomBox();
  }

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
  void resetRoomBox() {
    roomComboBox.getItems().clear();
    switch (floor) {
      case "F3":
        for (String room : roomsF3) {
          roomComboBox.getItems().add(room);
        }
        break;
      case "F2":
        for (String room : roomsF2) {
          roomComboBox.getItems().add(room);
        }
        break;
      case "F1":
        for (String room : roomsF1) {
          roomComboBox.getItems().add(room);
        }
        break;
      case "L1":
        for (String room : roomsFL1) {
          roomComboBox.getItems().add(room);
        }
        break;
      case "L2":
        for (String room : roomsFL2) {
          roomComboBox.getItems().add(room);
        }
        break;
    }
  }

  @FXML
  void setRoom() {
    room = roomComboBox.getValue();
  }

  @FXML
  void setEquipment() {
    equipment = equipmentComboBox.getValue();
  }

  @FXML
  void setName() {
    name = nameInput.getText();
  }

  @FXML
  void setNotes() {
    notes = (additionalNotes.getText() == null) ? "" : additionalNotes.getText();
  }

  @FXML
  void sendRequest() {
    setName();
    setNotes();
    if (room.equals("") && equipment.equals("") && name.equals("")) {
      resultLabel.setText(
          "Fields are empty, please specify room, equipment, and name of requester");
    } else if (room.equals("")) {
      resultLabel.setText("Please specify a room");
    } else if (name.equals("")) {
      resultLabel.setText("Please input name of requester");
    } else if (equipment.equals("")) {
      resultLabel.setText("Please select equipment to request");
    } else {
      String locationID = dao.getLocationID(room);
      EquipmentRequest request = new EquipmentRequest(equipment, name, locationID, notes);

      resultLabel.setText("Request sent: " + equipment + " to " + room + " by " + name);
    }
  }

  @FXML
  void reset() {
    roomComboBox.setValue("");
    equipmentComboBox.setValue("");
    floorComboBox.setValue("");
    resultLabel.setText("");
    nameInput.setText("");
    additionalNotes.setText("");
    room = "";
    equipment = "";
    name = "";
    floor = "";
    roomComboBox.getItems().clear();
  }
}
