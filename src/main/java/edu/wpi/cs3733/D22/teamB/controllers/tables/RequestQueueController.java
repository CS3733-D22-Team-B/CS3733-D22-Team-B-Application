package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.DateHelper;
import edu.wpi.cs3733.D22.teamB.controllers.AlertController;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import edu.wpi.cs3733.D22.teamB.requests.SanitationRequest;
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
  @FXML TableColumn<Request, HBox> columnButtons;

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
  @FXML ComboBox<String> Type;
  @FXML TextArea informationInput;
  @FXML Label charactersRemainingLabel;

  @FXML AnchorPane viewingPane;
  @FXML AnchorPane editingPane;
  @FXML AnchorPane otherAnchorPane;

  public static Request currentRequest;
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

    view();
  }

  private void addButtonToTable() {
    Callback<TableColumn<Request, HBox>, TableCell<Request, HBox>> cellFactory =
        new Callback<TableColumn<Request, HBox>, TableCell<Request, HBox>>() {
          @Override
          public TableCell<Request, HBox> call(final TableColumn<Request, HBox> param) {
            final TableCell<Request, HBox> cell =
                new TableCell<Request, HBox>() {
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
                          if (!request.getEmployee().getEmployeeID().equals("0")) {
                            employeeText.setText(request.getEmployee().getOverview());
                          } else {
                            employeeText.setText("No employee assigned");
                          }
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
                          if (!request.getEmployee().getEmployeeID().equals("0")) {
                            employeeInput.setValue(request.getEmployee().getOverview());
                          } else {
                            employeeInput.setValue("");
                          }
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
                  public void updateItem(HBox item, boolean empty) {
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
      AlertController.getInstance().checkForAlerts();
    } else if (currentRequest instanceof SanitationRequest) {
      SanitationRequest sanReq = (SanitationRequest) currentRequest;
      sanReq.updateMedicalEquipmentStatus();
    }

    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
  }

  private void sortRequestsByCreationDate(ObservableList<Request> requests) {
    Collections.sort(
        requests, (Request r1, Request r2) -> r1.getTimeCreated().compareTo(r2.getTimeCreated()));
  }

  private void sortRequestsByLastEdit(ObservableList<Request> requests) {
    Collections.sort(
        requests, (Request r1, Request r2) -> r1.getLastEdited().compareTo(r2.getLastEdited()));
  }

  public void timeCreated() {
    sortRequestsByCreationDate(requests);
  }

  public void lastEdit() {
    sortRequestsByLastEdit(requests);
  }

  private ObservableList<Request> sortRequestsByType(ObservableList<Request> request, String type) {

    for (int x = 0; x < 5; x++) {
      for (int i = 0; i < request.size(); i++) {
        System.out.println(request.get(i).getType());
        if (!request.get(i).getType().equals(type)) {
          System.out.println(request.get(i).getType());
          request.remove(i);
        }
      }
    }
    return request;
  }

  public void setSortType() {
    // use combobox to choose type to sort by type
    ObservableList<Request> tempRequests = requests;
    String type = Type.getValue();
    System.out.println(type);
    if (type != null) {
      ObservableList<Request> refreshRequest = FXCollections.observableArrayList();
      for (Request request : ServiceRequestsDB.getInstance().list()) {
        // if (!request.getStatus().equals("Completed"))
        refreshRequest.add(request);
      }
      sortRequestsByCreationDate(refreshRequest);
      requestTable.setItems(refreshRequest);
      addButtonToTable();
      switch (type) {
        case "Lab Test":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Equipment Delivery":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Meal":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Medicine":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Interpreter":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Transfer":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Gift":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Laundry":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "Item Delivery":
          sortRequestsByType(refreshRequest, type);
          requestTable.setItems(refreshRequest);
        case "All":
          requestTable.setItems(refreshRequest);
      }
    }
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

  public void view() {
    Request desiredRequest = currentRequest;
    currentRequest = null;

    if (desiredRequest != null) {
      requestTable.getSelectionModel().select(desiredRequest);
      requestTable.scrollTo(desiredRequest);

      otherAnchorPane.setVisible(false);
      viewingPane.setVisible(true);
      editingPane.setVisible(false);

      requestIDText.setText(desiredRequest.getRequestID());
      creationText.setText(DateHelper.stringify(desiredRequest.getTimeCreated()));
      editText.setText(DateHelper.stringify(desiredRequest.getLastEdited()));
      employeeText.setText(desiredRequest.getEmployee().getOverview());
      statusText.setText(desiredRequest.getStatus());
      informationText.setText(desiredRequest.getInformation());
    }
  }
}
