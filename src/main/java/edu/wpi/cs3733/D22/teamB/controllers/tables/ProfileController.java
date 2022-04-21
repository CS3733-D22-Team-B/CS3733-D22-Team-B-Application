package edu.wpi.cs3733.D22.teamB.controllers.tables;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ProfileController extends MenuBarController implements Initializable {

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
  @FXML TableView<Request> requestTable;
  @FXML TableColumn<Request, String> columnRequestID;
  @FXML TableColumn<Request, String> columnType;
  @FXML TableColumn<Request, String> columnStatus;
  @FXML TableColumn<Request, Integer> columnPriority;
  @FXML TableColumn<Request, Void> columnButtons;

  @FXML ScrollPane scrollPane;
  @FXML Label requestIDLabel;
  @FXML Label createdLabel;
  @FXML Label lastEditedLabel;
  @FXML Label informationLabel;
  @FXML ComboBox<String> statusInput;

  private ObservableList<Request> requests = FXCollections.observableArrayList();
  Request currentRequest = null;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnRequestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    columnPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));

    columnRequestID.getStyleClass().add("table-column-left");
    columnType.getStyleClass().add("table-column-middle");
    columnStatus.getStyleClass().add("table-column-middle");
    columnPriority.getStyleClass().add("table-column-middle");
    columnButtons.getStyleClass().add("table-column-right");

    for (Request request : ServiceRequestsDB.getInstance().list()) {
      if (request.getEmployeeID() != null
          && request.getEmployeeID().equals(App.currentUser.getEmployeeID())
          && !request.getStatus().equals("Completed")) requests.add(request);
    }

    sortRequestsByCreationDate(requests);

    requestTable.setItems(requests);
    addButtonToTable();
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
                          Request request = getTableView().getItems().get(getIndex());
                          currentRequest = request;
                          requestIDLabel.setText(request.getRequestID());
                          createdLabel.setText("Created: " + request.getTimeCreated().toString());
                          lastEditedLabel.setText(
                              "Last Edited: " + request.getLastEdited().toString());
                          informationLabel.setText(request.getInformation());

                          statusInput.setValue(request.getStatus());
                          statusInput.setDisable(false);

                          scrollPane.setVisible(true);
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

    columnButtons.setCellFactory(cellFactory);
  }

  @FXML
  public void saveData(ActionEvent event) {
    currentRequest.setStatus(statusInput.getValue());
    statusInput.setDisable(true);
    currentRequest.setLastEdited(new Date());
    if (currentRequest.getStatus().equals("Completed")) requests.remove(currentRequest);
    ServiceRequestsDB.getInstance().update(currentRequest);
    requestTable.refresh();

    scrollPane.setVisible(false);
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
