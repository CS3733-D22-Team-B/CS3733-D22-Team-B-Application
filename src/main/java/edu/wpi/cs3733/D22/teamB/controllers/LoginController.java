package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordBox;
  @FXML private Label loginFail;
  @FXML private Button loginButton;

  private String username;
  private String password;

  public void setUsername() {
    this.username = usernameField.getText();
  }

  public void setPassword() {
    this.password = passwordBox.getText();
  }

  public void login() throws Exception {
    // Login functionality
    this.setPassword();
    this.setUsername();
    Employee employee = null;
    EmployeesDB emp = EmployeesDB.getInstance();
    if (emp.searchFor(this.username).size() == 1) {
      employee = emp.searchFor(this.username).get(0);
    }
    if (employee == null) {
      loginFail.setText("Invalid ID");
    } else {
      if (employee.getPassword().equals(password)) {
        App.currentUser = employee;

        UIController.getInstance().goToPage("homepage");
      } else {
        loginFail.setText("Invalid Password");
      }
    }
  }

  @FXML
  void quitApplication() {
    System.exit(0);
  }
}
