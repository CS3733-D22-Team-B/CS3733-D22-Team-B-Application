package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class InterpreterRequestController extends MenuBarController {
  @FXML private Label outputLabel;
  @FXML private ChoiceBox languageField;

  @FXML
  public void submitLanguage(ActionEvent actionEvent) {
    String language = languageField.getSelectionModel().getSelectedItem().toString();
    outputLabel.setText("You have selected " + language);
  }
}
