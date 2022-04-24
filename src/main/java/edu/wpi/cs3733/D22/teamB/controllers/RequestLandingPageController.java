package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class RequestLandingPageController extends MenuBarController {
  public AnchorPane customRequestPage;
  public AnchorPane equipmentRequestPage;
  public AnchorPane giftRequestPage;
  public AnchorPane interpreterRequestPage;
  public AnchorPane labRequestPage;
  public AnchorPane laundryRequestPage;
  public AnchorPane mealRequestPage;
  public AnchorPane medicineRequestPage;
  public AnchorPane transferRequestPage;
  public AnchorPane sanitationPage;
  public AnchorPane securityRequestPage;
  public AnchorPane blankPane;

  @FXML private JFXButton equipmentRequestButton;
  @FXML private JFXButton labRequestButton;
  @FXML private JFXButton mealRequestButton;
  @FXML private JFXButton medicineRequestButton;
  @FXML private JFXButton interpreterRequestButton;
  @FXML private JFXButton internalPatientTransferButton;
  @FXML private JFXButton customRequestButton;
  @FXML private GridPane uglyNamePane;

  private JFXButton currentButton;

  @FXML
  public void toggleUglyNames(ActionEvent actionEvent) {
    if (uglyNamePane.isVisible()) {
      uglyNamePane.setVisible(false);
    } else {
      uglyNamePane.setVisible(true);
    }
  }

  public void initialize() {
    hideOthers(blankPane);
  }

  @FXML
  public void toggleCustomRequestPage(ActionEvent actionEvent) {
    customRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(customRequestPage);
    currentButton = customRequestButton;
  }

  @FXML
  public void toggleEquipmentRequestPage(ActionEvent actionEvent) {
    equipmentRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(equipmentRequestPage);
    currentButton = equipmentRequestButton;
  }

  @FXML
  public void toggleGiftPage() {
    hideOthers(giftRequestPage);
  }

  @FXML
  public void toggleInterpreterPage() {
    hideOthers(interpreterRequestPage);
  }

  @FXML
  public void toggleLabPage() {
    hideOthers(labRequestPage);
  }

  @FXML
  public void toggleMedicinePage() {
    hideOthers(medicineRequestPage);
  }

  @FXML
  public void toggleTransferPage() {
    hideOthers(transferRequestPage);
  }

  @FXML
  public void toggleLaundryPage() {
    hideOthers(laundryRequestPage);
  }

  @FXML
  public void toggleMealPage() {
    hideOthers(mealRequestPage);
  }

  @FXML
  public void toggleSanitationPage() {
    hideOthers(sanitationPage);
  }

  @FXML
  public void toggleSecurityPage() {
    hideOthers(securityRequestPage);
  }

  @FXML
  private void hideOthers(AnchorPane current) {
    blankPane.setVisible(false);
    customRequestPage.setVisible(false);
    equipmentRequestPage.setVisible(false);
    giftRequestPage.setVisible(false);
    interpreterRequestPage.setVisible(false);
    labRequestPage.setVisible(false);
    medicineRequestPage.setVisible(false);
    transferRequestPage.setVisible(false);
    laundryRequestPage.setVisible(false);
    mealRequestPage.setVisible(false);
    sanitationPage.setVisible(false);
    securityRequestPage.setVisible(false);
    if (currentButton != null) currentButton.getStyleClass().remove("request-button-selected");
    current.setVisible(true);
  }
}
