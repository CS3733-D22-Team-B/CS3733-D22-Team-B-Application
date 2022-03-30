package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class RequestController extends MenuBarController {
  @FXML protected Label requestLabel;
  @FXML protected TextField employeeNameInput;
  @FXML protected ComboBox<String> floorInput;
  @FXML protected ComboBox<String> roomInput;

  protected String employeeName;
  protected String floor;
  protected String room;

  protected LinkedList<String> roomsF3 = new LinkedList<>();
  protected LinkedList<String> roomsF2 = new LinkedList<>();
  protected LinkedList<String> roomsF1 = new LinkedList<>();
  protected LinkedList<String> roomsFL1 = new LinkedList<>();
  protected LinkedList<String> roomsFL2 = new LinkedList<>();

  protected LocationsDAO dao;

  public void initialize() {
    dao = new LocationsDAO();
    LinkedList<Location> locations = dao.listLocations();

    for (Location location : locations) {
      switch (location.getFloor()) {
        case "3":
          roomsF3.add(location.getLongName());
          break;
        case "2":
          roomsF2.add(location.getLongName());
          break;
        case "1":
          roomsF1.add(location.getLongName());
          break;
        case "L1":
          roomsFL1.add(location.getLongName());
          break;
        case "L2":
          roomsFL2.add(location.getLongName());
          break;
      }
    }
  }

  public void setEmployeeName() {
    employeeName = employeeNameInput.getText();
  }

  public void setFloor() {
    floor = floorInput.getValue();
    resetRoomBox();
  }

  public void setRoom() {
    room = roomInput.getValue();
  }

  private void resetRoomBox() {
    roomInput.getItems().clear();
    switch (floor) {
      case "F3":
        for (String room : roomsF3) {
          roomInput.getItems().add(room);
        }
        break;
      case "F2":
        for (String room : roomsF2) {
          roomInput.getItems().add(room);
        }
        break;
      case "F1":
        for (String room : roomsF1) {
          roomInput.getItems().add(room);
        }
        break;
      case "L1":
        for (String room : roomsFL1) {
          roomInput.getItems().add(room);
        }
        break;
      case "L2":
        for (String room : roomsFL2) {
          roomInput.getItems().add(room);
        }
        break;
    }
  }

  @FXML
  public abstract void sendRequest(ActionEvent event);

  @FXML
  public abstract void reset(ActionEvent event);

  public Boolean setStatus() {
    return null;
  }
}
