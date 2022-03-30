package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipmentDAO;
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
  @FXML TableColumn<Location, String> columnEquipment;
  @FXML TableColumn<Location, String> columnSterilization;
  @FXML TableColumn<Location, String> columnFloor;
  @FXML TableColumn<Location, String> columnLocation;
  @FXML TableColumn<Location, String> columnBuilding;

  private ObservableList<Location> locations = FXCollections.observableArrayList();
  private ObservableList<MedicalEquipment> equipment = FXCollections.observableArrayList();
  private LocationsDAO dao;
  private MedicalEquipmentDAO medDao;

  @Override
  public void initialize(URL loc, ResourceBundle resources) {
    columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipmentID"));
    columnSterilization.setCellValueFactory(new PropertyValueFactory<>("isClean"));
    columnFloor.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnLocation.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    columnBuilding.setCellValueFactory(new PropertyValueFactory<>("isRequested"));

    dao = new LocationsDAO();
    medDao = new MedicalEquipmentDAO();
    LinkedList<Location> locs = dao.listLocations();
    for (Location location : locs) {
      locations.add(location);
    }
    LinkedList<MedicalEquipment> equip = medDao.listMedicalEquipment();
    for (MedicalEquipment med : equip) {
      equipment.add(med);
    }

    equipmentTable.setItems(equipment);
  }
}
