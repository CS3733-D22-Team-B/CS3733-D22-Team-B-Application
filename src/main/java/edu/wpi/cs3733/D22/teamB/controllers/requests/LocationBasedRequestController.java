package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public abstract class LocationBasedRequestController extends RequestController {
  @FXML protected ComboBox<String> floorInput;
  @FXML protected ComboBox<String> locationInput;

  protected String floor;
  protected String locationName;

  protected LinkedList<String> locationsF3 = new LinkedList<>();
  protected LinkedList<String> locationsF2 = new LinkedList<>();
  protected LinkedList<String> locationsF1 = new LinkedList<>();
  protected LinkedList<String> locationsFL1 = new LinkedList<>();
  protected LinkedList<String> locationsFL2 = new LinkedList<>();

  protected LocationsDB locationsDAO;

  public void initialize() {
    locationsDAO = LocationsDB.getInstance();
    LinkedList<Location> locations = locationsDAO.list();

    for (Location location : locations) {
      switch (location.getFloor()) {
        case "3":
          locationsF3.add(location.getLongName());
          break;
        case "2":
          locationsF2.add(location.getLongName());
          break;
        case "1":
          locationsF1.add(location.getLongName());
          break;
        case "L1":
          locationsFL1.add(location.getLongName());
          break;
        case "L2":
          locationsFL2.add(location.getLongName());
          break;
      }
    }
    locationName = "";
    floor = "";
  }

  public void setFloor() {
    floor = floorInput.getValue();
    resetLocations();
  }

  public void setLocation() {
    locationName = locationInput.getValue();
    enableSubmission();
  }

  private void resetLocations() {
    locationInput.getItems().clear();
    switch (floor) {
      case "F3":
        for (String locationF3 : locationsF3) {
          locationInput.getItems().add(locationF3);
        }
        break;
      case "F2":
        for (String locationF2 : locationsF2) {
          locationInput.getItems().add(locationF2);
        }
        break;
      case "F1":
        for (String locationF1 : locationsF1) {
          locationInput.getItems().add(locationF1);
        }
        break;
      case "L1":
        for (String locationFL1 : locationsFL1) {
          locationInput.getItems().add(locationFL1);
        }
        break;
      case "L2":
        for (String locationFL2 : locationsFL2) {
          locationInput.getItems().add(locationFL2);
        }
        break;
    }
  }
}
