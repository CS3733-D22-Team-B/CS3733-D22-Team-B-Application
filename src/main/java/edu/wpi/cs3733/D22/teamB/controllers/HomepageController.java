package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomepageController {
  @FXML
  void goToEquipmentDelivery(ActionEvent event) throws Exception {
    Parent tutorialRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/equipmentRequest.fxml"));
    Scene tutorialScene = new Scene(tutorialRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(tutorialScene);
    window.show();
  }

  public void goToLabService(ActionEvent event) throws Exception {
    Parent labRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/labRequest.fxml"));
    Scene labScene = new Scene(labRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(labScene);
    window.show();
  }

  public void goToMedicineDelivery(ActionEvent event) throws Exception {
    Parent medicineRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/medicineRequest.fxml"));
    Scene medicineScene = new Scene(medicineRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(medicineScene);
    window.show();
  }

  public void goToMealDelivery(ActionEvent event) throws Exception {
    Parent mealRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/mealRequest.fxml"));
    Scene mealScene = new Scene(mealRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(mealScene);
    window.show();
  }

  public void goToInterpreterPage(ActionEvent event) throws Exception {
    Parent interpreterRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/interpreterRequest.fxml"));
    Scene interpreterScene = new Scene(interpreterRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(interpreterScene);
    window.show();
  }

  @FXML
  void goToInternalPatient(ActionEvent event) throws Exception {
    Parent internalRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/internalPatient.fxml"));
    Scene internalScene = new Scene(internalRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(internalScene);
    window.show();
  }
}
