package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.databases.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileController extends MenuBarController {

  @FXML private Label nameDisplay;
  @FXML private Label employeeID;
  @FXML private Label positionDisplay;
  @FXML private Label usernameDisplay;
  @FXML private Label passwordDisplay;

  @FXML
  public void initialize() {
    Employee currentUser = App.currentUser;

    nameDisplay.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
    employeeID.setText(currentUser.getEmployeeID());
    positionDisplay.setText(currentUser.getPosition());
    usernameDisplay.setText(currentUser.getUsername());
    // passwordDisplay.setText(currentUser.getPassword());
  }
}
