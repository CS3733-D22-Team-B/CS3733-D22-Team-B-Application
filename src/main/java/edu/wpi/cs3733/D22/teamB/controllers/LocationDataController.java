package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
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

public class LocationDataController extends MenuBarController implements Initializable {
  @FXML TableView<edu.wpi.cs3733.D22.teamB.databases.Location> locationTable;
  @FXML TableColumn<edu.wpi.cs3733.D22.teamB.databases.Location, String> columnName;
  @FXML TableColumn<edu.wpi.cs3733.D22.teamB.databases.Location, String> columnX;
  @FXML TableColumn<edu.wpi.cs3733.D22.teamB.databases.Location, String> columnY;
  @FXML TableColumn<edu.wpi.cs3733.D22.teamB.databases.Location, String> columnFloor;
  @FXML TableColumn<edu.wpi.cs3733.D22.teamB.databases.Location, String> columnBuilding;

  private ObservableList<Location> locations = FXCollections.observableArrayList();
  private LocationsDB dao;

  @Override
  public void initialize(URL loc, ResourceBundle resources) {
    columnName.setCellValueFactory(new PropertyValueFactory<>("longName"));
    columnX.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    columnY.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
    columnFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
    columnBuilding.setCellValueFactory(new PropertyValueFactory<>("building"));

    dao = new LocationsDB();
    LinkedList<edu.wpi.cs3733.D22.teamB.databases.Location> locs = dao.listLocations();
    for (edu.wpi.cs3733.D22.teamB.databases.Location location : locs) {
      locations.add(location);
    }

    locationTable.setItems(locations);
  }
}
