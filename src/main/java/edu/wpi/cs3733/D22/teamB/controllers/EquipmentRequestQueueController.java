package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.Request;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import java.net.URL;
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

public class EquipmentRequestQueueController extends MenuBarController implements Initializable {
  @FXML TableView<Request> requestTable;
  @FXML TableColumn<Request, String> columnRequestID;
  @FXML TableColumn<Request, String> columnType;
  @FXML TableColumn<Request, String> columnStatus;
  @FXML TableColumn<Request, Void> columnButtons;

  @FXML Label requestIDLabel;
  @FXML Label typeLabel;
  @FXML Label statusLabel;
  @FXML Label locationLabel;
  @FXML Label employeeLabel;

  @FXML ComboBox<String> statusInput;

  Request currentRequest = null;

  private ObservableList<Request> requests = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnRequestID.setCellValueFactory(new PropertyValueFactory<>("employee"));
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    for (Request equipmentRequest : RequestQueue.getRequests()) {
      requests.add(equipmentRequest);
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
                  private final Button btn = new Button("View Request");

                  {
                    btn.setOnAction(
                        (ActionEvent event) -> {
                          Request request = getTableView().getItems().get(getIndex());
                          currentRequest = request;
                          locationLabel.setText(request.getLocation());
                        });
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(btn);
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
  public void setStatus(ActionEvent event) {
    currentRequest.setStatus(statusInput.getValue());
    requestTable.refresh();
  }
}
