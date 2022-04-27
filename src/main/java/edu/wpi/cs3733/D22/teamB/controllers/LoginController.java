package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.EmailHelper;
import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordBox;
  @FXML private Label loginFail;
  @FXML private Button loginButton;
  @FXML private Button loginButton2FA;
  @FXML private AnchorPane loginPane;
  @FXML private Label emailText;
  @FXML private TextField emailField;
  @FXML private Button emailButton;
  @FXML private Label authText;
  @FXML private TextField authField;
  @FXML private Button authButton;

  private String username;
  private String password;
  private String email;
  private String authCode;

  public void setUsername() {
    this.username = usernameField.getText();
  }

  public void setPassword() {
    this.password = passwordBox.getText();
  }

  public void setEmail() {
    this.email = emailField.getText();
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

  public void login2FA() throws Exception {
    // Login functionality
    loginFail.setVisible(false);
    this.setPassword();
    this.setUsername();
    Employee employee = null;
    EmployeesDB emp = EmployeesDB.getInstance();
    if (emp.searchFor(this.username).size() == 1) {
      employee = emp.searchFor(this.username).get(0);
    }
    if (employee == null) {
      loginFail.setText("Invalid ID");
      loginFail.setVisible(true);
    } else {
      if (employee.getPassword().equals(password)) {

        loginButton2FA.setDisable(true);
        loginButton2FA.setVisible(false);

        loginButton.setVisible(false);
        loginButton.setDisable(true);

        usernameField.setDisable(true);
        usernameField.setVisible(false);
        passwordBox.setDisable(true);
        passwordBox.setVisible(false);

        emailField.setDisable(false);
        emailField.setVisible(true);
        emailButton.setVisible(true);
        emailButton.setDisable(false);
        emailText.setVisible(true);

        authText.setVisible(false);

        Employee finalEmployee = employee;
        authButton.setOnAction(
            e -> {
              if (authCode.equals(authField.getText())) {
                authButton.setVisible(false);
                authButton.setDisable(true);
                authText.setText("Authentication Successful");
                authField.setVisible(false);
                authField.setDisable(true);
                loginButton.setVisible(true);
                loginButton.setDisable(false);
                loginFail.setVisible(false);
              } else {
                loginFail.setVisible(true);
                loginFail.setText("Incorrect Code");
              }
            });
      } else {
        loginFail.setText("Invalid Password");
        loginFail.setVisible(true);
      }
    }
  }

  @FXML
  void quitApplication() {
    System.exit(0);
  }

  public static String getRandomNumberString() {
    Random random = new Random();
    int num = random.nextInt(999999);

    return String.format("%06d", num);
  }

  @FXML
  void enterEmail() {
    authCode = getRandomNumberString();
    this.setEmail();

    authText.setVisible(true);
    authText.setText("Authentication code was sent to " + email);

    emailButton.setVisible(false);
    emailButton.setDisable(true);
    emailField.setDisable(true);
    emailField.setVisible(false);
    emailText.setVisible(false);

    authText.setVisible(true);
    authField.setVisible(true);
    authField.setDisable(false);
    authButton.setVisible(true);
    authButton.setDisable(false);

    EmailHelper.send(email, authCode);
  }
}
