package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DatabaseLandingPageController extends MenuBarController {
  @FXML public AnchorPane employeeDatabasePage;
  @FXML public AnchorPane patientDatabasePage;
  @FXML public AnchorPane locationDatabasePage;
  @FXML public AnchorPane blankPane;
  @FXML public JFXButton locationButton;
  @FXML public JFXButton patientButton;
  @FXML public JFXButton employeeButton;
  private JFXButton currentButton;

  public void initialize() {
    hideOthers(blankPane);
  }

  @FXML
  public void togglePatients() {
    patientButton.getStyleClass().add("request-button-selected");
    hideOthers(patientDatabasePage);
    currentButton = patientButton;
  }

  @FXML
  public void toggleEmployees() {
    employeeButton.getStyleClass().add("request-button-selected");
    hideOthers(employeeDatabasePage);
    currentButton = employeeButton;
  }

  @FXML
  public void toggleLocations() {
    locationButton.getStyleClass().add("request-button-selected");
    hideOthers(locationDatabasePage);
    currentButton = locationButton;
  }

  @FXML
  private void hideOthers(AnchorPane current) {
    blankPane.setVisible(false);
    employeeDatabasePage.setVisible(false);
    patientDatabasePage.setVisible(false);
    locationDatabasePage.setVisible(false);
    if (currentButton != null) currentButton.getStyleClass().remove("request-button-selected");
    current.setVisible(true);
  }

  @FXML
  public void toggleClientServer() throws Exception {
    DatabaseController db = DatabaseController.getInstance();
    db.switchConnection();
    UIController.getInstance().goToPage("databaseLandingPage");
    // patientTable.refresh();
  }
}
