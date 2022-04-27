package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.api.API;
import edu.wpi.cs3733.D22.teamB.api.ServiceException;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamD.API.StartAPI;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {
  @FXML private MenuBar homeBar;

  @FXML
  public void goToHomepage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("homepage");
  }

  @FXML
  public void goToRequestLanding(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("requestLandingPage");
  }

  @FXML
  public void goToMap(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("interactiveMapPage");
  }

  @FXML
  public void goToMapLandingPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("mapLandingPage");
  }

  @FXML
  public void goToEquipmentTrackerPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("equipmentTrackerPage");
  }

  @FXML
  public void goToRequestLandingPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("requestLandingPage");
  }

  @FXML
  public void goToRequestQueue(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("requestQueuePage");
  }

  @FXML
  public void goToPatientDatabase(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("patientDatabasePage");
  }

  @FXML
  public void goToEmployeeDatabase(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("employeeDatabasePage");
  }

  @FXML
  public void goToLocationDataPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("locationDataPage");
  }

  @FXML
  public void goToProfilePage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("profilePage");
  }

  @FXML
  public void goToEquipmentRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("equipmentRequestPage");
  }

  @FXML
  public void goToLabRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("labRequestPage");
  }

  @FXML
  public void goToMealRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("mealRequestPage");
  }

  @FXML
  public void goToMedicineRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("medicineRequestPage");
  }

  @FXML
  public void goToInterpreterRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("interpreterRequestPage");
  }

  @FXML
  public void goToInternalPatientTransferRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("internalPatientTransferRequestPage");
  }

  @FXML
  public void goToCustomRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("customRequestPage");
  }

  @FXML
  public void goToLaundryRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("laundryRequestPage");
  }

  @FXML
  public void goToSecurityRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("securityRequestPage");
  }

  @FXML
  public void goToGiftDeliveryRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("giftRequestPage");
  }

  @FXML
  public void goToSanitationRequestPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("sanitationRequestPage");
  }

  @FXML
  public void goToAboutPage(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("aboutPage");
  }

  @FXML
  public void goToDashboard(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("dashboard");
  }

  @FXML
  public void goToGame(ActionEvent event) throws Exception {
    UIController.getInstance().goToPage("sample");
  }

  @FXML
  public void quitApplication() {
    System.exit(0);
  }

  @FXML
  public void amongUsButton() {
    System.out.println("Among Us button clicked");

    // link to Among Us website
    try {
      Desktop.getDesktop().browse(new URL("http://wilsonwong.org").toURI());
    } catch (Exception e) {
      System.out.println("Error opening Among Us website");
    }
  }

  public void goToCovidPage(ActionEvent actionEvent) throws Exception {
    UIController.getInstance().goToPage("COVID-19Page");
  }

  @FXML
  public void launchAPI() throws ServiceException, IOException {
    try {
      API api = new API();
      // need to make an instance of the database here and add an employee
      api.run(0, 0, 600, 400, null, null, null);
    } catch (Exception e) {
    }
  }

  @FXML
  public void launchTeamCAPI()
      throws
          edu.wpi
              .cs3733
              .D22
              .teamC
              .controller
              .service_request
              .facility_maintenance
              .ServiceException {
    TeamCAPI api = new TeamCAPI();
    api.run(0, 0, 600, 400, null, null, null);
  }

  @FXML
  public void launchTeamDAPI() {
    StartAPI api = new StartAPI();

    try {
      api.run(0, 0, 600, 400, null, null);
    } catch (Exception e) {
      System.out.println("BRUH");
    }
  }

  public void goToDatabaseLandingPage(ActionEvent actionEvent) throws Exception {
    UIController.getInstance().goToPage("databaseLandingPage");
  }

  public void goToRequestInfoPage(ActionEvent actionEvent) throws Exception {
    UIController.getInstance().goToPage("requestsInfo");
  }

  public void goToAnalyticsPage(ActionEvent actionEvent) throws Exception {
    UIController.getInstance().goToPage("analyticsPage");
  }
}
