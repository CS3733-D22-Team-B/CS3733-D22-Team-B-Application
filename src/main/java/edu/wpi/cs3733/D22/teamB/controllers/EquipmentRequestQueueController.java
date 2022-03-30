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
  /*
     TODO: replace with equipment request database - not yet implemented
  private EquipmentRequestDAO dao;
     */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipment"));
    columnDestination.setCellValueFactory(new PropertyValueFactory<>("location"));
    columnEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
    requests.add(new EquipmentRequest("Equipment", "Employee Name", "Location", "notes"));
    /*
    TODO: replace with equipment database
    dao = new EquipmentRequestDAO();
    LinkedList<EquipmentRequest> equips = dao.listEquipmentRequests();
    for (EquipmentRequest equipment : equips) {
      requests.add(equipment);
    }
     */
    requestTable.setItems(requests);
  }
}
