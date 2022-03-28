package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MedicineRequestController extends MenuBarController {
  @FXML private Label resultLabel;
  @FXML private ComboBox roomComboBox;
  @FXML private ComboBox medicineComboBox;

  private String room;
  private String medicine;

  @FXML
  void setRoom(ActionEvent event) {
    String room = roomComboBox.getValue().toString();

    // TODO: set the room
  }

  @FXML
  void setMedication(ActionEvent event) {
    String videoSource = medicineComboBox.getValue().toString();
  }

  @FXML
  void sendRequest() {
    resultLabel.setText("Request sent!");
  }
}
