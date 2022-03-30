package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class InterpreterRequestController extends MenuBarController {
  @FXML ChoiceBox floorNumber;
  @FXML ChoiceBox roomNumber;
  @FXML private Label outputLabel;
  @FXML private ChoiceBox languageField;

  private LinkedList<String> roomsF3 = new LinkedList<>();
  private LinkedList<String> roomsF2 = new LinkedList<>();
  private LinkedList<String> roomsF1 = new LinkedList<>();
  private LinkedList<String> roomsFL1 = new LinkedList<>();
  private LinkedList<String> roomsFL2 = new LinkedList<>();

  private LocationsDAO dao;

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

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String language = languageField.getSelectionModel().getSelectedItem().toString();
    String room = roomNumber.getSelectionModel().getSelectedItem().toString();
    String floor = floorNumber.getSelectionModel().getSelectedItem().toString();

    String locationID = dao.getLocationID(room);
    InterpreterRequest request = new InterpreterRequest("Please Fix This!", language, locationID);

    outputLabel.setText("You have selected " + language);
    // TODO: request list
  }

  @FXML
  public void reset() {
    floorNumber.setValue("");
    roomNumber.setValue("");
    languageField.setValue("");
    outputLabel.setText("");
  }
}
