package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.awt.*;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {
  @FXML private MenuBar homeBar;

  @FXML
  void goToHomepage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("homepage");
  }

  @FXML
  void goToMap(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("interactiveMapPage");
  }

  @FXML
  void goToMapLandingPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("mapLandingPage");
  }

  @FXML
  void goToEquipmentTrackerPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("equipmentTrackerPage");
  }

  @FXML
  void goToRequestLandingPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("requestLandingPage");
  }

  @FXML
  void goToRequestQueue(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("requestQueuePage");
  }

  @FXML
  void goToPatientDatabase(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("patientDatabasePage");
  }

  @FXML
  void goToEmployeeDatabase(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("employeeDatabasePage");
  }

  @FXML
  void goToLocationDataPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("locationDataPage");
  }

  @FXML
  void goToProfilePage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("profilePage");
  }

  @FXML
  void goToEquipmentRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("equipmentRequestPage");
  }

  @FXML
  void goToLabRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("labRequestPage");
  }

  @FXML
  void goToMealRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("mealRequestPage");
  }

  @FXML
  void goToMedicineRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("medicineRequestPage");
  }

  @FXML
  void goToInterpreterRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("interpreterRequestPage");
  }

  @FXML
  void goToInternalPatientTransferRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("internalPatientTransferRequestPage");
  }

  @FXML
  void goToCustomRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("customRequestPage");
  }

  @FXML
  void goToAboutPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("aboutPage");
  }

  @FXML
  void goToDashboard(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("dashboard");
  }

  @FXML
  void quitApplication() {
    System.exit(0);
  }

  @FXML
  void amongUsButton() {
    System.out.println("Among Us button clicked");

    // link to Among Us website
    try {
      Desktop.getDesktop().browse(new URL("http://wilsonwong.org").toURI());
    } catch (Exception e) {
      System.out.println("Error opening Among Us website");
    }
  }
}
