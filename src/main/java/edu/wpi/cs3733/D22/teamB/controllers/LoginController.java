package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends MenuBarController {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordBox;
  @FXML private Button loginButton;

  private String username;
  private String password;

  public void setUsername() {
    username = usernameField.getText();
  }

  public void setPassword() {
    password = passwordBox.getText();
  }

  public void login() {
    // Login functionality
  }
}
