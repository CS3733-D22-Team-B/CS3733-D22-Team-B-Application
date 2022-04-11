package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RequestQueueController extends MenuBarController implements Initializable {
  @FXML TableView<Request> requestTable;
  @FXML TableColumn<Request, String> columnRequestID;
  @FXML TableColumn<Request, String> columnType;
  @FXML TableColumn<Request, String> columnStatus;
  @FXML TableColumn<Request, Integer> columnPriority;
  @FXML TableColumn<Request, Void> columnButtons;

  @FXML Label requestIDLabel;
  @FXML Label locationLabel;
  @FXML Label employeeLabel;

  @FXML ComboBox<String> statusInput;
  @FXML ComboBox<String> employeeInput;

  Request currentRequest = null;
  protected EmployeesDB dao;

  private ObservableList<Request> requests = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnRequestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    columnPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));

    columnRequestID.getStyleClass().add("simple-table-column-left");
    columnType.getStyleClass().add("simple-table-column-middle");
    columnStatus.getStyleClass().add("simple-table-column-middle");
    columnPriority.getStyleClass().add("simple-table-column-middle");
    columnButtons.getStyleClass().add("simple-table-column-right");

    statusInput.setDisable(true);
    employeeInput.setDisable(true);

    dao = EmployeesDB.getInstance();
    LinkedList<Employee> employees = dao.list();
    for (Employee employeeItem : employees) {
      if (!employeeItem.getEmployeeID().equals("0")) {
        employeeInput
            .getItems()
            .add(
                employeeItem.getFirstName()
                    + " "
                    + employeeItem.getLastName()
                    + " ("
                    + employeeItem.getEmployeeID()
                    + ")");
      }
    }

    for (EquipmentRequest request : EquipmentRequestDB.getInstance().list()) {
      requests.add(request);
    }
    for (LabRequest request : LabRequestsDB.getInstance().list()) {
      requests.add(request);
    }
    for (Request request : ServiceRequestsDB.getInstance().list()) {
      requests.add(request);
    }

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
                  private final Button requestViewerButton = new Button("View Request");

                  {
                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          Request request = getTableView().getItems().get(getIndex());
                          currentRequest = request;
                          requestIDLabel.setText(request.getRequestID());
                          locationLabel.setText(request.getInformation());

                          String employeeID = request.getEmployeeID();
                          if (!employeeID.equals("0")) {
                            employeeInput.setValue(employeeID);
                          }

                          employeeInput.setDisable(false);
                          statusInput.setValue(request.getStatus());
                          statusInput.setDisable(false);
                        });
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(requestViewerButton);
                    }
                  }
                };
            return cell;
          }
        };

    columnButtons.setCellFactory(cellFactory);
    requestTable.getColumns().add(columnButtons);
  }

  @FXML
  public void saveData(ActionEvent event) {
    currentRequest.setStatus(statusInput.getValue());
    currentRequest.setEmployeeID(employeeInput.getValue());
    employeeInput.setValue("");
    requestTable.refresh();
    statusInput.setDisable(true);
    employeeInput.setDisable(true);
  }
}
