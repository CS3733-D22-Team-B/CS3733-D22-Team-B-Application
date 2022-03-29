package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.InterpreterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class InterpreterRequestController extends MenuBarController {
  @FXML ChoiceBox floorNumber;
  @FXML ChoiceBox roomNumber;
  @FXML private Label outputLabel;
  @FXML private ChoiceBox languageField;

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String language = languageField.getSelectionModel().getSelectedItem().toString();
    String room = roomNumber.getSelectionModel().getSelectedItem().toString();
    String floor = floorNumber.getSelectionModel().getSelectedItem().toString();
    outputLabel.setText("You have selected " + language);
    InterpreterRequest ir = new InterpreterRequest(language, room, floor);
    // TODO: request list
  }

  @FXML
  public void reset() {
    floorNumber.setValue("");
    roomNumber.setValue("");
    languageField.setValue("");
    outputLabel.setText("");
  }
}
