package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.UIController;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ProfileController extends MenuBarController implements Initializable {

  @FXML TableView requestTable;
  @FXML TableColumn<Request, String> requestID;
  @FXML TableColumn<Request, String> requestType;
  @FXML TableColumn<Request, String> requestStatus;
  @FXML TableColumn<Request, String> requestPriority;
  TableColumn<Request, Void> viewRequest;

  @FXML GridPane changePasswordPane;
  @FXML GridPane tablePane;
  @FXML GridPane colorThemePane;

  @FXML Label nameLabel;
  @FXML Label idLabel;
  @FXML Label positionLabel;
  @FXML Label usernameLabel;

  @FXML TextField newPasswordField;
  @FXML TextField confirmPasswordField;
  @FXML Label messageBox;

  @FXML Button changePasswordButton;
  @FXML Button modeButton;
  @FXML Button colorButton;

  private ObservableList<Request> requests = FXCollections.observableArrayList();

  private String password;

  private EmployeesDB employeesDAO;

  @FXML
  public void initialize(URL url, ResourceBundle resourceBundle) {
    employeesDAO = EmployeesDB.getInstance();

    Employee currentUser = App.currentUser;
    password = currentUser.getPassword();

    nameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
    positionLabel.setText(currentUser.getPosition());
    idLabel.setText(currentUser.getEmployeeID());
    usernameLabel.setText("Username: " + currentUser.getUsername());

    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    requestType.setCellValueFactory(new PropertyValueFactory<>("type"));
    requestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    requestPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));

    viewRequest = new TableColumn<>("View");
    requestTable.getColumns().add(viewRequest);

    requestID.getStyleClass().add("table-column-left");
    requestType.getStyleClass().add("table-column-middle");
    requestStatus.getStyleClass().add("table-column-middle");
    requestPriority.getStyleClass().add("table-column-middle");
    viewRequest.getStyleClass().add("table-column-right");

    for (Request request : ServiceRequestsDB.getInstance().list()) {
      if (request.getEmployeeID() != null
          && request.getEmployeeID().equals(App.currentUser.getEmployeeID())
          && !request.getStatus().equals("Completed")) requests.add(request);
    }

    sortRequestsByCreationDate(requests);

    requestTable.setItems(requests);
    addButtonToTable();

    if (App.currentUser.getLightOn()) {
      modeButton.getStyleClass().add("light-dark-button");
    } else {
      modeButton.getStyleClass().add("light-dark-button");
    }

    switch (App.currentUser.getColor()) {
      case "Blue":
        colorButton.getStyleClass().add("blue-button");
        break;
      case "Green":
        colorButton.getStyleClass().add("green-button");
        break;
      case "Red":
        colorButton.getStyleClass().add("red-button");
        break;
    }

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

  private void addButtonToTable() {
    Callback<TableColumn<Request, Void>, TableCell<Request, Void>> cellFactory =
        new Callback<TableColumn<Request, Void>, TableCell<Request, Void>>() {
          @Override
          public TableCell<Request, Void> call(final TableColumn<Request, Void> param) {
            final TableCell<Request, Void> cell =
                new TableCell<Request, Void>() {
                  private final Button requestViewerButton = new Button("View");

                  {
                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          // TODO: Implement request viewer
                        });
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(requestViewerButton);
                      requestViewerButton.getStyleClass().add("simple-button");
                    }
                  }
                };
            return cell;
          }
        };

    viewRequest.setCellFactory(cellFactory);
  }

  private void sortRequestsByCreationDate(ObservableList<Request> requests) {
    Collections.sort(
        requests, (Request r1, Request r2) -> r1.getTimeCreated().compareTo(r2.getTimeCreated()));
  }

  public void enableSubmission() {
    if (!newPasswordField.getText().equals("") && !confirmPasswordField.getText().equals("")) {
      changePasswordButton.setDisable(false);
    }
  }

  public void toggleChangePasswordPane(ActionEvent actionEvent) {
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
      messageBox.setText("New password cannot be the same as the old password.");
    } else if (!newPassword.equals(confirmPassword)) {
      messageBox.setText("Passwords do not match");
    } else {
      App.currentUser.setPassword(newPassword);
      EmployeesDB.getInstance().update(App.currentUser);
      password = newPassword;
      toggleChangePasswordDisplay(false);

      newPasswordField.setText("");
      confirmPasswordField.setText("");
      messageBox.setText("");
    }
  }

  private void toggleChangePasswordDisplay(boolean visible) {
    changePasswordPane.setVisible(visible);
    tablePane.setVisible(!visible);
  }

  public void toggleColorThemePane(ActionEvent actionEvent) {
    colorThemePane.setVisible(!colorThemePane.isVisible());
  }

  public void toggleLightMode(ActionEvent actionEvent) {
    if (tablePane.isVisible()) {
      App.currentUser.setLightOn(!App.currentUser.getLightOn());
      EmployeesDB.getInstance().update(App.currentUser);
      updateColorTheme();
    }
  }

  public void setColorThemeBlue(ActionEvent actionEvent) {
    setColorTheme("Blue");
  }

  public void setColorThemeGreen(ActionEvent actionEvent) {
    setColorTheme("Green");
  }

  public void setColorThemeRed(ActionEvent actionEvent) {
    setColorTheme("Red");
  }

  private void setColorTheme(String newColorTheme) {
    if (requestTable.isVisible()) {
      App.currentUser.setColor(newColorTheme);
      EmployeesDB.getInstance().update(App.currentUser);
      updateColorTheme();
    }
  }

  private void updateColorTheme() {
    modeButton.getStyleClass().clear();
    colorButton.getStyleClass().clear();

    toggleColorThemePane(null);

    try {
      UIController.getInstance().goToPage("profilePage");
    } catch (Exception e) {
    }
  }
}
