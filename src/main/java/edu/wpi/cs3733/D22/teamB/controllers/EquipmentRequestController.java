package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EquipmentRequestController extends MenuBarController {
  @FXML private Label resultLabel;
  @FXML private ComboBox<String> roomComboBox;
  @FXML private ComboBox<String> equipmentComboBox;
  @FXML private TextField nameInput;

  private String room = "";
  private String equipment = "";
  private String name = "";

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
    resultLabel.setText("");
    nameInput.setText("");
    room = "";
    equipment = "";
    name = "";
  }
}
