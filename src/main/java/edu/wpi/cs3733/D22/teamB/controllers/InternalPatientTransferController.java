package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class InternalPatientTransferController extends MenuBarController {

  @FXML private ChoiceBox startBox;
  @FXML private ChoiceBox endBox;
  @FXML private Label reqLabel;

  @FXML
  public void initialize() {
    startBox.getItems().add("Floor 1");
    startBox.getItems().add("Floor 2");
    startBox.getItems().add("Floor 3");

    endBox.getItems().add("Floor 1");
    endBox.getItems().add("Floor 2");
    endBox.getItems().add("Floor 3");
  }

  @FXML
  void sendRequest() {
    reqLabel.setText("Request Sent!");
  }
}
