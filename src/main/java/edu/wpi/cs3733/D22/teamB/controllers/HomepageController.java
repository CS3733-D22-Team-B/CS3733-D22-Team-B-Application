package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomepageController extends MenuBarController {
  @FXML private Button logInButton;
  @FXML private Button profileButton;
  @FXML private Button mapButton;
  @FXML private Button equipmentTrackerButton;
  @FXML private Button requestButton;
  @FXML private Button equipmentRequestButton;
  @FXML private Button labRequestButton;
  @FXML private Button mealRequestButton;
  @FXML private Button medicineRequestButton;
  @FXML private Button interpreterRequestButton;
  @FXML private Button internalPatientTransferButton;
  @FXML private Button backButton;
  @FXML private Button patientDatabaseButton;

  @FXML
  void displayRequestButtons(ActionEvent event) {
    // set request buttons visible and hide all other buttons
    equipmentRequestButton.setVisible(true);
    labRequestButton.setVisible(true);
    mealRequestButton.setVisible(true);
    medicineRequestButton.setVisible(true);
    interpreterRequestButton.setVisible(true);
    internalPatientTransferButton.setVisible(true);
    backButton.setVisible(true);

    mapButton.setVisible(false);
    equipmentTrackerButton.setVisible(false);
    requestButton.setVisible(false);
    patientDatabaseButton.setVisible(false);

    // enable request buttons and disable all other buttons
    equipmentRequestButton.setDisable(false);
    labRequestButton.setDisable(false);
    mealRequestButton.setDisable(false);
    medicineRequestButton.setDisable(false);
    interpreterRequestButton.setDisable(false);
    internalPatientTransferButton.setDisable(false);
    backButton.setDisable(false);

    mapButton.setDisable(true);
    equipmentTrackerButton.setDisable(true);
    requestButton.setDisable(true);
    patientDatabaseButton.setDisable(true);
  }

  @FXML
  void backToHomepage(ActionEvent event) throws Exception {
    // set homepage buttons visible and hide all other buttons
    mapButton.setVisible(true);
    equipmentTrackerButton.setVisible(true);
    requestButton.setVisible(true);
    patientDatabaseButton.setVisible(true);

    equipmentRequestButton.setVisible(false);
    labRequestButton.setVisible(false);
    medicineRequestButton.setVisible(false);
    mealRequestButton.setVisible(false);
    interpreterRequestButton.setVisible(false);
    internalPatientTransferButton.setVisible(false);
    backButton.setVisible(false);

    // enable homepage buttons and disable all other buttons
    mapButton.setDisable(false);
    equipmentTrackerButton.setDisable(false);
    requestButton.setDisable(false);
    patientDatabaseButton.setDisable(true);

    equipmentRequestButton.setDisable(true);
    labRequestButton.setDisable(true);
    medicineRequestButton.setDisable(true);
    mealRequestButton.setDisable(true);
    interpreterRequestButton.setDisable(true);
    internalPatientTransferButton.setDisable(true);
    backButton.setDisable(true);
  }

  public void goToRequestQueue(ActionEvent event) throws Exception {
    Parent internalRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/requestStatusPage.fxml"));
    Scene internalScene = new Scene(internalRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(internalScene);
    window.show();
  }
}
