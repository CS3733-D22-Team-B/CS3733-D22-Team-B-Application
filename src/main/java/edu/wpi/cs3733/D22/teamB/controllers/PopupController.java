package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.UIController;
import javafx.fxml.FXML;

public class PopupController {

  public void initialize() {
    System.out.println(
        "Popup controller launched!"); // never reach this... so the controller is not launching???
  }

  @FXML
  void setDashBoard() throws Exception {
    UIController.getInstance().goToPage("dashboard");
  }
}
