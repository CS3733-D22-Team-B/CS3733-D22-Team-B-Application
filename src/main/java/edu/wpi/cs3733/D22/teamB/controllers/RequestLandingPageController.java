package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class RequestLandingPageController extends MenuBarController {
  @FXML private JFXButton equipmentRequestButton;
  @FXML private JFXButton labRequestButton;
  @FXML private JFXButton mealRequestButton;
  @FXML private JFXButton medicineRequestButton;
  @FXML private JFXButton interpreterRequestButton;
  @FXML private JFXButton internalPatientTransferButton;
  @FXML private JFXButton customRequestButton;
  @FXML private AnchorPane uglyNamePane;

  @FXML
  public void toggleUglyNames(ActionEvent actionEvent) {
    if (uglyNamePane.isVisible()) {
      uglyNamePane.setVisible(false);
    } else {
      uglyNamePane.setVisible(true);
    }
  }
}
