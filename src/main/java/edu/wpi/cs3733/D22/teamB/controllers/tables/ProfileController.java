package edu.wpi.cs3733.D22.teamB.controllers.tables;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeesDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProfileController extends MenuBarController {

  @FXML private Label nameLabel;
  @FXML private Label positionLabel;
  @FXML private Label idLabel;
  @FXML private Label usernameLabel;
  @FXML private Label passwordLabel;
  @FXML private JFXButton changePasswordButton;
  @FXML private TextField newPasswordField;
  @FXML private TextField confirmPasswordField;
  @FXML private Label errorLabel;
  @FXML private AnchorPane changePasswordPane;

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

    // passwordLabel.setText("Password: " + hiddenPassword);

    newPasswordField
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableSubmission();
              }
            });

    confirmPasswordField
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableSubmission();
              }
            });
  }

  public void enableSubmission() {
    if (!newPasswordField.getText().equals("") && !confirmPasswordField.getText().equals("")) {
      changePasswordButton.setDisable(false);
    }
  }

  public void openChangePasswordDisplay(ActionEvent actionEvent) {
    if (changePasswordPane.isVisible()) {
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
      // passwordLabel.setText("Password: " + hiddenPassword);
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
    changePasswordPane.setVisible(visible);
  }
}
