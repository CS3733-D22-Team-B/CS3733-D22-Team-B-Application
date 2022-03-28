package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EquipmentRequestController {
  @FXML private Label resultLabel;
  @FXML private ComboBox<String> roomComboBox;
  @FXML private ComboBox<String> equipmentComboBox;

  private String room = "";
  private String equipment = "";

  @FXML
  void setRoom() {
    room = roomComboBox.getValue();
  }

  @FXML
  void setEquipment() {
    equipment = equipmentComboBox.getValue();
  }

  @FXML
  void sendRequest() {
    if (room.equals("") && equipment.equals("")) {
      resultLabel.setText("Fields are empty, please specify room and equipment");
    } else if (room.equals("")) {
      resultLabel.setText("Please specify a room");
    } else if (equipment.equals("")) {
      resultLabel.setText("Please select equipment to request");
    } else resultLabel.setText("Request sent: " + equipment + " to " + room);
  }

  @FXML
  void reset() {
    roomComboBox.setValue("");
    equipmentComboBox.setValue("");
    resultLabel.setText("");
    room = "";
    equipment = "";
  }

  @FXML
  void goToHomepage(ActionEvent event) throws Exception {
    Parent homepageRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
    Scene homepageScene = new Scene(homepageRoot);
    Stage window = (Stage) resultLabel.getScene().getWindow();
    window.setScene(homepageScene);
    window.show();
  }
}
