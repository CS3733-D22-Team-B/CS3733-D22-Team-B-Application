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
  public void goToEquipmentDelivery(ActionEvent event) throws Exception {
    Parent equipmentRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/equipmentRequest.fxml"));
    Scene equipmentScene = new Scene(equipmentRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(equipmentScene);
    window.show();
  }

  @FXML
  public void goToLabService(ActionEvent event) throws Exception {
    Parent labRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/labRequest.fxml"));
    Scene labScene = new Scene(labRoot);
    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(labScene);
    window.show();
  }

  @FXML
  public void goToMedicineDelivery(ActionEvent event) throws Exception {
    Parent medicineRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/medicineRequest.fxml"));
    Scene medicineScene = new Scene(medicineRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(medicineScene);
    window.show();
  }

  @FXML
  public void goToMealDelivery(ActionEvent event) throws Exception {
    Parent mealRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/mealRequest.fxml"));
    Scene mealScene = new Scene(mealRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();

    window.setScene(mealScene);
    window.show();
  }

  @FXML
  public void goToInterpreterPage(ActionEvent event) throws Exception {
    Parent interpreterRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/interpreterRequest.fxml"));
    Scene interpreterScene = new Scene(interpreterRoot);

    Stage window;
    window = (Stage) homeBar.getScene().getWindow();
    window.setScene(interpreterScene);
    window.show();
  }

  @FXML
  void goToInternalPatient(ActionEvent event) throws Exception {
    Parent internalRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/internalPatient.fxml"));
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
