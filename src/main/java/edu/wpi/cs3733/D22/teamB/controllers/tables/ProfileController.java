package edu.wpi.cs3733.D22.teamB.controllers.tables;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeesDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController extends MenuBarController {

  @FXML private Label nameLabel;
  @FXML private Label positionLabel;
  @FXML private Label idLabel;
  @FXML private Label usernameLabel;
  @FXML private Label passwordLabel;
  @FXML private JFXButton viewPasswordButton;
  @FXML private JFXButton changePasswordButton;
  @FXML private Label changePasswordLabel;
  @FXML private TextField newPasswordField;
  @FXML private TextField confirmPasswordField;
  @FXML private Label errorLabel;

  private String password;
  private String hiddenPassword;

  @FXML
  public void initialize() {
    Employee currentUser = App.currentUser;

    nameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
    positionLabel.setText(currentUser.getPosition());
    idLabel.setText(currentUser.getEmployeeID());
    usernameLabel.setText("Username: " + currentUser.getUsername());

    password = currentUser.getPassword();

    hiddenPassword = "";
    for (int i = 0; i < password.length(); i++) {
      hiddenPassword += "\u2022";
    }

    passwordLabel.setText("Password: " + hiddenPassword);
  }

  public void togglePassword(ActionEvent actionEvent) {
    if (viewPasswordButton.getText().equals("View Password")) {
      togglePassword(true);
      viewPasswordButton.setText("Hide Password");
    } else {
      togglePassword(false);
      viewPasswordButton.setText("View Password");
    }
  }

  public void openChangePasswordDisplay(ActionEvent actionEvent) {
    if (changePasswordButton.isVisible()) {
      toggleChangePasswordDisplay(false);
    } else {
      toggleChangePasswordDisplay(true);
    }
  }

  public void changePassword(ActionEvent actionEvent) {
    String newPassword = newPasswordField.getText();
    String confirmPassword = confirmPasswordField.getText();

    if (newPassword.equals(password) || confirmPassword.equals(password)) {
      errorLabel.setText("New password cannot be the same as the old password.");
    } else if (!newPassword.equals(confirmPassword)) {
      errorLabel.setText("Passwords do not match");
    } else {
      App.currentUser.setPassword(newPassword);
      EmployeesDB.getInstance().update(App.currentUser);
      password = newPassword;
      hiddenPassword = "";
      for (int i = 0; i < password.length(); i++) {
        hiddenPassword += "\u2022";
      }
      if (viewPasswordButton.getText().equals("View Password")) {
        togglePassword(false);
      } else {
        togglePassword(true);
      }
      toggleChangePasswordDisplay(false);

      newPasswordField.setText("");
      confirmPasswordField.setText("");
      errorLabel.setText("");
    }
  }

  private void togglePassword(boolean visible) {
    if (visible) {
      passwordLabel.setText("Password: " + password);
    } else {
      passwordLabel.setText("Password: " + hiddenPassword);
    }
  }

  private void toggleChangePasswordDisplay(boolean visible) {
    changePasswordButton.setVisible(visible);
    changePasswordLabel.setVisible(visible);
    newPasswordField.setVisible(visible);
    confirmPasswordField.setVisible(visible);
    errorLabel.setVisible(visible);
  }
}
