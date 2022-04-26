package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.UIController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

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
  @FXML private JFXButton giftRequestButton;
  @FXML private JFXButton laundryRequestButton;
  @FXML private JFXButton sanitationRequestButton;
  @FXML private JFXButton securityRequestButton;

  private JFXButton currentButton;
  private String currentPage = "none";

  public void initialize() {
    hideOthers(blankPane);
  }

  public void goToHelpPage() throws Exception {
    switch (currentPage) {
      case "Custom":
        UIController.getInstance().goToPage("CustomHelpPage");
      case "Equipment":
        UIController.getInstance().goToPage("EquipmentHelpPage");
      case "Security":
        UIController.getInstance().goToPage("SecurityHelpPage");
      case "Laundry":
        UIController.getInstance().goToPage("LaundryHelpPage");
      case "Sanitation":
        UIController.getInstance().goToPage("SanitationHelpPage");
      case "Gift":
        UIController.getInstance().goToPage("GiftHelpPage");
      case "InternalPatientTransfer":
        UIController.getInstance().goToPage("InternalPatientTransferHelpPage");
      case "Interpreter":
        UIController.getInstance().goToPage("InterpreterHelpPage");
      case "Meal":
        UIController.getInstance().goToPage("MealHelpPage");
      case "Medicine":
        UIController.getInstance().goToPage("MedicineHelpPage");
      case "LabRequest":
        UIController.getInstance().goToPage("LabRequestHelpPage");
    }
  }

  @FXML
  public void toggleCustomRequestPage(ActionEvent actionEvent) {
    customRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(customRequestPage);
    currentButton = customRequestButton;
    currentPage = "Custom";
  }

  @FXML
  public void toggleEquipmentRequestPage(ActionEvent actionEvent) throws IOException {
    equipmentRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(equipmentRequestPage);
    currentButton = equipmentRequestButton;
    currentPage = "Equipment";
  }

  @FXML
  public void toggleGiftPage() {
    giftRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(giftRequestPage);
    currentButton = giftRequestButton;
    currentPage = "Gift";
  }

  @FXML
  public void toggleInterpreterPage() {
    interpreterRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(interpreterRequestPage);
    currentButton = interpreterRequestButton;
    currentPage = "Interpreter";
  }

  @FXML
  public void toggleLabPage() {
    labRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(labRequestPage);
    currentButton = labRequestButton;
    currentPage = "LabRequest";
  }

  @FXML
  public void toggleMedicinePage() {
    medicineRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(medicineRequestPage);
    currentButton = medicineRequestButton;
    currentPage = "Medicine";
  }

  @FXML
  public void toggleTransferPage() {
    internalPatientTransferButton.getStyleClass().add("request-button-selected");
    hideOthers(transferRequestPage);
    currentButton = internalPatientTransferButton;
    currentPage = "InternalPatientTransfer";
  }

  @FXML
  public void toggleLaundryPage() {
    laundryRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(laundryRequestPage);
    currentButton = laundryRequestButton;
    currentPage = "Laundry";
  }

  @FXML
  public void toggleMealPage() {
    mealRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(mealRequestPage);
    currentButton = mealRequestButton;
    currentPage = "Meal";
  }

  @FXML
  public void toggleSanitationPage() {
    sanitationRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(sanitationPage);
    currentButton = sanitationRequestButton;
    currentPage = "Sanitation";
  }

  @FXML
  public void toggleSecurityPage() {
    securityRequestButton.getStyleClass().add("request-button-selected");
    hideOthers(securityRequestPage);
    currentButton = securityRequestButton;
    currentPage = "Security";
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
