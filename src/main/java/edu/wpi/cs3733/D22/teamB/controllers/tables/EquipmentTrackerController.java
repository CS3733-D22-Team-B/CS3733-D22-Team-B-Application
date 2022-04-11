package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipmentDB;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipmentTrackerController extends MenuBarController implements Initializable {
  @FXML TableView<MedicalEquipment> equipmentTable;
  @FXML TableColumn<MedicalEquipment, String> columnEquipment;
  @FXML TableColumn<MedicalEquipment, String> columnLocation;
  @FXML TableColumn<MedicalEquipment, String> columnSterilization;
  @FXML TableColumn<MedicalEquipment, String> columnAvailability;

  private ObservableList<Location> locations = FXCollections.observableArrayList();
  private ObservableList<MedicalEquipment> equipment = FXCollections.observableArrayList();
  private LocationsDB dao;
  private MedicalEquipmentDB medDao;

  @Override
  public void initialize(URL loc, ResourceBundle resources) {
    dao = LocationsDB.getInstance();
    medDao = MedicalEquipmentDB.getInstance();
    LinkedList<MedicalEquipment> equip = medDao.list();
    for (MedicalEquipment med : equip) {
      med.getLocation(); // Initialize the location for each medical equipment object
      equipment.add(med);
    }

    columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipmentID"));
    columnLocation.setCellValueFactory(new PropertyValueFactory<>("longName"));
    columnSterilization.setCellValueFactory(new PropertyValueFactory<>("isSterilized"));
    columnAvailability.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

    columnEquipment.getStyleClass().add("simple-table-column-left");
    columnLocation.getStyleClass().add("simple-table-column-middle");
    columnSterilization.getStyleClass().add("simple-table-column-middle");
    columnAvailability.getStyleClass().add("simple-table-column-right");

    equipmentTable.setItems(equipment);
  }
}
