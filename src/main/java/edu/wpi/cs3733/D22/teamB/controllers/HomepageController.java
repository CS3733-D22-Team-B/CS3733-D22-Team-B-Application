package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController extends MenuBarController {
  @FXML private Button profileButton;
  @FXML private Button mapButton;
  @FXML private Button mapEditorButton;
  @FXML private Button mapViewerButton;
  @FXML private Button mapDatabaseButton;
  @FXML private Button equipmentTrackerButton;
  @FXML private Button requestButton;
  @FXML private Button equipmentRequestButton;
  @FXML private Button labRequestButton;
  @FXML private Button mealRequestButton;
  @FXML private Button medicineRequestButton;
  @FXML private Button interpreterRequestButton;
  @FXML private Button internalPatientTransferButton;
  @FXML private Button patientDatabaseButton;
  @FXML private Button customRequestButton;
  @FXML private Button backButton;
  @FXML private Button requestQueueButton;

  @FXML
  void displayRequestButtons(ActionEvent event) {
    // set request buttons visible and hide all other buttons
    equipmentRequestButton.setVisible(true);
    labRequestButton.setVisible(true);
    mealRequestButton.setVisible(true);
    medicineRequestButton.setVisible(true);
    interpreterRequestButton.setVisible(true);
    internalPatientTransferButton.setVisible(true);
    customRequestButton.setVisible(true);
    requestQueueButton.setVisible(true);
    backButton.setVisible(true);

    mapButton.setVisible(false);
    mapEditorButton.setVisible(false);
    mapViewerButton.setVisible(false);
    mapDatabaseButton.setVisible(false);
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
    customRequestButton.setDisable(false);
    requestQueueButton.setDisable(false);
    backButton.setDisable(false);

    mapButton.setDisable(true);
    mapEditorButton.setDisable(true);
    mapViewerButton.setDisable(true);
    mapDatabaseButton.setDisable(true);
    equipmentTrackerButton.setDisable(true);
    requestButton.setDisable(true);
    patientDatabaseButton.setDisable(true);
  }

  @FXML
  void displayMapButtons(ActionEvent event) {
    // set map buttons visible and hide all other buttons
    mapEditorButton.setVisible(true);
    mapViewerButton.setVisible(true);
    mapDatabaseButton.setVisible(true);
    backButton.setVisible(true);

    equipmentRequestButton.setVisible(false);
    labRequestButton.setVisible(false);
    mealRequestButton.setVisible(false);
    medicineRequestButton.setVisible(false);
    interpreterRequestButton.setVisible(false);
    internalPatientTransferButton.setVisible(false);
    customRequestButton.setVisible(false);
    mapButton.setVisible(false);
    equipmentTrackerButton.setVisible(false);
    requestButton.setVisible(false);
    patientDatabaseButton.setVisible(false);

    // enable map buttons and disable all other buttons
    mapEditorButton.setDisable(false);
    mapViewerButton.setDisable(false);
    mapDatabaseButton.setDisable(false);
    backButton.setDisable(false);

    equipmentRequestButton.setDisable(true);
    labRequestButton.setDisable(true);
    mealRequestButton.setDisable(true);
    medicineRequestButton.setDisable(true);
    interpreterRequestButton.setDisable(true);
    internalPatientTransferButton.setDisable(true);
    customRequestButton.setDisable(true);
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

    mapEditorButton.setVisible(false);
    mapViewerButton.setVisible(false);
    mapDatabaseButton.setVisible(false);
    equipmentRequestButton.setVisible(false);
    labRequestButton.setVisible(false);
    medicineRequestButton.setVisible(false);
    mealRequestButton.setVisible(false);
    interpreterRequestButton.setVisible(false);
    internalPatientTransferButton.setVisible(false);
    customRequestButton.setVisible(false);
    requestQueueButton.setVisible(false);
    backButton.setVisible(false);

    // enable homepage buttons and disable all other buttons
    mapButton.setDisable(false);
    equipmentTrackerButton.setDisable(false);
    requestButton.setDisable(false);
    patientDatabaseButton.setDisable(true);

    mapEditorButton.setDisable(true);
    mapViewerButton.setDisable(true);
    mapDatabaseButton.setDisable(true);
    equipmentRequestButton.setDisable(true);
    labRequestButton.setDisable(true);
    medicineRequestButton.setDisable(true);
    mealRequestButton.setDisable(true);
    interpreterRequestButton.setDisable(true);
    internalPatientTransferButton.setDisable(true);
    customRequestButton.setDisable(true);
    requestQueueButton.setDisable(true);
    backButton.setDisable(true);
  }
}
