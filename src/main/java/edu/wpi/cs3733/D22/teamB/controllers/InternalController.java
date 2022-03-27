package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InternalController {

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
  void goToHomepage(ActionEvent event) throws Exception {
    Parent homepageRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
    Scene homepageScene = new Scene(homepageRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(homepageScene);
    window.show();
  }

  @FXML
  void sendRequest() {
    reqLabel.setText("Request Sent!");
  }
}
