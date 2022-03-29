package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class MenuBarController {
  @FXML private MenuBar homeBar;

  @FXML
  void goToHomepage(ActionEvent event) throws Exception {
    Parent homepageRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
    Scene homepageScene = new Scene(homepageRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(homepageScene);
    window.show();
  }

  @FXML
  public void goToEquipmentDeliveryRequestPage(ActionEvent event) throws Exception {
    Parent equipmentRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/equipmentRequestPage.fxml"));
    Scene equipmentScene = new Scene(equipmentRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(equipmentScene);
    window.show();
  }

  @FXML
  public void goToLabServiceRequestPage(ActionEvent event) throws Exception {
    Parent labRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/labRequestPage.fxml"));
    Scene labScene = new Scene(labRoot);
    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(labScene);
    window.show();
  }

  @FXML
  public void goToMedicineDeliveryRequestPage(ActionEvent event) throws Exception {
    Parent medicineRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/medicineRequestPage.fxml"));
    Scene medicineScene = new Scene(medicineRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(medicineScene);
    window.show();
  }

  @FXML
  public void goToMealDeliveryRequestPage(ActionEvent event) throws Exception {
    Parent mealRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/mealRequestPage.fxml"));
    Scene mealScene = new Scene(mealRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();

    window.setScene(mealScene);
    window.show();
  }

  @FXML
  public void goToInterpreterRequestPage(ActionEvent event) throws Exception {
    Parent interpreterRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/interpreterRequestPage.fxml"));
    Scene interpreterScene = new Scene(interpreterRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(interpreterScene);
    window.show();
  }

  @FXML
  void goToInternalPatientTransferRequestPage(ActionEvent event) throws Exception {
    Parent internalRoot =
        FXMLLoader.load(
            getClass()
                .getResource(
                    "/edu/wpi/cs3733/D22/teamB/views/internalPatientTransferRequestPage.fxml"));
    Scene internalScene = new Scene(internalRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(internalScene);
    window.show();
  }

  @FXML
  void goToMapViewPage(ActionEvent event) throws Exception {
    Parent internalRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/mapViewerPage.fxml"));
    Scene internalScene = new Scene(internalRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(internalScene);
    window.show();
  }

  @FXML
  void quitApplication() {
    System.exit(0);
  }
}