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

public class InterpreterController {
  @FXML private Label outputLabel;
  @FXML private ChoiceBox languageField;

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
  public void submitLanguage(ActionEvent actionEvent) {
    String language = languageField.getSelectionModel().getSelectedItem().toString();
    outputLabel.setText("You have selected " + language);
  }
}
