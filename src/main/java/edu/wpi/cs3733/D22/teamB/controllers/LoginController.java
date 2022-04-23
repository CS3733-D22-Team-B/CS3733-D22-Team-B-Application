package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.EmailHelper;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.io.IOException;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordBox;
  @FXML private Label loginFail;
  @FXML private Button loginButton;
  @FXML private AnchorPane loginPane;
  @FXML private Text emailText;
  @FXML private TextField emailField;
  @FXML private Button emailButton;
  @FXML private Text authText;
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
        emailText = new Text("Enter your email to validate your identity");
        emailText.relocate(500, 270);
        loginPane.getChildren().add(emailText);
        emailField = new TextField();
        emailField.relocate(500, 290);
        emailField.setPromptText("Email");
        loginPane.getChildren().add(emailField);
        emailButton = new Button();
        emailButton.relocate(700, 290);
        loginPane.getChildren().add(emailButton);

        authText = new Text();
        authText.relocate(500, 450);
        authText.setVisible(false);
        loginPane.getChildren().add(authText);
        authField = new TextField();
        authField.relocate(500, 470);
        authField.setVisible(false);
        loginPane.getChildren().add(authField);
        authButton = new Button();
        authButton.relocate(700, 470);
        authButton.setVisible(false);
        loginPane.getChildren().add(authButton);

        emailButton.setOnAction(
            e -> {
              authCode = getRandomNumberString();
              this.setEmail();
              EmailHelper.send(email, authCode);
              authText.setText("Authentication code was sent to " + email);
              authText.setVisible(true);
              authField.setVisible(true);
              authButton.setVisible(true);
            });

        Employee finalEmployee = employee;
        authButton.setOnAction(
            e -> {
              if (authCode.equals(authField.getText())) {
                App.currentUser = finalEmployee;

                Parent homepageRoot = null;
                try {
                  homepageRoot =
                      FXMLLoader.load(
                          getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
                Scene homepageScene = new Scene(homepageRoot);
                Stage window = (Stage) loginButton.getScene().getWindow();
                window.setScene(homepageScene);
                window.show();
              }
            });
      } else {
        loginFail.setText("Invalid Password");
      }
    }
  }

  @FXML
  void quitApplication() {
    System.exit(0);
  }

  public static String getRandomNumberString() {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    Random rnd = new Random();
    int number = rnd.nextInt(999999);

    // this will convert any number sequence into 6 character.
    return String.format("%06d", number);
  }
}
