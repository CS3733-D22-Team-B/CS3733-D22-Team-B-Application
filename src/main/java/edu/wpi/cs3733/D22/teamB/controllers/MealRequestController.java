package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MealRequestController extends MenuBarController {
  @FXML private Label resultLabel;
  @FXML private ComboBox roomComboBox;
  @FXML private TextField mealNameTextField;

  private String room;
  private String mealName;

  @FXML
  void setRoom(ActionEvent event) {
    room = roomComboBox.getValue().toString();

    // TODO: set the room
  }

  @FXML
  void setMealName() {
    mealName = mealNameTextField.getText();
  }

  @FXML
  void sendRequest() {
    setMealName();
    resultLabel.setText("Meal request sent: " + mealName + " for " + room);
  }
}
