package edu.wpi.cs3733.D22.teamB.controllers;

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

  @FXML
  void setFloor() {
    floor = floorComboBox.getValue();
    resetRoomBox();
  }

  @FXML
  void resetRoomBox() {
    roomComboBox.getItems().clear();
    switch (floor) {
      case "F3":
        // Set roomComboBox to list of Floor 3 Rooms
        roomComboBox.getItems().addAll("Floor 3: R1");
        break;
      case "F2":
        // Set roomComboBox to list of Floor 2 Rooms
        break;
      case "F1":
        // Set roomComboBox to list of Floor 1 Rooms
        break;
      case "L1":
        // Set roomComboBox to list of Floor L1 Rooms
        break;
      case "L2":
        // Set roomComboBox to list of Floor L2 Rooms
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
  void sendRequest() {
    setName();
    if (room.equals("") && equipment.equals("") && name.equals("")) {
      resultLabel.setText(
          "Fields are empty, please specify room, equipment, and name of requester");
    } else if (room.equals("")) {
      resultLabel.setText("Please specify a room");
    } else if (name.equals("")) {
      resultLabel.setText("Please input name of requester");
    } else if (equipment.equals("")) {
      resultLabel.setText("Please select equipment to request");
    } else resultLabel.setText("Request sent: " + equipment + " to " + room + " by " + name);
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
