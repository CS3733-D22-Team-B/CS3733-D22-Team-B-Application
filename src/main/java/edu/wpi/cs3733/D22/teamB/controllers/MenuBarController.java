package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
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
  void goToMap(ActionEvent event) throws Exception {
    Parent mapRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/mapPage.fxml"));
    Scene mapScene = new Scene(mapRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(mapScene);
    window.show();
  }

  @FXML
  void goToEquipmentTrackerPage(ActionEvent event) throws Exception {
    Parent equipmentTrackerRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/equipmentTrackerPage.fxml"));
    Scene equipmentTrackerScene = new Scene(equipmentTrackerRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(equipmentTrackerScene);
    window.show();
  }

  @FXML
  void goToRequestLandingPage(ActionEvent event) throws Exception {
    Parent requestLandingRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/requestLandingPage.fxml"));
    Scene requestLandingScene = new Scene(requestLandingRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(requestLandingScene);
    window.show();
  }

  @FXML
  void goToRequestQueue(ActionEvent event) throws Exception {
    Parent requestQueueRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/requestQueuePage.fxml"));
    Scene requestQueueScene = new Scene(requestQueueRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(requestQueueScene);
    window.show();
  }

  @FXML
  void goToPatientDatabase(ActionEvent event) throws Exception {
    Parent patientDatabaseRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/patientDatabasePage.fxml"));
    Scene patientDatabaseScene = new Scene(patientDatabaseRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(patientDatabaseScene);
    window.show();
  }

  @FXML
  void goToEmployeeDatabase(ActionEvent event) throws Exception {
    Parent employeeDatabaseRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/employeeDatabasePage.fxml"));
    Scene employeeDatabaseScene = new Scene(employeeDatabaseRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(employeeDatabaseScene);
    window.show();
  }

  @FXML
  void goToProfilePage(ActionEvent event) throws Exception {
    Parent profileRoot =
        FXMLLoader.load(
            getClass()
                .getResource("src/main/resources/edu/wpi/cs3733/D22/teamB/views/profilePage.fxml"));
    Scene profileScene = new Scene(profileRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(profileScene);
    window.show();
  }

  @FXML
  void goToEquipmentRequestPage(ActionEvent event) throws Exception {
    Parent equipmentRequestRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/equipmentRequestPage.fxml"));
    Scene equipmentRequestScene = new Scene(equipmentRequestRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(equipmentRequestScene);
    window.show();
  }

  @FXML
  void goToLabRequestPage(ActionEvent event) throws Exception {
    Parent labRequestRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/labRequestPage.fxml"));
    Scene labRequestScene = new Scene(labRequestRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(labRequestScene);
    window.show();
  }

  @FXML
  void goToMealRequestPage(ActionEvent event) throws Exception {
    Parent mealRequestRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/mealRequestPage.fxml"));
    Scene mealRequestScene = new Scene(mealRequestRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(mealRequestScene);
    window.show();
  }

  @FXML
  void goToMedicineRequestPage(ActionEvent event) throws Exception {
    Parent medicineRequestRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/medicineRequestPage.fxml"));
    Scene medicineRequestScene = new Scene(medicineRequestRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(medicineRequestScene);
    window.show();
  }

  @FXML
  void goToInterpreterRequestPage(ActionEvent event) throws Exception {
    Parent interpreterRequestRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/interpreterRequestPage.fxml"));
    Scene interpreterRequestScene = new Scene(interpreterRequestRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(interpreterRequestScene);
    window.show();
  }

  @FXML
  void goToInternalPatientTransferRequestPage(ActionEvent event) throws Exception {
    Parent internalPatientTransferRoot =
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamB/views/internalPatientTransferPage.fxml"));
    Scene internalPatientTransferScene = new Scene(internalPatientTransferRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(internalPatientTransferScene);
    window.show();
  }

  @FXML
  void goToCustomRequestPage(ActionEvent event) throws Exception {
    Parent customRequestRoot =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/customRequestPage.fxml"));
    Scene customRequestScene = new Scene(customRequestRoot);
    Stage window = (Stage) homeBar.getScene().getWindow();
    window.setScene(customRequestScene);
    window.show();
  }

  @FXML
  void quitApplication() {
    LocationsDB locDB = LocationsDB.getInstance();
    MedicalEquipmentDB medEqDB = MedicalEquipmentDB.getInstance();
    PatientsDB patDB = PatientsDB.getInstance();
    EquipmentRequestDB eqReqDB = EquipmentRequestDB.getInstance();
    EmployeesDB empDB = EmployeesDB.getInstance();
    LabRequestsDB labReqDB = LabRequestsDB.getInstance();
    ServiceRequestsDB serReqDB = ServiceRequestsDB.getInstance();

    serReqDB.quit();
    labReqDB.quit();
    eqReqDB.quit();
    patDB.quit();
    medEqDB.quit();
    locDB.quit();
    empDB.quit();

    System.exit(0);
  }
}
