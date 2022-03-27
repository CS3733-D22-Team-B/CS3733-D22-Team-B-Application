package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MedicineRequestController {
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

  @FXML
  void goToHomepage(ActionEvent event) throws Exception {
    Parent homepageRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
    Scene homepageScene = new Scene(homepageRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(homepageScene);
    window.show();
  }
}
