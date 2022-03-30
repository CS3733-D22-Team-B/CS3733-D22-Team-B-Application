package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.EquipmentRequest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipmentRequestQueueController extends MenuBarController implements Initializable {
  @FXML TableView<EquipmentRequest> requestTable;
  @FXML TableColumn<EquipmentRequest, String> columnEquipment;
  @FXML TableColumn<EquipmentRequest, String> columnDestination;
  @FXML TableColumn<EquipmentRequest, String> columnEmployee;

  private ObservableList<EquipmentRequest> requests = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipment"));
    columnDestination.setCellValueFactory(new PropertyValueFactory<>("location"));
    columnEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));

    for (EquipmentRequest equipmentRequest : EquipmentRequest.equipmentRequests) {
      requests.add(equipmentRequest);
    }

    requestTable.setItems(requests);
  }
}
