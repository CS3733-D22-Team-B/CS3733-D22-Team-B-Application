package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.databases.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

        Parent homepageRoot =
            FXMLLoader.load(
                getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
        Scene homepageScene = new Scene(homepageRoot);
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(homepageScene);
        window.show();
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
