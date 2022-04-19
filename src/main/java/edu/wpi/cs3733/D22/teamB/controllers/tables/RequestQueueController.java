package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.DateHelper;
import edu.wpi.cs3733.D22.teamB.controllers.AlertController;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class RequestQueueController extends MenuBarController implements Initializable {
  @FXML TableView<Request> requestTable;
  @FXML TableColumn<Request, String> columnRequestID;
  @FXML TableColumn<Request, String> columnType;
  @FXML TableColumn<Request, String> columnStatus;
  @FXML TableColumn<Request, Integer> columnPriority;
  @FXML TableColumn<Request, Void> columnButtons;

  @FXML private TextField downloadText;
  @FXML private Label errorLabel;

  @FXML Label requestIDText;
  @FXML Text creationText;
  @FXML Text editText;
  @FXML Text employeeText;
  @FXML Text statusText;
  @FXML Text informationText;

  @FXML Label requestIDLabel;
  @FXML ComboBox<String> employeeInput;
  @FXML ComboBox<String> statusInput;
  @FXML TextArea informationInput;
  @FXML Label charactersRemainingLabel;

  @FXML AnchorPane viewingPane;
  @FXML AnchorPane editingPane;
  @FXML AnchorPane otherAnchorPane;

  Request currentRequest = null;
  protected EmployeesDB dao;

  private ObservableList<Request> requests = FXCollections.observableArrayList();

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

    dao = EmployeesDB.getInstance();
    LinkedList<Employee> employees = dao.list();
    for (Employee employeeItem : employees) {
      if (!employeeItem.getEmployeeID().equals("0")) {
        employeeInput.getItems().add(employeeItem.getOverview());
      }
    }

    for (Request request : ServiceRequestsDB.getInstance().list()) {
      requests.add(request);
    }

    sortRequestsByCreationDate(requests);

    requestTable.setItems(requests);
    addButtonToTable();

    informationInput
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.length() > 500) {
                informationInput.setText(oldValue);
              }
              charactersRemainingLabel.setText(
                  String.valueOf(500 - informationInput.getText().length())
                      + " characters remaining");
            });
  }

  private void addButtonToTable() {
    Callback<TableColumn<Request, Void>, TableCell<Request, Void>> cellFactory =
        new Callback<TableColumn<Request, Void>, TableCell<Request, Void>>() {
          @Override
          public TableCell<Request, Void> call(final TableColumn<Request, Void> param) {
            final TableCell<Request, Void> cell =
                new TableCell<Request, Void>() {
                  private final HBox hBox = new HBox();
                  private final Button requestViewerButton = new Button("View");
                  private final Button requestEditButton = new Button("Edit");
                  private final Button requestDeleteButton = new Button("Delete");

                  private final ImageView viewIcon =
                      new ImageView(
                          new Image(
                              "/edu/wpi/cs3733/D22/teamB/assets/Buttons & Common Assets/viewIcon.png"));
                  private final ImageView editIcon =
                      new ImageView(
                          new Image(
                              "/edu/wpi/cs3733/D22/teamB/assets/Buttons & Common Assets/editIcon.png"));
                  private final ImageView deleteIcon =
                      new ImageView(
                          new Image(
                              "/edu/wpi/cs3733/D22/teamB/assets/Buttons & Common Assets/deleteIcon.jpg"));

                  {
                    hBox.setSpacing(10);

                    requestViewerButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestViewerButton.setMinSize(25, 25);
                    requestViewerButton.setPrefSize(25, 25);
                    requestViewerButton.setMaxSize(25, 25);
                    requestViewerButton.setGraphic(viewIcon);
                    viewIcon.setFitHeight(25);
                    viewIcon.setFitWidth(25);

                    requestEditButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestEditButton.setMinSize(25, 25);
                    requestEditButton.setPrefSize(25, 25);
                    requestEditButton.setMaxSize(25, 25);
                    requestEditButton.setGraphic(editIcon);
                    editIcon.setFitHeight(25);
                    editIcon.setFitWidth(25);

                    requestDeleteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestDeleteButton.setMinSize(25, 25);
                    requestDeleteButton.setPrefSize(25, 25);
                    requestDeleteButton.setMaxSize(25, 25);
                    requestDeleteButton.setGraphic(deleteIcon);
                    deleteIcon.setFitHeight(25);
                    deleteIcon.setFitWidth(25);

                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(false);
                          viewingPane.setVisible(true);
                          editingPane.setVisible(false);

                          Request request = getTableView().getItems().get(getIndex());
                          currentRequest = request;

                          requestIDText.setText(request.getRequestID());
                          creationText.setText(DateHelper.stringify(request.getTimeCreated()));
                          editText.setText(DateHelper.stringify(request.getLastEdited()));
                          employeeText.setText(request.getEmployee().getOverview());
                          statusText.setText(request.getStatus());
                          informationText.setText(request.getInformation());
                        });

                    requestEditButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(false);
                          viewingPane.setVisible(false);
                          editingPane.setVisible(true);

                          Request request = getTableView().getItems().get(getIndex());
                          currentRequest = request;

                          requestIDLabel.setText(request.getRequestID());
                          employeeInput.setValue(request.getEmployee().getOverview());
                          statusInput.setValue(request.getStatus());
                          informationInput.setText(request.getInformation());
                        });

                    requestDeleteButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(true);
                          viewingPane.setVisible(false);
                          editingPane.setVisible(false);

                          Request request = getTableView().getItems().get(getIndex());
                          currentRequest = request;

                          // create a confirmation dialog
                          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                          alert.setTitle("Confirm Delete");
                          alert.setHeaderText(
                              "Are you sure you want to delete request "
                                  + currentRequest.getRequestID()
                                  + "?");
                          alert.setContentText("This action cannot be undone.");

                          ((Button) alert.getDialogPane().lookupButton(ButtonType.OK))
                              .setText("Delete");
                          ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL))
                              .setText("Cancel");

                          Optional<ButtonType> result = alert.showAndWait();
                          if (result.get() == ButtonType.OK) {
                            ServiceRequestsDB.getInstance().delete(request);
                            requests.remove(request);
                            requestTable.refresh();
                            currentRequest = null;
                          }
                        });

                    hBox.getChildren()
                        .addAll(requestViewerButton, requestEditButton, requestDeleteButton);
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(hBox);
                      requestViewerButton.getStyleClass().add("hidden-button");
                      requestEditButton.getStyleClass().add("hidden-button");
                      requestDeleteButton.getStyleClass().add("hidden-button");
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
    currentRequest.setEmployeeID(EmployeesDB.getInstance().getEmployeeID(employeeInput.getValue()));
    currentRequest.setInformation(informationInput.getText());
    currentRequest.setLastEdited(new Date());

    employeeInput.setValue("");
    statusInput.setValue("");
    informationInput.setText("");

    requestTable.refresh();

    ServiceRequestsDB.getInstance().update(currentRequest);
    if (currentRequest instanceof EquipmentRequest) {
      EquipmentRequest eqReq = (EquipmentRequest) currentRequest;
      eqReq.updateMedicalEquipmentStatus();
      AlertController alertController = AlertController.getInstance();
      alertController.checkForAlerts();
    }

    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
  }

  private void sortRequestsByCreationDate(ObservableList<Request> requests) {
    Collections.sort(
        requests, (Request r1, Request r2) -> r1.getTimeCreated().compareTo(r2.getTimeCreated()));
  }

  @FXML
  public void download() {
    if (downloadText.getText().equals("")) {
      errorLabel.setText("Please enter filename");
    } else {
      ServiceRequestsDB.getInstance().downloadCSV(downloadText.getText() + " - Service Requests");
      errorLabel.setText("Files downloaded");
    }
  }
}
