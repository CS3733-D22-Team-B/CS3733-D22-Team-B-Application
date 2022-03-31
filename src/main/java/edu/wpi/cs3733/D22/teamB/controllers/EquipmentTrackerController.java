package edu.wpi.cs3733.D22.teamB.controllers;

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
  @FXML TableColumn<MedicalEquipment, String> columnSterilization;
  @FXML TableColumn<MedicalEquipment, String> columnFloor;
  @FXML TableColumn<MedicalEquipment, String> columnLocation;
  @FXML TableColumn<MedicalEquipment, String> columnBuilding;

  private ObservableList<Location> locations = FXCollections.observableArrayList();
  private ObservableList<MedicalEquipment> equipment = FXCollections.observableArrayList();
  private LocationsDB dao;
  private MedicalEquipmentDB medDao;

  @Override
  public void initialize(URL loc, ResourceBundle resources) {
    columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipmentID"));
    columnSterilization.setCellValueFactory(new PropertyValueFactory<>("isClean"));
    columnFloor.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    columnBuilding.setCellValueFactory(new PropertyValueFactory<>("isRequested"));

    dao = LocationsDB.getInstance();
    medDao = new MedicalEquipmentDB();
    LinkedList<MedicalEquipment> equip = medDao.listMedicalEquipment();
    for (MedicalEquipment med : equip) {
      med.setLocation(dao.getLocation(med.getNodeID()).getLongName());
      equipment.add(med);
    }

    equipmentTable.setItems(equipment);
  }
}
