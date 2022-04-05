package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeesDB;
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
    EmployeesDB emp = EmployeesDB.getInstance();
    System.out.println(this.username + " " + this.password);
    Employee employee = emp.getEmployee(this.username);
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
}
