package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeeDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends MenuBarController {
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

  public void login() {
    // Login functionality
    this.setPassword();
    this.setUsername();
    EmployeeDB emp = EmployeeDB.getInstance();
    System.out.println(this.username + " " + this.password);
    Employee employee = emp.getEmployee(this.username);
    loginFail.setText(employee.getEmployeeID());

    if (employee.getPassword().equals(password)) {
      // go to homepage
      loginFail.setText("Go home");
    } else {
      loginFail.setText("Invalid login");
    }
  }
}
